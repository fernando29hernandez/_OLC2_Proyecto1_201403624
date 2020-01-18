/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Entorno;

/**
 *
 * @author Fernando
 */
public class Error {

    public Error(String descripcion, int fila, int columna, String tipo,String ruta) {
        this.descripcion = descripcion;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.ruta=ruta;
    }
    
    public String descripcion;
    public int fila;
    public int columna;
    public String tipo;
    public String ruta;
}
