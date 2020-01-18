/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Display;
import static Arbol.Entorno.Display.archivo_abrir;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.Instruccion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class BUSCAR_ABRIR extends Instruccion {

    LinkedList<Expresion> path;
    int fila;
    int columna;

    public BUSCAR_ABRIR(LinkedList<Expresion> path, int fila, int columna) {
        this.path = path;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {

        if (!(path.size() == 1)) {
            Errores.agregar_error("No hay parametros para la funcion apend validos", fila, columna, "Semantico");
            return null;
        }
        Expresion ruta = (Expresion) path.get(0).Ejecutar(tablasimbolos);

        if (ruta.tipo.tipo != Tipo.TipoSimbolo.objeto && !(ruta.tipo.tipo_Compuesto.equals("ARREGLO"))) {
            Errores.agregar_error("La ruta no es una cadena de caracteres", fila, columna, "Semantico");
            return null;
        }
        DEVOLVER_SIMBOLO dev = (DEVOLVER_SIMBOLO) ruta;
        if (dev.simbolo_retorno.tipo_datos.tipo != Tipo.TipoSimbolo.caracter) {
            Errores.agregar_error("La ruta no es una cadena de caracteres", fila, columna, "Semantico");
            return null;
        }

        TRANSFORMARENCADENA literal = new TRANSFORMARENCADENA(dev.simbolo_retorno);
        String cadena = literal.transformar();
        if (cadena.startsWith("/")) {
            cadena = cadena.replaceFirst("/", "");
        }
        if (Display.archivo_abrir != null) {
            Errores.agregar_error("Ya se encuentra un archivo de texto en uso", fila, columna, "Semantico");
            return null;
        }
        Display.archivo_abrir = new File(cadena);
        if (archivo_abrir.exists()) {
            try {
                FileWriter temp = new FileWriter(Display.archivo_abrir, true);
                Display.bw = new BufferedWriter(temp);
            } catch (IOException ex) {
                //Logger.getLogger(ABRIR.class.getName()).log(Level.SEVERE, null, ex);
                 Display.bw = null;
                Display.archivo_abrir = null;
               

                Errores.agregar_error("No se encuentra ningun archivo de texto en uso", fila, columna, "Semantico");
                return null;

            }
        } else {
            Errores.agregar_error("El archivo que se intenta acceder no existe", fila, columna, "Semantico");
            Display.bw = null;
            Display.archivo_abrir = null;
            return null;
        }
        return null;

    }

}
