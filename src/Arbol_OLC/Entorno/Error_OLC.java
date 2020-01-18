/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC.Entorno;

/**
 *
 * @author Fernando
 */
public class Error_OLC {
    
    public String descripcio;
    public int fila;
    public int columna;
    public String tipo;
    public String nombre_archivo;

    public Error_OLC(String descripcio, int fila, int columna, String tipo, String nombre_archivo) {
        this.descripcio = descripcio;
        this.fila = fila;
        this.columna = columna;
        this.tipo = tipo;
        this.nombre_archivo = nombre_archivo;
    }
    
}
