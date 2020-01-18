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
public class FOR extends Instruccion{
    Instruccion declaracion;
    Expresion comparacion;
    Instruccion actualizacion;
    LinkedList<Instruccion> sentencias;
    int columna;
    int fila;
    public FOR(Instruccion declaracion, Expresion comparacion, Instruccion actualizacion, LinkedList<Instruccion> sentencias, int fila, int columna)
    {
        this.declaracion = declaracion;
        this.comparacion = comparacion;
        this.actualizacion = actualizacion;
        this.sentencias = sentencias;
        this.columna = columna;
        this.fila = fila;
    }

   public Object ejecutarInstrucciones(Entorno local) {

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
                    Instruccion ins = sentencias.get(i);

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
        Entorno local_for_simple = new Entorno(tablasimbolos);
        Instruccion inicializar__variable_control = (Instruccion)declaracion.Ejecutar(local_for_simple);
        Expresion condicion_resultado = (Expresion)comparacion.Ejecutar(local_for_simple);
        if (condicion_resultado!=null)
        {
            if (condicion_resultado.tipo.tipo.equals(Tipo.TipoSimbolo.booleano))
            {
                Display.display.addFirst(Display.Tipo_Instruccion.for_simple);
                while (Boolean.parseBoolean(condicion_resultado.valor.toString()))
                {
                    Entorno local_ejecucion_iteracion;

                    local_ejecucion_iteracion = new Entorno(local_for_simple);

                    Instruccion resultado = (Instruccion)ejecutarInstrucciones(local_ejecucion_iteracion);
                    if (resultado != null)
                    {
                        if (resultado instanceof DETENER)
                        {
                            Display.display.pop();
                            return null;
                        }
                        else if (resultado instanceof CONTINUAR)
                        {
                            // como ya estoy en el nivel del ciclo simplemente no hago nada,dado que, ya rompi a nivel de instrucciones
                        }
                        else if (resultado instanceof RETORNAR)
                        {
                            Display.display.pop();
                            return resultado;
                        }
                    }
                    actualizacion.Ejecutar(local_ejecucion_iteracion);
                    condicion_resultado = (Expresion)comparacion.Ejecutar(local_ejecucion_iteracion);
                    if (condicion_resultado.tipo.tipo != Tipo.TipoSimbolo.booleano)
                    {
                        Display.display.pop();
                        return null;
                    }
                    //if (resultado is CONTINUAR) { continue; }
                }
                Display.display.pop();
                return null;

            }
            return null;
        }
        return null;
    }
}
