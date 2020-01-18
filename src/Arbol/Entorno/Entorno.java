/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Entorno;

import java.util.Hashtable;

/**
 *
 * @author Fernando
 */
public class Entorno {
    public Hashtable<String,Simbolo> tabla;
    public Entorno entornoPadre;
   
    public Entorno(Entorno entornoPadre) {
        this.entornoPadre = entornoPadre;
        this.tabla = new Hashtable<>();
    }
    public int insertar(String id, Simbolo sim,int fila,int columna) {
        if (this.tabla.containsKey(id)) {
            Errores.agregar_error("Error: la variable '" + id + "' ya existe en este ambito",fila,columna,"Semantico");
            return 0;
        }
        this.tabla.put(id,sim);
        return 1;
    }
    public Simbolo buscar(String id,int fila,int columna) {
        for (Entorno e = this; e != null; e = e.entornoPadre) {
            if (e.tabla.containsKey(id)) {
                Simbolo sim = e.tabla.get(id);
                return sim;
            }
        }
        Errores.agregar_error("Error: la variable '" + id + "' NO existe en este ambito",fila,columna,"Semantico");
        return null;
    }
    public Simbolo buscarsinreportar(String id,int fila,int columna) {
        for (Entorno e = this; e != null; e = e.entornoPadre) {
            if (e.tabla.containsKey(id)) {
                Simbolo sim = e.tabla.get(id);
                return sim;
            }
        }
        return null;
    }
}
