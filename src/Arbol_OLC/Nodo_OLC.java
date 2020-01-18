/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC;

import Arbol_OLC.Entorno.Entorno;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Fernando
 */
public abstract class Nodo_OLC {
    public abstract Object Ejecutar(Entorno tablasimbolos,String cadena);
}