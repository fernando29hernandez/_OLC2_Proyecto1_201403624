/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC.Instrucciones;

import Arbol.Entorno.Display;
import Arbol_OLC.Entorno.Entorno;
import Arbol_OLC.Entorno.Simbolo;
import java.io.File;
import java.util.LinkedList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import olc2.proyecto1_201403624.Principal_IDE;

/**
 *
 * @author Fernando
 */
public class PROYECTO extends Instruccion {

    LinkedList<Instruccion> valores;
    public int fila;
    public int columna;
    public static String ruta_absoluta = "";
    public static String nombre_proyecto = "";
    public static String archivo_ejecucion = "";
    public static Entorno carpetas;

    /**
     * *
     *
     * @param valores
     * @param fila
     * @param columna
     */
    public PROYECTO(LinkedList<Instruccion> valores, int fila, int columna) {
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }

    /**
     *
     * @param tablasimbolos
     * @param cadena
     * @return
     */
    @Override
    public Object Ejecutar(Entorno tablasimbolos, String cadena) {
        for (Instruccion ins : valores) {
            PROPIEDAD prop = (PROPIEDAD) ins;
            if (prop.atributo.equals("ruta")) {
                ruta_absoluta = prop.valor;
            } else if (prop.atributo.equals("nombre")) {
                nombre_proyecto = prop.valor;
            } else if (prop.atributo.equals("correr")) {
                archivo_ejecucion = prop.valor;
            } else if (prop.atributo.equals("configuracion")) {
                //Object temp = prop.Ejecutar(tablasimbolos, ruta_absoluta + nombre_proyecto + "/");
                // ANTES LA TENIA ASI 
                String cambio = ruta_absoluta;
                cambio = cambio.replace("\\", "/");
                ruta_absoluta = cambio;
                File archivo = new File(ruta_absoluta);
                if (archivo.exists()) {
                    String verificacion = ruta_absoluta.substring(ruta_absoluta.length() - 1);
                    if(!verificacion.equals("/"))
                    {
                        ruta_absoluta=ruta_absoluta+"/";
                    }
                } else {
                    ruta_absoluta=Display.RUTA_OLC_CARGADO;
                }
                Object temp = prop.Ejecutar(tablasimbolos, ruta_absoluta);
                PROPIEDAD tmp = (PROPIEDAD) temp;
                carpetas = tablasimbolos;
            }

        }
        DefaultTreeModel arbol = (DefaultTreeModel) Principal_IDE.explorador.getModel();
        DefaultMutableTreeNode nroot = new DefaultMutableTreeNode(nombre_proyecto);
        arbol.setRoot(nroot);

        System.out.println(ruta_absoluta);
        System.out.println(nombre_proyecto);
        System.out.println(archivo_ejecucion);
        for (Simbolo sim : carpetas.tabla.values()) {
            //System.out.println(sim.id);
            if (sim.valor instanceof CARPETA) {
            } else if (sim.valor instanceof ARCHIVO) {
                System.out.println("-------------------------------------->");
                ARCHIVO c = (ARCHIVO) sim.valor;
                System.out.println("Nombre " + c.nombre);
                System.out.println("Relativa " + c.ruta_relativa + c.nombre);
                System.out.println("Absoluta " + c.ruta_absoluta);
                System.out.println("--------------------------------------");
                for (String s : c.rutas) {
                    System.out.println("Posible ruta " + s);
                }
                System.out.println("--------------------------------------->");
            }

        }
        int i = 0;

        for (Simbolo sim : carpetas.tabla.values()) {
            //System.out.println(sim.id);
            DefaultMutableTreeNode aux = null;
            if (sim.Raiz) {
                aux = new DefaultMutableTreeNode(sim.id);
                arbol.insertNodeInto(aux, nroot, i);
                i++;
            }
            if (sim.valor instanceof CARPETA) {

                if (sim.Raiz) {
                    CargaEstructuraDirectorios(arbol, aux, (CARPETA) sim.valor);
                }
            }

        }

        return null;
    }

    private void CargaEstructuraDirectorios(DefaultTreeModel arbol, DefaultMutableTreeNode padre, CARPETA c) {
        DefaultMutableTreeNode aux = null;
        int i = 0;
        if (c.carpeta != null) {
            for (Simbolo sim : c.carpeta.tabla.values()) {
                //System.out.println(sim.id);
                aux = new DefaultMutableTreeNode(sim.id);
                arbol.insertNodeInto(aux, padre, i);
                if (sim.valor instanceof CARPETA) {
                    CARPETA c1 = (CARPETA) sim.valor;
                    CargaEstructuraDirectorios(arbol, aux, (CARPETA) sim.valor);
                }
//                } else if (sim.valor instanceof ARCHIVO) {
//                    ARCHIVO c1 = (ARCHIVO) sim.valor;
//                    
//                }
                i++;
            }

        }
    }

    public static LinkedList<ARCHIVO> buscarEjecutable() {

        LinkedList<ARCHIVO> LISTA = new LinkedList();
        for (Simbolo sim : carpetas.tabla.values()) {
            //System.out.println(sim.id);
            if (sim.valor instanceof CARPETA) {
            } else if (sim.valor instanceof ARCHIVO) {
                ARCHIVO c = (ARCHIVO) sim.valor;
                String extension;

                int i = c.nombre.lastIndexOf('.');
                if (i > 0) {
                    extension = c.nombre.substring(i + 1);
                    if (extension.toLowerCase().equals("r")) {
                        LISTA.addLast(c);
                    }
                }

            }

        }
        return LISTA;
    }
}
