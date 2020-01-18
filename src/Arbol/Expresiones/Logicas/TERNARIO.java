/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones.Logicas;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.Expresion;

/**
 *
 * @author Fernando
 */
public class TERNARIO extends Expresion{
    Expresion expresion1;
    Expresion expresion2;
    Expresion expresion3;
    int fila;
    int columna;
    public TERNARIO(Expresion expresion1, Expresion expresion2, Expresion expresion3,int fila,int columna)
    {
        this.expresion1 = expresion1;
        this.expresion2 = expresion2;
        this.expresion3 = expresion3;
        this.fila = fila;
        this.columna = columna;
    }
    

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
    Expresion expresultado = (Expresion)expresion1.Ejecutar(tablasimbolos);
        if (expresion1 == null)
        {
            return null;
        }
        if (!(expresultado.tipo.tipo == Tipo.TipoSimbolo.booleano)){
            return null;
        }
        if ((Boolean.parseBoolean(expresultado.valor.toString())))
        {
            return this.expresion2.Ejecutar(tablasimbolos);
        }
        else
        {
            return this.expresion3.Ejecutar(tablasimbolos);
        }
    }
}