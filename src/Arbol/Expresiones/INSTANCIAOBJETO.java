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
public class INSTANCIAOBJETO extends Expresion{
    public int tamano_memoria_apartada;
    
    public INSTANCIAOBJETO(int tamano_memoria_apartada) {
        this.tamano_memoria_apartada = tamano_memoria_apartada;
        this.tipo= new Tipo(Tipo.TipoSimbolo.instancia);
        this.valor = tamano_memoria_apartada;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        return this;
    }
    
}
