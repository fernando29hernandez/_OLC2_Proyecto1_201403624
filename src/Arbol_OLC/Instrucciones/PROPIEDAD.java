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
public class PROPIEDAD extends Instruccion {

    public String atributo;
    LinkedList<Instruccion> valores;
    public String valor;
    public int fila;
    public int columna;

    public PROPIEDAD(String atributo, String valor, int fila, int columna) {
        this.atributo = atributo;
        this.valor = valor;
        this.fila = fila;
        this.columna = columna;
    }

    public PROPIEDAD(String atributo, LinkedList<Instruccion> valores, int fila, int columna) {
        this.atributo = atributo;
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }

    public PROPIEDAD(String atributo, int fila, int columna) {
        this.atributo = atributo;
        this.valores = null;
        this.fila = fila;
        this.columna = columna;
    }

    /**
     *
     * @param tablasimbolos
     * @return
     */
    @Override
    public Object Ejecutar(Entorno tablasimbolos, String cadena) {
        if (atributo.equals("configuracion")) {
            if (valores != null) {
                for (Instruccion ins : valores) {
                    Object val = ins.Ejecutar(tablasimbolos, cadena);
                    if (val instanceof CARPETA) {
                        CARPETA c = (CARPETA) val;
                        Simbolo nuevo = new Simbolo(c.valor, c);
                        tablasimbolos.insertar(c.valor, nuevo, c.fila, c.columna);
                        nuevo.Raiz = true;
                    } else if (val instanceof ARCHIVO) {
                        ARCHIVO c = (ARCHIVO) val;
                        c.ruta_relativa = valor + "/" + c.ruta_relativa;
                        c.rutas.addLast(c.ruta_relativa);
                        Simbolo nuevo = new Simbolo(c.nombre, c);
                        nuevo.Raiz = true;
                        tablasimbolos.insertar(c.nombre, nuevo, c.fila, c.columna);
                    }

                }
                /*for (Simbolo sim : tablasimbolos.tabla.values()) {
                    //System.out.println(sim.id);
                    if (sim.valor instanceof CARPETA && sim.recien_declarado) {
                        sim.recien_declarado = false;
//                CARPETA c = (CARPETA)sim.valor;
//                System.out.println(c.valor);
                    } else if (sim.valor instanceof ARCHIVO && sim.recien_declarado == false) {
                        sim.recien_declarado = false;
                        ARCHIVO c = (ARCHIVO) sim.valor;
                        //c.ruta_relativa = valor + "/" + c.ruta_relativa;
                        //c.rutas.addLast(c.ruta_relativa+c.nombre);
                    }
                    sim.recien_declarado = false;
                }*/
            }

        }

        return this;
    }

}
