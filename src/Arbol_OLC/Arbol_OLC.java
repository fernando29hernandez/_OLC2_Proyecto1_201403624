/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC;

import Arbol_OLC.Entorno.Entorno;
import Arbol_OLC.Instrucciones.Instruccion;
import java.util.LinkedList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import olc2.proyecto1_201403624.Principal_IDE;

/**
 *
 * @author Fernando
 */
public class Arbol_OLC {
    LinkedList<Instruccion> Instrucciones ;
    Instruccion ins;
    Entorno GLOBAL = new Entorno(null);
    public Arbol_OLC(Instruccion Instrucciones) {
        this.ins = Instrucciones;
    }
    public void Ejecutar(){
        String valor ="";
        
        ins.Ejecutar(GLOBAL,valor);
    }
}
