/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC.Instrucciones;

import Arbol_OLC.Entorno.Entorno;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class ARCHIVO extends Instruccion {

    LinkedList<Instruccion> valores;
    public int fila;
    public int columna;
    public String nombre;
    public String fecha;
    public String ruta_relativa;
    public String ruta_absoluta;
    public LinkedList<String>rutas = new LinkedList();
    public ARCHIVO(LinkedList<Instruccion> valores, int fila, int columna) {
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
        this.ruta_relativa="";
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos,String cadena) {
        for(Instruccion ins: valores)
        {
            PROPIEDAD prop = (PROPIEDAD)ins;
            if(prop.atributo.equals("nombre"))
            {
                 nombre =prop.valor;
                 rutas.addLast(nombre);
                 //ruta_relativa =prop.valor;
            }   
            else if(prop.atributo.equals("fecha_mod"))
            {
                 fecha = prop.valor;
                 //ruta_relativa =prop.valor;
            }
        }
        ruta_absoluta = cadena+nombre;
        return this;
    }
}
