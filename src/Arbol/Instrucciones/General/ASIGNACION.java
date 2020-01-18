/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tamano;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.INSTANCIAOBJETO;
import Arbol.Expresiones.OBJETO;
import Arbol.Expresiones.OBJETO_ARREGLO;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.GUI.OBJETO_RSTRING;
import static Arbol.Instrucciones.General.DECLARACION_ARREGLO.CREAR_FALTANTES;
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class ASIGNACION extends Instruccion {

    Expresion acceso;
    Expresion expresion;
    int fila;
    int columna;

    public ASIGNACION(Expresion acceso, Expresion expresion, int fila, int columna) {
        this.acceso = acceso;
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        //impr escribir
        // nativas
        //probar todo
        Expresion variable = (Expresion) acceso.Ejecutar(tablasimbolos);
        Expresion asignacion = (Expresion) expresion.Ejecutar(tablasimbolos);

        if (!(variable instanceof DEVOLVER_SIMBOLO)) {
            Errores.agregar_error("No es posible la asignacion dado a que el lado izquierdo no es valido", fila, columna, "Semantico");
            return null;

        }
        DEVOLVER_SIMBOLO asignable = (DEVOLVER_SIMBOLO) variable;
        Simbolo simbolo_asignable = asignable.simbolo_retorno;
        if (simbolo_asignable.constante) {
            Errores.agregar_error("No se puede modificar el valor de una constante", fila, columna, "Semantico");
            return null;
        }
        if (simbolo_asignable.Tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
            if (asignacion.valor == null) {
                Errores.agregar_error("No se puede realizar porque la asignacion de valor es incorrecta", fila, columna, "Semantico");
                return null;
            }
            if (asignacion instanceof DEVOLVER_SIMBOLO) {
                DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) asignacion;
                if (sim.referencia) {
                    if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                        simbolo_asignable = sim.simbolo_retorno;
                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                        //error
                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                        //error 
                        return null;
                    }

                }

            }
            if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                Double valor_ = Double.valueOf(asignacion.valor.toString());
                int valor__ = valor_.intValue();
                simbolo_asignable.Valor = valor__;
                return null;
            } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                Double valor_ = Double.valueOf(asignacion.valor.toString());
                int valor__ = valor_.intValue();
                simbolo_asignable.Valor = valor__;
                return null;
            } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                int valor__ = asignacion.valor.toString().charAt(0);
                simbolo_asignable.Valor = valor__;
                return null;
            }
        } else if (simbolo_asignable.Tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
            if (asignacion == null) {
                Errores.agregar_error("No se puede realizar porque la asignacion de valor es incorrecta", fila, columna, "Semantico");
                return null;
            }
            if (asignacion instanceof DEVOLVER_SIMBOLO) {
                DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) asignacion;
                if (sim.referencia) {
                    if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                        simbolo_asignable = sim.simbolo_retorno;
//error
                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                        //error 
                        return null;
                    }

                }

            }
            if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                Double valor_ = Double.valueOf(asignacion.valor.toString());
                simbolo_asignable.Valor = valor_;
                return null;
            } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                Double valor_ = Double.valueOf(asignacion.valor.toString());
                simbolo_asignable.Valor = valor_;
                return null;
            } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                int valor_ = asignacion.valor.toString().charAt(0);
                Double valor__ = Double.valueOf(Integer.toString(valor_));
                simbolo_asignable.Valor = valor__;
                return null;
            }
            //error
            return null;
        } else if (simbolo_asignable.Tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

            if (asignacion == null) {
                Errores.agregar_error("No se puede declarar esta variable porque la asignacion es incorrecta", fila, columna, "Semantico");
                return null;
            }
            if (asignacion instanceof DEVOLVER_SIMBOLO) {
                DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) asignacion;
                if (sim.referencia) {
                    if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {

                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                        simbolo_asignable = sim.simbolo_retorno;
                        return null;
                    }

                }

            }
            if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                char valor_ = (char) Integer.parseInt(asignacion.valor.toString());
                String valor__ = String.valueOf(valor_);
                simbolo_asignable.Valor = valor__;
                return null;
            } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

                simbolo_asignable.Valor = asignacion.valor;
                return null;
            }
            //errorr
            return null;
        } else if (simbolo_asignable.Tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
            if (asignacion == null) {
                Errores.agregar_error("No se puede declarar esta variable porque la asignacion es incorrecta", fila, columna, "Semantico");
                return null;
            }
            if (asignacion instanceof DEVOLVER_SIMBOLO) {
                DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) asignacion;
                if (sim.referencia) {
                    if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {

                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                        //error 
                        return null;
                    } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                        simbolo_asignable = sim.simbolo_retorno;
                        return null;
                    }

                }

            }
            if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                int valor_ = Integer.parseInt(asignacion.valor.toString());
                Boolean valor__ = valor_ > 0;
                simbolo_asignable.Valor = valor__;
                return null;
            } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                char valor_ = (char) Integer.parseInt(asignacion.valor.toString());
                int valor__ = valor_;
                Boolean valor___ = valor__ > 0;
                simbolo_asignable.Valor = valor___;
                return null;
            } else if (asignacion.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                simbolo_asignable.Valor = asignacion.valor;
                return null;
            }
            //error
            return null;
        } else if (simbolo_asignable.Tipo.tipo.equals(Tipo.TipoSimbolo.objeto)) {
            if (simbolo_asignable.Tipo.tipo_Compuesto.equals("ARREGLO")) {
                if (asignacion.tipo.tipo == Tipo.TipoSimbolo.objeto
                        && asignacion.tipo.tipo_Compuesto.equals("ARREGLO")) {
                    DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) asignacion;
                    Simbolo sim = (Simbolo) temp.simbolo_retorno;
                    if (sim.dimensiones == simbolo_asignable.dimensiones
                            && Tamano.comparar_listas(simbolo_asignable.tams, sim.tams, sim.Pertenece) == 1
                            && sim.tipo_datos.tipo == simbolo_asignable.tipo_datos.tipo) {
                        simbolo_asignable.Pertenece = false;
                        sim.Pertenece = false;
                        simbolo_asignable.Valor = sim.Valor;
                        if (sim.dimensiones == 1 && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter) {
                            // creo los faltantes
                            if (sim.tams.tamano_actual < simbolo_asignable.tams.tamano_actual) {
                                CREAR_FALTANTES((OBJETO_ARREGLO) sim.Valor, simbolo_asignable.tams.tamano_actual - sim.tams.tamano_actual);
                            }
                        }
                    } else {
                        Errores.agregar_error("No se puede realizar la asignacion dado que el arreglo de entrada no es compatible con que se esta asignando", fila, columna, "Semantico");
                        return null;
                    }

                } else {
                    Errores.agregar_error("No se puede realizar la asignacion dado el valor a asignar no es un arreglo", fila, columna, "Semantico");
                    return null;
                }

                return null;
            }
            Simbolo fusion_encontrado = tablasimbolos.buscar("FUSION_" + simbolo_asignable.Tipo.tipo_Compuesto, fila, columna);
            if (fusion_encontrado != null) {
                if (asignacion.tipo.tipo == Tipo.TipoSimbolo.objeto) {
                    if(asignacion.valor==null)
                        {
                        simbolo_asignable.Valor = null;
                            
                            return null;
                            
                        }
                    OBJETO varResultado = (OBJETO) asignacion.valor;
                    if (varResultado == null) {
                        simbolo_asignable.Valor = varResultado;
                        return null;
                    }
                    //TENGO QUE VER QUE PASA PORQUE YA HACE LO QUE QUIERO O ESO CREO PERO CREO QUE LOS SEGUNDOS ELEMENTOS NO LOS INSERTA
                    if (varResultado.tipo.tipo_Compuesto.equals(simbolo_asignable.Tipo.tipo_Compuesto)) {
                        simbolo_asignable.Valor = varResultado;
                        return null;
                    }
                    return null;
                } else if (asignacion.tipo.tipo == Tipo.TipoSimbolo.instancia) {
                    FUSION temporal = (FUSION) fusion_encontrado.Valor;
                    LinkedList<Instruccion> declaraciones_struct = temporal.variables;
                    INSTANCIAOBJETO ins = (INSTANCIAOBJETO) asignacion;
                    if (temporal.variables.size() != ins.tamano_memoria_apartada) {
                        Errores.agregar_error("NO se aparto la memoria correspondiente para instancia este objeto", fila, columna, "Semantico");
                        return null;
                    }
                    Entorno nuevo_ent = new Entorno(tablasimbolos);
                    OBJETO nuevo_objeto = new OBJETO(simbolo_asignable.Identificador, nuevo_ent);
                    nuevo_objeto.tipo=simbolo_asignable.Tipo;
                    for (int i = 0; i < declaraciones_struct.size(); i++) {
                        if (declaraciones_struct.get(i) instanceof DECLARACION) {
                            DECLARACION nueva_variable = (DECLARACION) declaraciones_struct.get(i);
                            nueva_variable.Ejecutar(nuevo_ent);
                        } else if (declaraciones_struct.get(i) instanceof DECLARACION_ARREGLO) {
                            DECLARACION_ARREGLO nueva_variable = (DECLARACION_ARREGLO) declaraciones_struct.get(i);
                            nueva_variable.Ejecutar(nuevo_ent);
                        }
                    }
                    simbolo_asignable.Valor = nuevo_objeto;
                    return null;

                } else if (asignacion.tipo.tipo == Tipo.TipoSimbolo.nulo) {
                    simbolo_asignable.Valor = null;
                    return null;

                }
                else if (asignacion.valor == null) {
                    simbolo_asignable.Valor = null;
                    return null;

                }
            } else {
                Errores.agregar_error("NO existe el una fusion con el nombre especificado para uso", fila, columna, "Semantico");
                return null;
            }

        } else if (simbolo_asignable.Tipo.tipo == Tipo.TipoSimbolo.componente) {
            if (simbolo_asignable.Tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                Expresion exp = asignacion;
                if (exp.tipo.tipo_Compuesto.equals("ARREGLO")) {
                    DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) exp;
                    Simbolo sim = (Simbolo) temp.simbolo_retorno;
                    if (sim.dimensiones == 1
                            && sim.Pertenece == true
                            && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter) {

                        sim.Pertenece = false;
                        TRANSFORMARENCADENA transform = new TRANSFORMARENCADENA(sim);
                        String valor__ = transform.transformar();
                        OBJETO_RSTRING cad = new OBJETO_RSTRING(valor__, sim.Tipo);
                        simbolo_asignable.Valor = cad;
                        return null;

                    } else {
                        Errores.agregar_error("No se puede llevar cabo de asignacion porque Rstring solo admite cadenas ", fila, columna, "Semantico");
                        return null;

                    }
                } else if (exp.tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                    OBJETO_RSTRING valor__ = (OBJETO_RSTRING)exp;
                    OBJETO_RSTRING cad = new OBJETO_RSTRING(valor__.cadena, exp.tipo);
                    simbolo_asignable.Valor = cad;
                    return null;

                } // aqui va el RSTRING ASIGNACION
                else {
                    Errores.agregar_error("El valor a asignar no es una cadena", fila, columna, "Semantico");
                    return null;

                }

            }
        }
        return null;
    }

}
