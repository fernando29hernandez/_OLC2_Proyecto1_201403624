/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.Iterativa;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.LLAMADA_FUNCION;
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
public class WHILE extends Instruccion {

    Expresion condicion;
    LinkedList<Instruccion> instrucciones;
    int columna;
    int fila;

    /**
     * *
     *
     * @param condicion
     * @param instrucciones
     * @param fila
     * @param columna
     */
    public WHILE(Expresion condicion, LinkedList<Instruccion> instrucciones, int fila, int columna) {
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.columna = columna;
        this.fila = fila;
    }

    /**
     * Metodo utilizado para cuando venga un continue se rompa la secuencia de
     * instrucciones y ya no se ejecute nada mas
     *
     */
    /**
     *
     * @param local
     * @return
     */

    public Object ejecutarInstrucciones(Entorno local) {

        if (instrucciones != null) {

            int i = 0;
            while (i < instrucciones.size()) {
                /**
                 * *
                 * NINGUNA ACTIVA ESO QUIERE DECIR QUE ESTOY EN EL HILO
                 * PRINCIPAL
                 */
                if (Display.Bandera_ACTIVA == false && Display.Bandera_CLICK == false) {
                    Instruccion ins = instrucciones.get(i);

                    Object retorno = (((Instruccion) ins).Ejecutar(local));

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
                    Instruccion ins = instrucciones.get(i);

                    Object retorno = (((Instruccion) ins).Ejecutar(local));

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

    /* 
     * @param tablasimbolos
     * @return 
     */
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        Expresion condicion_resultado = (Expresion) condicion.Ejecutar(tablasimbolos);
        if (condicion_resultado != null) {
            if (condicion_resultado.tipo.tipo == Tipo.TipoSimbolo.booleano) {
                Display.display.addFirst(Display.Tipo_Instruccion.for_simple);
                while (Boolean.parseBoolean(condicion_resultado.valor.toString())) {
                    Entorno localmientras;

                    localmientras = new Entorno(tablasimbolos);

                    Instruccion resultado = (Instruccion) ejecutarInstrucciones(localmientras);
                    if (resultado != null) {
                        if (resultado instanceof DETENER) {
                            Display.display.removeFirst();
                            return null;
                        } else if (resultado instanceof CONTINUAR) {
                            // como ya estoy en el nivel del ciclo simplemente no hago nada,dado que, ya rompi a nivel de instrucciones
                        } else if (resultado instanceof RETORNAR) {
                            Display.display.removeFirst();
                            return resultado;
                        }
                    }

                    condicion_resultado = (Expresion) condicion.Ejecutar(tablasimbolos);
                    if (condicion_resultado.tipo.tipo != Tipo.TipoSimbolo.booleano) {
                        Display.display.removeFirst();
                        return null;
                    }
                    //if (resultado is CONTINUAR) { continue; }
                }
                Display.display.removeFirst();
                return null;
            }

        }
        return null;
    }
}
