/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC.Entorno;

import Arbol.Entorno.Display;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class Errores_OLC {
    public static LinkedList<Error_OLC> ListaErrores = new LinkedList<Error_OLC>();
    
    public static void agregar_error(String descripcion,int fila,int columna,String tipo){
        ListaErrores.add(new Error_OLC(descripcion,fila,columna,tipo,Display.OLC_CARGADO));
    }
}
