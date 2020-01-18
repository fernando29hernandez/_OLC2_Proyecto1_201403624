/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Simbolo;

/**
 *
 * @author Fernando
 */
public class DEVOLVER_SIMBOLO extends Expresion {
    public Simbolo simbolo_retorno;
    public Boolean referencia;
    public DEVOLVER_SIMBOLO(Simbolo simbolo_retorno)
    {
        this.simbolo_retorno = simbolo_retorno;
        this.tipo = simbolo_retorno.Tipo;
        this.valor = simbolo_retorno.Valor;
        this.referencia=false;
    }
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
     return this;
    }
}
