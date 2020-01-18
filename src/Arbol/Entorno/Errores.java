/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Entorno;

import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class Errores {
    public static LinkedList<Error> ListaErrores = new LinkedList<Error>();
    
    public static void agregar_error(String descripcion,int fila,int columna,String tipo){
        ListaErrores.add(new Error(descripcion,fila,columna,tipo,Display.ruta_archivo));
    }
    public static void agregar_error2(String descripcion,int fila,int columna,String tipo,String archivo){
        ListaErrores.add(new Error(descripcion,fila,columna,tipo,archivo));
    }
}
