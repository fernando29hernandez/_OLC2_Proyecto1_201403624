/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.Condicional;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.LLAMADA_FUNCION;
import Arbol.Expresiones.Relacionales.IGUALIGUAL;
import Arbol.Instrucciones.General.CONTINUAR;
import Arbol.Instrucciones.General.DETENER;
import Arbol.Instrucciones.General.RETORNAR;
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class SELECCIONA extends Instruccion {

    Expresion exp;
    LinkedList<Instruccion> listacasos;
    int columna;
    int fila;

    public SELECCIONA(Expresion exp, LinkedList<Instruccion> listacasos, int fila, int columna) {
        this.exp = exp;
        this.listacasos = listacasos;
        this.columna = columna;
        this.fila = fila;
    }

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

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        Boolean entrar_condicion_defecto = true;
        Boolean Cumplio_condicion_caso = false;
        if (listacasos != null) {
            Display.display.addFirst(Display.Tipo_Instruccion.seleccciona);
            Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
            if (resultado == null) {
                Errores.agregar_error("No se puede entrar a los casos de switch porque la expresion no es valida", fila, columna, "Semantico");
                return null;
            }
            Entorno tablalocal = new Entorno(tablasimbolos);
            for (Instruccion caso : listacasos) {
                CASO caso_separado = (CASO) caso;
                if (!caso_separado.es_defecto || Cumplio_condicion_caso) {
                    if (!Cumplio_condicion_caso) {
                        Expresion condicion_selecciona = (Expresion) caso_separado.expresion.Ejecutar(tablasimbolos);
                        IGUALIGUAL comparacionCaso = new IGUALIGUAL(resultado, condicion_selecciona, "==", caso_separado.fila, caso_separado.columna);
                        if (Boolean.parseBoolean(((Expresion) comparacionCaso.Ejecutar(tablasimbolos)).valor.toString())) {
                            if (entrar_condicion_defecto) {
                                entrar_condicion_defecto = false;
                            }
                            Cumplio_condicion_caso = true;
                            //ENTORNOS SEPARADOS PARA CADA CAW
                            Entorno Local = new Entorno(tablasimbolos);
                            if (caso_separado.lista_instrucciones != null) {

                                Object retorno = ejecutar_Instrucciones(Local, caso_separado.lista_instrucciones);
                                if (retorno != null) {
                                    if (retorno instanceof DETENER) {
                                        return retorno;
                                    } else if (retorno instanceof CONTINUAR) {
                                        return retorno;
                                    } else if (retorno instanceof RETORNAR) {
                                        return retorno;
                                    }
                                }
                                return null; //Si no vino un return devuelvo nulo

                            }
                            return null;
                        }
                    } else {
                        Entorno Local = new Entorno(tablasimbolos);
                        if (caso_separado.lista_instrucciones != null) {

                            Object retorno = ejecutar_Instrucciones(Local, caso_separado.lista_instrucciones);
                            if (retorno != null) {
                                if (retorno instanceof DETENER) {
                                    return retorno;
                                } else if (retorno instanceof CONTINUAR) {
                                    return retorno;
                                } else if (retorno instanceof RETORNAR) {
                                    return retorno;
                                }
                            }
                            return null; //Si no vino un return devuelvo nulo

                        }
                        return null;
                    }
                }
            }
            Cumplio_condicion_caso = false;
            if (entrar_condicion_defecto) {
                for (Instruccion caso : listacasos) {
                    CASO caso_separado_default = (CASO) caso;
                    if (caso_separado_default.es_defecto || Cumplio_condicion_caso) {
                        Cumplio_condicion_caso = true;
                        Entorno Local = new Entorno(tablasimbolos);
                        if (caso_separado_default.lista_instrucciones != null) {

                            Object retorno = ejecutar_Instrucciones(Local, caso_separado_default.lista_instrucciones);
                            if (retorno != null) {
                                if (retorno instanceof DETENER) {
                                    return retorno;
                                } else if (retorno instanceof CONTINUAR) {
                                    return retorno;
                                } else if (retorno instanceof RETORNAR) {
                                    return retorno;
                                }
                            }
                            return null; //Si no vino un return devuelvo nulo

                        }
                        return null;
                    }
                }
            }
            Display.display.removeFirst();
            return null;
        } else {
            return null;
        }
    }
}
