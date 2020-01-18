/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Tipo;

/**
 *
 * @author Fernando
 */
public class PRIMITIVO extends Expresion {

    int columna;
    int fila;
    public PRIMITIVO(Tipo tipo,Object valor, int fila, int columna)
    {
        this.tipo = tipo;
        this.tipo.dimensiones=0;
        this.valor = valor;
        this.columna = columna;
        this.fila = fila;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        if(tipo.tipo==Tipo.TipoSimbolo.booleano)
        {
            this.valor=Boolean.parseBoolean(this.valor.toString());
        }
        return this;
    }
    
}
