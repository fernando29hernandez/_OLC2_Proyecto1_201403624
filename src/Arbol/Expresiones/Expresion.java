/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Tipo;
import Arbol.Nodo_R;

/**
 *
 * @author Fernando
 */
public abstract class Expresion extends Nodo_R {
    public Tipo tipo;
    public Object valor;    
}
