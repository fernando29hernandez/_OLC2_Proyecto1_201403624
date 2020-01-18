/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.Condicional;

import Arbol.Entorno.Entorno;
import Arbol.Expresiones.Expresion;
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class CASO extends Instruccion{
    public Expresion expresion;
    public LinkedList<Instruccion> lista_instrucciones;
    public Boolean es_defecto;
    public int columna;
    public int fila;

    public CASO(Expresion expresion, LinkedList<Instruccion> lista_instrucciones,Boolean es_defecto, int fila,int columna)

    {
        this.expresion = expresion;
        this.lista_instrucciones = lista_instrucciones;
        this.es_defecto = es_defecto;
        this.columna = columna;
        this.fila = fila;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        return null;
    }

}
