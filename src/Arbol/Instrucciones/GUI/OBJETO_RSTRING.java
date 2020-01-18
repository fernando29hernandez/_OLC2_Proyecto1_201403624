/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.GUI;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.Expresion;

/**
 *
 * @author Fernando
 */
public class OBJETO_RSTRING extends Expresion {

    public  String cadena;

    public OBJETO_RSTRING(String cadena, Tipo tipo) {
        this.cadena = cadena;
        this.tipo = tipo;
        this.valor = this.cadena;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        return this;
    }

}
