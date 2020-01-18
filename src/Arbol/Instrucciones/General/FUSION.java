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
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class FUSION extends Instruccion {
    public String identificador;
    public LinkedList<Instruccion>variables;
    int fila;
    int columna;

    public FUSION(String identificador, LinkedList<Instruccion> variables, int fila, int columna) {
        this.identificador = identificador;
        this.variables = variables;
        this.fila = fila;
        this.columna = columna;
    }
    
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        if(Display.global_state==true)
        {
            String nombre = "FUSION_" + identificador;
            Simbolo nuevo_fusion = new Simbolo(new Tipo(Tipo.TipoSimbolo.fusion), nombre, this);
            tablasimbolos.insertar(nombre, nuevo_fusion, fila, columna);
            return null;
        }
        
        Errores.agregar_error("No se puede declarar una FUSION en un ambito que no sea el global", fila, columna, "Semantico");
        return null;
    }
    
}
