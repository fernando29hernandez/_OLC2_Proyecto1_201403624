/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.GUI.OBJETO_RSTRING;
import Arbol.Instrucciones.Instruccion;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class LEER extends Instruccion {

    Expresion path;
    Expresion contenedor;
    int fila;
    int columna;
    LinkedList<Expresion> valores;

    public LEER(LinkedList<Expresion> valores, int fila, int columna) {
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        if (!(valores.size() == 2)) {
            Errores.agregar_error("No hay parametros para la funcion apend validos", fila, columna, "Semantico");
            return null;
        }
        Expresion ruta = (Expresion) valores.get(0).Ejecutar(tablasimbolos);
        Expresion contenedor = (Expresion) valores.get(1).Ejecutar(tablasimbolos);
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
        if (contenedor.tipo.tipo != Tipo.TipoSimbolo.componente) {
            Errores.agregar_error("No se debe de enviar de contenedor de un LEER una variable que no sea RString", fila, columna, "Semantico");
            return null;
        }
        if (contenedor.tipo.tipo_comp != Tipo.TipoComponente.rstring) {
            Errores.agregar_error("El contenedor debe de ser una variable de tipo RString", fila, columna, "Semantico");
            return null;

        }
        Display.archivo_abrir = new File(cadena);
        if (Display.archivo_abrir.exists()) {
            FileReader lector = null;
            try {
                lector = new FileReader(Display.archivo_abrir);
                Display.br = new BufferedReader(lector);
                String linea = "";
                String temp = "";
                while ((temp = Display.br.readLine()) != null) {
                    if (temp == null) {
                    } else {
                        linea += temp;
                    }

                }
                lector.close();
                System.out.println(contenedor.getClass());
                if(!(contenedor instanceof DEVOLVER_SIMBOLO))
                {
                    return null;
                }
                String text = new String(Files.readAllBytes(Paths.get(cadena)), StandardCharsets.UTF_8);
                DEVOLVER_SIMBOLO temporal_cadena =(DEVOLVER_SIMBOLO)contenedor;
                Simbolo temporal_simbolo = temporal_cadena.simbolo_retorno;
                OBJETO_RSTRING cad = (OBJETO_RSTRING) temporal_simbolo.Valor;
                OBJETO_RSTRING nueva = new OBJETO_RSTRING(text,cad.tipo);
                
                System.out.println("La linea que leo es:"+text+"######################");
                temporal_simbolo.Valor=nueva;
            } catch (IOException ex) {
                //Logger.getLogger(ABRIR.class.getName()).log(Level.SEVERE, null, ex);
                
                    Display.archivo_abrir = null;
                    Display.br = null;
            }

        } else {
            Display.archivo_abrir = null;
                    Display.br = null;
            Errores.agregar_error("No se encuentra ningun archivo con la ruta especificada para la fucion _read", fila, columna, "Semantico");
            return null;

        }

        return null;

    }

}
