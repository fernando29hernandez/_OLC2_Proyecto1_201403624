/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Instrucciones.Instruccion;

/**
 *
 * @author Fernando
 */
public class DETENER extends Instruccion {

    int fila;
    int columna;

    public DETENER(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        if (Display.Verificador_tope(Display.Tipo_Instruccion.for_simple) || Display.Verificador_tope(Display.Tipo_Instruccion.seleccciona)) {
            return new DETENER(fila, columna);
        }
        Errores.agregar_error("La instruccion ROMPER NO es valida en este ambito", fila, columna, "Semantica");
        return null;
    }
}
