/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import Arbol.Instrucciones.GUI.COMPONENTE;
import Arbol.Instrucciones.General.CONTINUAR;
import Arbol.Instrucciones.General.DECLARACION;
import Arbol.Instrucciones.General.DECLARACION_ARREGLO;
import Arbol.Instrucciones.General.DETENER;
import Arbol.Instrucciones.General.FUNCION;
import Arbol.Instrucciones.General.RETORNAR;
import Arbol.Instrucciones.Instruccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Fernando
 */
public class LLAMADA_FUNCION extends Expresion {

    String identificador;
    LinkedList<Expresion> valores;
    int fila;
    int columna;

    /**
     *
     * @param identificador
     * @param valores
     * @param fila
     * @param columna
     */
    public LLAMADA_FUNCION(String identificador, LinkedList<Expresion> valores, int fila, int columna) {
        this.identificador = identificador;
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }

    /**
     *
     * @param entorno_funcion
     * @param sentencias
     * @return
     */
    public Object ejecutar_Instrucciones(Entorno entorno_funcion, LinkedList<Instruccion> sentencias) {

        if (sentencias != null) {

            int i = 0;
            while (i < sentencias.size()) {
                /**
                 * *
                 * NINGUNA ACTIVA ESO QUIERE DECIR QUE ESTOY EN EL HILO
                 * PRINCIPAL
                 */
                if (Display.Bandera_ACTIVA == false && Display.Bandera_CLICK == false) {
                    Instruccion ins = sentencias.get(i);

                    Object retorno = (((Instruccion) ins).Ejecutar(entorno_funcion));

                    if (retorno != null) {
                        if (retorno instanceof DETENER) {
                            return retorno;
                        } else if (retorno instanceof CONTINUAR) {
                            return retorno;
                        } else if (retorno instanceof RETORNAR) {
                            return retorno;
                        }
                    }
                    i++;
                    /**
                     * LAS DOS ACTIVAS ESO QUIERE DECIR QUE ESTOY EN UN BOTON
                     */
                } else if (Display.Bandera_ACTIVA && Display.Bandera_CLICK) {
                    Instruccion ins = sentencias.get(i);

                    Object retorno = (((Instruccion) ins).Ejecutar(entorno_funcion));

                    if (retorno != null) {
                        if (retorno instanceof DETENER) {
                            return retorno;
                        } else if (retorno instanceof CONTINUAR) {
                            return retorno;
                        } else if (retorno instanceof RETORNAR) {
                            return retorno;
                        }
                    }
                    i++;
                    /**
                     * *
                     * SI NO ESTAN LAS DOS ACTIVAS O AMBAS DESACTIVADAS ESO
                     * QUIERE DECIR QUE TENGO QUE ESPERAR A QUE CAMBIEN DE
                     * ESTADO
                     */
                } else {
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LLAMADA_FUNCION.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }

        return null;
    }

    /**
     *
     * @param tablasimbolos
     * @return
     */
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "error", fila, columna);

        String id_busqueda = "FUNCION_" + identificador;
        LinkedList<Expresion> valores_ejecutados = new LinkedList<>();
        if (valores != null) {
            for (Expresion exp : valores) {
                Expresion temporal = (Expresion) exp.Ejecutar(tablasimbolos);
                if (temporal != null) {
                    if (temporal.tipo.tipo == Tipo.TipoSimbolo.objeto) {
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) temporal;
                        Simbolo sim = (Simbolo) temp.simbolo_retorno;
                        if (sim.Tipo.tipo_Compuesto.equals("ARREGLO")) {
                            OBJETO_ARREGLO temp_ = (OBJETO_ARREGLO) sim.Valor;
                            valores_ejecutados.addLast(temporal);
                            id_busqueda += "_D_" + temp_.tipo.tipo + "_D_" + temp_.tipo.tipo_Compuesto + "_D_" + temp_.dimensiones + "_D_" + temp_.tipoDatos.tipo + "_D_" + temp_.tipoDatos.tipo_Compuesto;
                        } else {
                            valores_ejecutados.addLast(temporal);
                            id_busqueda += "_D_" + temporal.tipo.tipo + "_D_" + temporal.tipo.tipo_Compuesto;
                        }
                    } else {
                        valores_ejecutados.addLast(temporal);
                        id_busqueda += "_D_" + temporal.tipo.tipo + "_D_" + temporal.tipo.tipo_Compuesto;

                    }
                }
            }
        }

        Simbolo simbolo_buscado = tablasimbolos.buscarsinreportar(id_busqueda, fila, columna);
        if (simbolo_buscado != null) {
            FUNCION funcion_recuperada = (FUNCION) simbolo_buscado.Valor;
            Entorno localfuncion = new Entorno(tablasimbolos);
            if (funcion_recuperada != null) {
                for (int i = 0; i < valores_ejecutados.size(); i++) {
                    if (funcion_recuperada.parametros.get(i) instanceof DECLARACION) {
                        DECLARACION temporal_parametro = (DECLARACION) funcion_recuperada.parametros.get(i);
                        temporal_parametro.expresion = valores_ejecutados.get(i);
                        temporal_parametro.Ejecutar(localfuncion);
                    } else if (funcion_recuperada.parametros.get(i) instanceof DECLARACION_ARREGLO) {
                        DECLARACION_ARREGLO temporal_parametro = (DECLARACION_ARREGLO) funcion_recuperada.parametros.get(i);
                        temporal_parametro.expresion = valores_ejecutados.get(i);
                        temporal_parametro.Ejecutar(localfuncion);

                    }

                }
            }
            Display.display.addFirst(Display.Tipo_Instruccion.funcion);
            String temporal = Display.ruta_archivo;
            Display.ruta_archivo = funcion_recuperada.nombre_archivo_origen;
            Object valor_retorno = ejecutar_Instrucciones(localfuncion, funcion_recuperada.instruciones);
            Display.ruta_archivo = temporal;

            if (valor_retorno != null) {
                if (valor_retorno instanceof RETORNAR) {
                    if (funcion_recuperada.tipo.tipo == Tipo.TipoSimbolo.vacio) {
                        Errores.agregar_error("La funcion buscada no deberia de retornar ningun valor", fila, columna, "Semantico");
                        Display.display.removeFirst();
                        return error;
                    }
                    RETORNAR valor = (RETORNAR) valor_retorno;
                    if (valor.valor_de_retorno != null) {
                        Expresion retorno_final = (Expresion) valor.valor_de_retorno;

                        if (retorno_final.tipo.tipo == Tipo.TipoSimbolo.objeto) {
                            DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) retorno_final;
                            Simbolo sim = (Simbolo) temp.simbolo_retorno;
                            if (sim.Tipo.tipo_Compuesto.equals("ARREGLO")) {
                                if (sim.dimensiones == funcion_recuperada.dimensiones) {
                                    OBJETO_ARREGLO temp_ = (OBJETO_ARREGLO) sim.Valor;
                                    if (sim.tipo_datos.tipo == funcion_recuperada.tipo.tipo
                                            && sim.tipo_datos.tipo_Compuesto.equals(funcion_recuperada.tipo.tipo_Compuesto)) {
                                        Display.display.removeFirst();
                                        return retorno_final;
                                    }
                                    //son arreglos y son iguales de dimensiones pero el tipo que regresa el objeto no es el mismo
                                    Errores.agregar_error("NO coinciden el tipo de retorno con tipo de la funcion", fila, columna, "Semantico");
                                    Display.display.removeFirst();
                                    return error;
                                } else {
                                    //error
                                    Errores.agregar_error("NO coinciden el tipo de retorno con tipo de la funcion por las dimensiones de la misma", fila, columna, "Semantico");
                                    Display.display.removeFirst();
                                    return error;
                                }
                            } else {

                                if (Tipo.TipoSimbolo.objeto == funcion_recuperada.tipo.tipo
                                        && retorno_final.valor == null) {
                                    Display.display.removeFirst();
                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo == funcion_recuperada.tipo.tipo
                                        && retorno_final.tipo.tipo_Compuesto.equals(funcion_recuperada.tipo.tipo_Compuesto)) {
                                    Display.display.removeFirst();
                                    return retorno_final;
                                } else {
                                    Errores.agregar_error("NO coinciden el tipo de retorno con tipo de la funcion", fila, columna, "Semantico");
                                    Display.display.removeFirst();
                                    return error;
                                }
                            }
                        } else {
                            //PRIMITIVO

                            if (retorno_final.tipo.tipo == funcion_recuperada.tipo.tipo) {
                                Display.display.removeFirst();
                                return retorno_final;
                            }
                            if (funcion_recuperada.tipo.tipo == Tipo.TipoSimbolo.objeto
                                    && funcion_recuperada.dimensiones == 0
                                    && retorno_final.tipo.tipo == Tipo.TipoSimbolo.nulo) {
                                Display.display.removeFirst();
                                return retorno_final;
                            }
                            // esto lo acabo de agregar no se si jale

                            if (funcion_recuperada.tipo.tipo == Tipo.TipoSimbolo.objeto
                                    && funcion_recuperada.dimensiones == 0
                                    && retorno_final.valor == null) {
                                Display.display.removeFirst();
                                return retorno_final;
                            }
                            if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    int valor__ = valor_.intValue();
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.entero;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    int valor__ = valor_.intValue();
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.entero;
                                    Display.display.removeFirst();

                                    return retorno_final;

                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                                    int valor__ = retorno_final.valor.toString().charAt(0);
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.entero;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                }
                            } else if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {

                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    retorno_final.valor = valor_;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.doble;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    retorno_final.valor = valor_;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.doble;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                                    int valor_ = retorno_final.valor.toString().charAt(0);
                                    Double valor__ = Double.valueOf(Integer.toString(valor_));
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.doble;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                }
                            } else if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    char valor_ = (char) Integer.parseInt(retorno_final.valor.toString());
                                    String valor__ = String.valueOf(valor_);
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.caracter;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

                                    retorno_final.valor = retorno_final.valor;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.caracter;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                }
                            } else if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {

                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    int valor_ = Integer.parseInt(retorno_final.valor.toString());
                                    Boolean valor__ = valor_ > 0;
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.booleano;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                                    char valor_ = (char) Integer.parseInt(retorno_final.valor.toString());
                                    int valor__ = valor_;
                                    Boolean valor___ = valor__ > 0;
                                    retorno_final.valor = valor___;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.booleano;

                                    Display.display.removeFirst();
                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                                    retorno_final.valor = retorno_final.valor;
                                    Display.display.removeFirst();

                                    return retorno_final;

                                }
                            }
                            Errores.agregar_error("Los tipo de la funcion no coinciden ni con casteo implicito ", fila, columna, "Semantico");
                            Display.display.removeFirst();
                            return error;
                        }
                    }
                } else if (valor_retorno instanceof CONTINUAR) {
                } else if (valor_retorno instanceof DETENER) {
                }

            } else if (funcion_recuperada.tipo.tipo != Tipo.TipoSimbolo.vacio) {
                Errores.agregar_error("Deberia de tener porlomenos un REGRESAR la llamada a funcion", fila, columna, "Semantico");
                Display.display.removeFirst();
                return error;
            }
            /**
             * POR SI ES UN METODO VOID O ZRO EN ESTE CASO DISMINUIR EL DISPLAY
             */
            Display.display.removeFirst();
            return error;
        }
        simbolo_buscado = tablasimbolos.buscarsinreportar("BOTON_" + identificador, fila, columna);
        if (simbolo_buscado != null) {
            FUNCION funcion_recuperada = (FUNCION) simbolo_buscado.Valor;
            Entorno localfuncion = new Entorno(tablasimbolos);
            if (funcion_recuperada != null) {
                for (int i = 0; i < valores_ejecutados.size(); i++) {
                    if (funcion_recuperada.parametros.get(i) instanceof DECLARACION) {
                        DECLARACION temporal_parametro = (DECLARACION) funcion_recuperada.parametros.get(i);
                        temporal_parametro.expresion = valores_ejecutados.get(i);
                        temporal_parametro.Ejecutar(localfuncion);
                    } else if (funcion_recuperada.parametros.get(i) instanceof DECLARACION_ARREGLO) {
                        DECLARACION_ARREGLO temporal_parametro = (DECLARACION_ARREGLO) funcion_recuperada.parametros.get(i);
                        temporal_parametro.expresion = valores_ejecutados.get(i);
                        temporal_parametro.Ejecutar(localfuncion);
                    }
                }
            }
            Display.display.addFirst(Display.Tipo_Instruccion.funcion);
            String temporal = Display.ruta_archivo;
            Display.ruta_archivo = funcion_recuperada.nombre_archivo_origen;
            Object valor_retorno = ejecutar_Instrucciones(localfuncion, funcion_recuperada.instruciones);
            Display.ruta_archivo = temporal;

            if (valor_retorno != null) {
                if (valor_retorno instanceof RETORNAR) {
                    if (funcion_recuperada.tipo.tipo == Tipo.TipoSimbolo.vacio) {
                        Errores.agregar_error("La funcion buscada no deberia de retornar ningun valor", fila, columna, "Semantico");
                        Display.display.removeFirst();
                        return error;
                    }
                    RETORNAR valor = (RETORNAR) valor_retorno;
                    if (valor.valor_de_retorno != null) {
                        Expresion retorno_final = (Expresion) valor.valor_de_retorno;

                        if (retorno_final.tipo.tipo == Tipo.TipoSimbolo.objeto) {
                            DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) retorno_final;
                            Simbolo sim = (Simbolo) temp.simbolo_retorno;
                            if (sim.Tipo.tipo_Compuesto.equals("ARREGLO")) {
                                if (sim.dimensiones == funcion_recuperada.dimensiones) {
                                    OBJETO_ARREGLO temp_ = (OBJETO_ARREGLO) sim.Valor;
                                    if (sim.tipo_datos.tipo == funcion_recuperada.tipo.tipo
                                            && sim.tipo_datos.tipo_Compuesto.equals(funcion_recuperada.tipo.tipo_Compuesto)) {
                                        Display.display.removeFirst();
                                        return retorno_final;
                                    }
                                    //son arreglos y son iguales de dimensiones pero el tipo que regresa el objeto no es el mismo
                                    Errores.agregar_error("NO coinciden el tipo de retorno con tipo de la funcion", fila, columna, "Semantico");
                                    Display.display.removeFirst();
                                    return error;
                                } else {
                                    //error
                                    Errores.agregar_error("NO coinciden el tipo de retorno con tipo de la funcion por las dimensiones de la misma", fila, columna, "Semantico");
                                    Display.display.removeFirst();
                                    return error;
                                }
                            } else {

                                if (Tipo.TipoSimbolo.objeto == funcion_recuperada.tipo.tipo
                                        && retorno_final.valor == null) {
                                    Display.display.removeFirst();
                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo == funcion_recuperada.tipo.tipo
                                        && retorno_final.tipo.tipo_Compuesto.equals(funcion_recuperada.tipo.tipo_Compuesto)) {
                                    Display.display.removeFirst();
                                    return retorno_final;
                                } else {
                                    Errores.agregar_error("NO coinciden el tipo de retorno con tipo de la funcion", fila, columna, "Semantico");
                                    Display.display.removeFirst();
                                    return error;
                                }
                            }
                        } else {
                            //PRMITIVO

                            if (retorno_final.tipo.tipo == funcion_recuperada.tipo.tipo) {
                                Display.display.removeFirst();
                                return retorno_final;
                            }
                            if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    int valor__ = valor_.intValue();
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.entero;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    int valor__ = valor_.intValue();
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.entero;
                                    Display.display.removeFirst();

                                    return retorno_final;

                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                                    int valor__ = retorno_final.valor.toString().charAt(0);
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.entero;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                }
                            } else if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {

                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    retorno_final.valor = valor_;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.doble;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                                    Double valor_ = Double.valueOf(retorno_final.valor.toString());
                                    retorno_final.valor = valor_;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.doble;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                                    int valor_ = retorno_final.valor.toString().charAt(0);
                                    Double valor__ = Double.valueOf(Integer.toString(valor_));
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.doble;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                }
                            } else if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    char valor_ = (char) Integer.parseInt(retorno_final.valor.toString());
                                    String valor__ = String.valueOf(valor_);
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.caracter;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

                                    retorno_final.valor = retorno_final.valor;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.caracter;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                }
                            } else if (funcion_recuperada.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {

                                if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                                    int valor_ = Integer.parseInt(retorno_final.valor.toString());
                                    Boolean valor__ = valor_ >= 0;
                                    retorno_final.valor = valor__;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.booleano;
                                    Display.display.removeFirst();

                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                                    char valor_ = (char) Integer.parseInt(retorno_final.valor.toString());
                                    int valor__ = valor_;
                                    Boolean valor___ = valor__ >= 0;
                                    retorno_final.valor = valor___;
                                    retorno_final.tipo.tipo = Tipo.TipoSimbolo.booleano;

                                    Display.display.removeFirst();
                                    return retorno_final;
                                } else if (retorno_final.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                                    retorno_final.valor = retorno_final.valor;
                                    Display.display.removeFirst();

                                    return retorno_final;

                                }
                            }
                            Errores.agregar_error("Los tipo de la funcion no coinciden ni con casteo implicito ", fila, columna, "Semantico");
                            Display.display.removeFirst();
                            return error;
                        }
                    }
                } else if (valor_retorno instanceof CONTINUAR) {
                } else if (valor_retorno instanceof DETENER) {
                }

            } else if (funcion_recuperada.tipo.tipo != Tipo.TipoSimbolo.vacio) {
                Errores.agregar_error("Deberia de tener porlomenos un REGRESAR la llamada a funcion", fila, columna, "Semantico");
                Display.display.removeFirst();
                return error;
            }
            Display.display.removeFirst();
            return error;
        }

        Errores.agregar_error("NO existe una funcion con tal cual satisfaga el nombre de \"" + identificador + "\" y los parametros usados", fila, columna, "Semantico");
        return error;
    }

}
