/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC.Instrucciones;

import Arbol_OLC.Entorno.Entorno;
import Arbol_OLC.Entorno.Simbolo;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class CARPETA extends Instruccion {

    LinkedList<Instruccion> valores;
    String nombre;
    String valor;
    public int fila;
    public int columna;
    Entorno carpeta;

    public CARPETA(String nombre, String valor, LinkedList<Instruccion> valores, int fila, int columna) {
        this.valores = valores;
        this.nombre = nombre;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;

    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos, String cadena) {
        this.carpeta = new Entorno(null);
        if (valores != null) {
            for (Instruccion ins : valores) {
                if(!(ins instanceof ARCHIVO))
                {
                
                Object val = ins.Ejecutar(tablasimbolos, cadena + valor + "/");
                if (val instanceof CARPETA) {
                    CARPETA c = (CARPETA) val;
                    //c.valor=valor+"/"+c.valor;
                    Simbolo nuevo = new Simbolo(c.valor, c);
                    tablasimbolos.insertar(c.valor, nuevo, c.fila, c.columna);
                    carpeta.insertar(c.valor, nuevo, c.fila, c.columna);
                }
                }
            }
            for (Instruccion ins : valores) {
                if(!(ins instanceof CARPETA))
                {
                Object val = ins.Ejecutar(tablasimbolos, cadena + valor + "/");
                if (val instanceof ARCHIVO) {
                    ARCHIVO c = (ARCHIVO) val;
                    c.ruta_relativa = valor + "/" + c.ruta_relativa;
                    c.rutas.addLast(c.ruta_relativa + c.nombre);
                    Simbolo nuevo = new Simbolo(c.nombre, c);
                    tablasimbolos.insertar(c.nombre, nuevo, c.fila, c.columna);
                    carpeta.insertar(c.nombre, nuevo, c.fila, c.columna);
                }
                }
                
            
            }
            
            for (int i = 0;i<tablasimbolos.tabla.size();i++) {
                Simbolo sim = (Simbolo) tablasimbolos.tabla.values().toArray()[i];
                if (sim.valor instanceof ARCHIVO && sim.recien_declarado == false) {
                    sim.recien_declarado = false;
                    ARCHIVO c = (ARCHIVO) sim.valor;
                    c.ruta_relativa = valor + "/" + c.ruta_relativa;
                    c.rutas.addLast(c.ruta_relativa + c.nombre);
                }
                
            }
            for (int i = 0;i<tablasimbolos.tabla.size();i++) {
                
                Simbolo sim = (Simbolo) tablasimbolos.tabla.values().toArray()[i];
                if(sim.nivel)
                {
                        sim.recien_declarado = false;
            
                }
                sim.nivel=false;
            }
            
        }

        return this;
    }

}
