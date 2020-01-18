/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Entorno;
import Arbol.Expresiones.Expresion;
import Arbol.Instrucciones.Instruccion;

/**
 *
 * @author Fernando
 */
public class INSACCESO extends Instruccion{

    public INSACCESO(Expresion expresion) {
        this.expresion = expresion;
    }
    Expresion expresion;
    
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        expresion.Ejecutar(tablasimbolos);
        return null;
    }
    
}
