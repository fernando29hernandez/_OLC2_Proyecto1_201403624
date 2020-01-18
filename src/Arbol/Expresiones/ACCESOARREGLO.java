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
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class ACCESOARREGLO extends Expresion {
    String identificador;
    LinkedList<Expresion> dimensiones_explicitas;
    int fila;
    int columna;
    Entorno fuera;
    public ACCESOARREGLO(String identificador, LinkedList<Expresion> dimensiones_explicitas, int fila, int columna) {
        this.identificador = identificador;
        this.dimensiones_explicitas = dimensiones_explicitas;
        this.fila = fila;
        this.columna = columna;
    }
    
    public Expresion BUSQUEDA_ARBOL(int dimensionActual, LinkedList<Integer> valores_indices, OBJETO_ARREGLO arreglo){
     int indice = valores_indices.get(dimensionActual - 1); 
        if (dimensionActual == valores_indices.size()) { 
            if (indice >= arreglo.lista_elementos.size()) { 
                Errores.agregar_error("La posicion que se intenta acceder esta fuera de los limites del arreglo", fila, columna, "Semantico");
                return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "Error",0,0);
            }
            Simbolo sim = arreglo.lista_elementos.get(indice);
            return new DEVOLVER_SIMBOLO(sim);
        }
        
        if (indice >= arreglo.lista_elementos.size()) { 
            Errores.agregar_error("La posicion que se intenta acceder esta fuera de los limites del arreglo", fila, columna, "Semantico");
            return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "Error",0,0);
        }
        OBJETO_ARREGLO obj = (OBJETO_ARREGLO) arreglo.lista_elementos.get(indice).Valor; 
        return BUSQUEDA_ARBOL(dimensionActual + 1, valores_indices,obj); 
    
    }
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        return getAcceso(tablasimbolos,tablasimbolos);
    }
    
    public Object getAcceso(Entorno tablasimbolos,Entorno fueraacceso)
    {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error",0,0);

        Simbolo sim = tablasimbolos.buscar(identificador, fila, columna);
        if (sim != null) { 
            if (sim.Tipo.tipo == Tipo.TipoSimbolo.objeto) {
                if (sim.Valor == null) {
                    Errores.agregar_error("No se puede acceder a este arreglo", fila, columna, "Semantico");
                    return error;
                }

                if (sim.Tipo.tipo_Compuesto.equals("ARREGLO")) { 
                    OBJETO_ARREGLO arreglo = (OBJETO_ARREGLO)sim.Valor;
                    if (dimensiones_explicitas.size() > arreglo.dimensiones) {
                        Errores.agregar_error("El arreglo no cuenta con el numero de dimensiones el cual se intenta acceder", fila, columna, "Semantico");
                        return error;
                    }
                    LinkedList<Integer> valores_indices = new LinkedList<>(); 
                    int valorentero;
                    for (Expresion expresion : dimensiones_explicitas) {
                        Expresion resultado = (Expresion)expresion.Ejecutar(fueraacceso);
                        if (resultado.tipo.tipo != Tipo.TipoSimbolo.entero) { 
                            Errores.agregar_error("No se puede acceder a una dimension con un tipo que no sea un entero", fila, columna, "Semantico");
                            return error; 
                        }
                        valorentero = Integer.parseInt(resultado.valor.toString());
                        if (valorentero < 0) { 
                            Errores.agregar_error("No se puede acceder a una dimension con un indice negativo", fila, columna, "Semantico");
                            return error; 
                        }
                        valores_indices.add(valorentero);
                    }
                    return BUSQUEDA_ARBOL(1, valores_indices, arreglo);
                }
            }
            Errores.agregar_error("La variable accedida no es un arreglo", fila, columna, "Semantico");
            return error; 
        }
        return error;
    
    }
    
}
