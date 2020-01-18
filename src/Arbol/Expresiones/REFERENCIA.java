/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Tipo;

/**
 *
 * @author Fernando
 */
public class REFERENCIA extends Expresion{
    Expresion expresion;
    String operacion;
    int fila;
    int columna;

    public REFERENCIA(String operacion,Expresion expresion,  int fila, int columna) {
        this.expresion = expresion;
        this.operacion = operacion;
        this.fila = fila;
        this.columna = columna;
    }
    
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
        
        Expresion exp = (Expresion)expresion.Ejecutar(tablasimbolos);
        DEVOLVER_SIMBOLO temp;
        if(exp instanceof DEVOLVER_SIMBOLO)
        {
            temp = (DEVOLVER_SIMBOLO)exp;
            
            temp.referencia=true;
            return temp;
        }
        Errores.agregar_error("No es posible acceder a la referencia de dicha variable", fila, columna, "Semantica");
        return error;
    }
    
}
