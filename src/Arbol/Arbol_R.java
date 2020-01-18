/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.LLAMADA_FUNCION;
import Arbol.Instrucciones.General.DECLARACION;
import Arbol.Instrucciones.General.FUNCION;
import Arbol.Instrucciones.General.FUSION;
import Arbol.Instrucciones.General.IMPORTAR;
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class Arbol_R {

    LinkedList<Instruccion> Instrucciones;
    public Entorno GLOBAL = new Entorno(null);

    public Arbol_R(LinkedList<Instruccion> Instrucciones) {
        this.Instrucciones = Instrucciones;
    }

    public Object ejecutar() {
        Display.global_state = true;
        Display.GLOBAL = new Entorno(null);
        GLOBAL = Display.GLOBAL;
        for (Nodo_R ins : Instrucciones) {
            if (ins instanceof IMPORTAR) {
                ins.Ejecutar(GLOBAL);
            } else if (ins instanceof FUNCION) {
            } else {
            }
        }
        Display.global_state = true;

        for (Nodo_R ins : Instrucciones) {
            if (ins instanceof IMPORTAR) {
            } else if (ins instanceof FUSION) {
                ins.Ejecutar(GLOBAL);
            } else {
            }

        }
        int i = 0;
        while (i < Instrucciones.size()) {

            Instruccion ins = Instrucciones.get(i);
            if (ins instanceof IMPORTAR) {
            } else if (ins instanceof FUNCION) {

            } else if (ins instanceof FUSION) {

            } else {
                ins.Ejecutar(GLOBAL);
            }
            i++;
        }
        for (Nodo_R ins : Instrucciones) {
            if (ins instanceof IMPORTAR) {
            } else if (ins instanceof FUNCION) {
                ins.Ejecutar(GLOBAL);
            } else {
            }

        }
        Display.global_state = false;
        Simbolo main = GLOBAL.buscar("FUNCION_main", 0, 0);
        if (main != null) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    LLAMADA_FUNCION ejecucion = new LLAMADA_FUNCION("main", null, 0, 0);
                    ejecucion.Ejecutar(GLOBAL);

                }
            });
            thread.start();

            //thread.interrupt();
        } else {
            Errores.agregar_error("No se declaro metodo main al archivo .R", -1, -1, "Semantico");
        }
        return null;
    }

    public Object ejecutar_import(Entorno tablasimbolos) {
        //GLOBAL = tablasimbolos;
        GLOBAL = Display.GLOBAL;
        
        Display.global_state = true;
        for (Nodo_R ins : Instrucciones) {
            if (ins instanceof IMPORTAR) {
                ins.Ejecutar(GLOBAL);
            } else if (ins instanceof FUNCION) {
            } else {
            }
        }
        Display.global_state = true;

        for (Nodo_R ins : Instrucciones) {
            if (ins instanceof IMPORTAR) {
            } else if (ins instanceof FUSION) {
                ins.Ejecutar(GLOBAL);
            } else {
            }

        }
        for (Nodo_R ins : Instrucciones) {
            if (ins instanceof IMPORTAR) {
            } else if (ins instanceof FUNCION) {
                ins.Ejecutar(GLOBAL);
            } else {
            }

        }
//        for (Nodo_R ins : Instrucciones) {
//                if (ins instanceof DECLARACION) {
//                    ins.Ejecutar(GLOBAL);
//                }
//                
//                
//        }
        for (Nodo_R ins : Instrucciones) {
            if (ins instanceof IMPORTAR) {
            } else if (ins instanceof FUNCION) {
            } else if (ins instanceof FUSION) {

            } else {
                ins.Ejecutar(GLOBAL);
            }

        }
        Display.global_state = false;
        Simbolo main = GLOBAL.buscarsinreportar("FUNCION_main", 0, 0);
        if (main != null) {

              Errores.agregar_error("No se permite la declaracion del  metodo main al archivo que no sean .R", -1, -1, "Semantico");

            //thread.interrupt();
        } else {
         
        }
        return null;
    }
}
