/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.Condicional;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
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
public class SI extends Instruccion {

    Expresion condicion;
    LinkedList<Instruccion> instrucciones;
    Instruccion si_anidado;
    Boolean si_else;

    public SI(Expresion condicion, LinkedList<Instruccion> instrucciones, Instruccion si_anidado, Boolean si_else, int fila, int columna) {
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.si_anidado = si_anidado;
        this.si_else = si_else;
        this.columna = columna;
        this.fila = fila;
    }

    public SI(Expresion condicion, LinkedList<Instruccion> instrucciones, int fila, int columna) {
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.si_anidado = null;
        this.si_else = false;
        this.columna = columna;
        this.fila = fila;
    }
    int columna;
    int fila;

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

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        Expresion Valor = (Expresion) this.condicion.Ejecutar(tablasimbolos);
        if (Valor == null) {
            return null;
        } else if (!(Valor.valor instanceof Boolean)) {
            //ERROR TIENE QUE SER BOOLEANO
            if (!(Valor instanceof DEVOLVER_SIMBOLO)) {

                return null;
            }

        }
        boolean respuesta = false;
        if ((Valor instanceof DEVOLVER_SIMBOLO)) {
            DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) Valor;
            respuesta = Boolean.parseBoolean(sim.simbolo_retorno.Valor.toString());
        } else {
            respuesta = Boolean.parseBoolean(Valor.valor.toString());
        }
        if ((respuesta) || si_else) {
            Entorno Local = new Entorno(tablasimbolos);
            if (instrucciones != null) {

                Object retorno = ejecutarInstrucciones(Local);
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
        } else {
            if (si_anidado != null) {
                Object retorno = si_anidado.Ejecutar(tablasimbolos);
                if (retorno != null) {
                    if (retorno instanceof DETENER) {
                        return retorno;
                    } else if (retorno instanceof CONTINUAR) {
                        return retorno;
                    } else if (retorno instanceof RETORNAR) {
                        return retorno;
                    }
                }
            }
            return null;
        }
    }

}
