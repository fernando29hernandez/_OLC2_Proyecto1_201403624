/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;

/**
 *
 * @author Fernando
 */
public class ACCESO extends Expresion{
    Expresion op1;
    Expresion op2;
    int fila;
    int columna;

    public ACCESO(Expresion expresion, Expresion expresion2, int fila, int columna) {
        this.op1 = expresion;
        this.op2 = expresion2;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
        
        Expresion resultado = (Expresion)op1.Ejecutar(tablasimbolos);
        if (resultado != null)
        {
            if (resultado instanceof DEVOLVER_SIMBOLO)
            {
                DEVOLVER_SIMBOLO primeracceso = (DEVOLVER_SIMBOLO)resultado;
                Simbolo simbolo_recuperado = primeracceso.simbolo_retorno;
                if (simbolo_recuperado != null)
                {
                    if (op2 instanceof FUNCION_NATIVA)
                    {
                        // SE LO QUITE AL IF PORQUE NO DEJBA PASAR AL METODO
                        //&&simbolo_recuperado.Tipo.tipo_comp!=Tipo.TipoComponente.rstring
                        
                        if (simbolo_recuperado.Tipo.tipo == Tipo.TipoSimbolo.componente)
                        {
                            Object padre = (Object)simbolo_recuperado.Valor;
                            FUNCION_NATIVA nuevo_acceso = (FUNCION_NATIVA)op2;
                            nuevo_acceso.setComponente(padre);
                            Object resultado_acceso = nuevo_acceso.Ejecutar(tablasimbolos);
                            return resultado_acceso;
                        }
                    }
                    else if (op2 instanceof ID)
                    {
                        if (simbolo_recuperado.Tipo.tipo == Tipo.TipoSimbolo.objeto)
                        {
                            OBJETO padre = (OBJETO)simbolo_recuperado.Valor;
                            if(padre==null)
                            {
                                return error;
                            }
                            ID nuevo_acceso = (ID)op2;
                            Object resultado_acceso = nuevo_acceso.Ejecutar(padre.entorno_struct);
                            return resultado_acceso;
                        }
                    }
                    else if (op2 instanceof ACCESOARREGLO)
                    {
                        if (simbolo_recuperado.Tipo.tipo == Tipo.TipoSimbolo.objeto)
                        {
                            OBJETO padre = (OBJETO)simbolo_recuperado.Valor;
                            ACCESOARREGLO nuevo_acceso = (ACCESOARREGLO)op2;
                            Object resultado_acceso = nuevo_acceso.getAcceso(padre.entorno_struct,tablasimbolos);
                            return resultado_acceso;
                        }
                    }
                }
            }
            else if (resultado instanceof PRIMITIVO)
            {
                if (op2 instanceof FUNCION_NATIVA)
                {
                    
                }
                return error;
            }
            return error;
        }
        return error;
    }
    
}