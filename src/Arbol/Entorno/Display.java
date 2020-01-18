/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Entorno;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class Display {

    public enum Tipo_Instruccion {
        for_simple,
        seleccciona,
        funcion,
        procecimiento,
        repeat,
        mientras
    }
    public static LinkedList<Tipo_Instruccion> display = new LinkedList<Tipo_Instruccion>();
    public static Entorno GLOBAL = new Entorno(null);
    public static String ruta_archivo = "";// esta me sive para el nombre del archivo en los errores
    public static File archivo_abrir = null;
    public static BufferedWriter bw = null;
    public static BufferedReader br = null;
    public static String nombre_archivo_activo = "";
    public static String extension_archivo_activo = "";
    public static Boolean Bandera_ACTIVA = false;
    public static Boolean Bandera_CLICK = false;
    public static Boolean Bandera_Mensaje = false;
    public static Boolean Constructor = false;
    public static String nombre_ventana = "";
    public static LinkedList<String> mensajes = new LinkedList();
    public static Boolean global_state = false;
    public static String OLC_CARGADO = "";
    public static String RUTA_OLC_CARGADO = "";

    public static Boolean Verificador_tope(Tipo_Instruccion instruccion_verificable) {
        System.out.println("EL TAMANO DEL DISPLAY es :" + display.size());
        if (display.size()> 0) {
            System.out.println(display.getFirst());
            Tipo_Instruccion actual = display.getFirst();
            if (actual.equals(instruccion_verificable)) {
                return true;
            }

            return false;
        }
        return false;
    }
}
