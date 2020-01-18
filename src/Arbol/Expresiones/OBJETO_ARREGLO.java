/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class OBJETO_ARREGLO extends Expresion{
    public Tipo tipoDatos; 
    public LinkedList<Simbolo> lista_elementos; 
    public int dimensiones;  

    /**
     * @param tipoDatos me dice de qué tipo son los datos contenidos en el
     * arreglo (entero, doble, caracter)
     * @param dimensiones me dice el número de dimensiones de este arreglo
     */
    public OBJETO_ARREGLO(Tipo tipoDatos, int dimensiones) {
        this.tipoDatos = tipoDatos;
        this.dimensiones = dimensiones;
        this.lista_elementos = new LinkedList<>(); //Inicializo una lista vacía
        String cadena = "Arreglo_" + tipoDatos.tipo;
        for (int i = 0; i < dimensiones; i++) {
            cadena += "[]";
        }
        this.valor = cadena;
        this.tipo = new Tipo(Tipo.TipoSimbolo.objeto,"ARREGLO");
        this.tipo.dimensiones=dimensiones;
 
    }
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        return this;
    }
}
