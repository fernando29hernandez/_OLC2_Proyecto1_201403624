/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.GUI;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.LLAMADA_FUNCION;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.Instruccion;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import olc2.proyecto1_201403624.Principal_IDE;

/**
 *
 * @author Fernando
 */
public class MENSAJE extends Instruccion {

    Tipo tipo;
    LinkedList<Expresion> valores;
    int fila;
    int columna;

    /**
     *
     * @param tipo
     * @param valores
     * @param fila
     * @param columna
     */
    public MENSAJE(Tipo tipo, LinkedList<Expresion> valores, int fila, int columna) {
        this.tipo = tipo;
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }
    /**
     *
     */
    public String impresion = "";

    /**
     *
     * @param tablasimbolos
     * @return
     */
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
//ESTO SI LO VOY A USAR TODAVIA NO TERMINO EL ANALIZADOR

//        Simbolo buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
//        VENTANA ventana = (VENTANA) buscar_ventana.Valor;
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(MENSAJE.class.getName()).log(Level.SEVERE, null, ex);
//        }
        /**
         * AL FINA YA NO LO USE PUEDE BORRARSE
         */
        Expresion val = (Expresion) valores.get(0).Ejecutar(tablasimbolos);

        if (val instanceof DEVOLVER_SIMBOLO) {
            DEVOLVER_SIMBOLO val_exp = (DEVOLVER_SIMBOLO) val;
            Simbolo sim = val_exp.simbolo_retorno;
            if (sim.Tipo.tipo == Tipo.TipoSimbolo.objeto
                    && sim.Tipo.tipo_Compuesto.equals("ARREGLO")
                    && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter) {
                TRANSFORMARENCADENA literal = new TRANSFORMARENCADENA(sim);
                String cadena = literal.transformar();
                impresion = cadena;
                //valores.pop();
                int indice_entero = 0;
                int indice_decimal = 0;
                int indice_caracter = 0;
                int indice_booleano = 0;
                int indice_char_array = 0;
                indice_entero = impresion.indexOf("%e");
                indice_decimal = impresion.indexOf("%d");
                indice_caracter = impresion.indexOf("%c");
                indice_booleano = impresion.indexOf("%b");
                indice_char_array = impresion.indexOf("%s");
                if (valores.size() > 1 && indice_entero == -1
                        && indice_decimal == -1
                        && indice_caracter == -1
                        && indice_booleano == -1
                        && indice_char_array == -1) {
                    Errores.agregar_error("No se puede imprimir debido a que ni la cadena ni los parametros coinciden", fila, columna, "Semantico");
                    return null;
                }
                if (valores.size() == 1 && indice_entero != -1
                        && indice_decimal != -1
                        && indice_caracter != -1
                        && indice_booleano != -1
                        && indice_char_array != -1) {
                    Errores.agregar_error("No se puede imprimir debido a que ni la cadena ni los parametros coinciden", fila, columna, "Semantico");
                    return null;
                }
                if (valores.size() > 1) {

                    for (int i = 1; i < valores.size(); i++) {
                        Expresion exp = valores.get(i);
                        Expresion aux = (Expresion) exp.Ejecutar(tablasimbolos);
                        if (aux.tipo.tipo == Tipo.TipoSimbolo.entero && indice_entero != -1) {
                            impresion = impresion.replaceFirst("%e", aux.valor.toString());
                            indice_entero = -1;
                        } else if (aux.tipo.tipo == Tipo.TipoSimbolo.doble && indice_decimal != -1) {
                            impresion = impresion.replaceFirst("%d", aux.valor.toString());
                            indice_decimal = -1;
                        } else if (aux.tipo.tipo == Tipo.TipoSimbolo.caracter && indice_caracter != -1) {
                            impresion = impresion.replaceFirst("%c", aux.valor.toString());
                            indice_caracter = -1;
                        } else if (aux.tipo.tipo == Tipo.TipoSimbolo.booleano && indice_booleano != -1) {
                            impresion = impresion.replaceFirst("%b", aux.valor.toString());
                            indice_booleano = -1;
                        } else if (aux.tipo.tipo == Tipo.TipoSimbolo.objeto
                                && aux.tipo.tipo_Compuesto.equals("ARREGLO")
                                && indice_char_array != -1) {

                            DEVOLVER_SIMBOLO val_exp_2 = (DEVOLVER_SIMBOLO) aux;
                            Simbolo sim_2 = val_exp_2.simbolo_retorno;
                            if (sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter) {
                                TRANSFORMARENCADENA result_s = new TRANSFORMARENCADENA(sim_2);
                                String cadena2 = result_s.transformar();
                                impresion = impresion.replaceFirst("%s", cadena2);
                                indice_char_array = -1;
                            } else {
                                Errores.agregar_error("El tipo de valor que se quiere imprimir no es valido", fila, columna, "Semantico");
                                return null;
                            }
                        } else {
                            Errores.agregar_error("El tipo de valor que se quiere imprimir no es valido", fila, columna, "Semantico");
                            return null;
                        }
                        indice_entero = impresion.indexOf("%e");
                        indice_decimal = impresion.indexOf("%d");
                        indice_caracter = impresion.indexOf("%c");
                        indice_booleano = impresion.indexOf("%b");
                        indice_char_array = impresion.indexOf("%s");
                    }
                }
//                Display.Bandera_Mensaje=true;
//                JOptionPane.showMessageDialog(ventana.ventana,impresion);
//                Display.Bandera_Mensaje=false;
                indice_entero = impresion.indexOf("%e");
                indice_decimal = impresion.indexOf("%d");
                indice_caracter = impresion.indexOf("%c");
                indice_booleano = impresion.indexOf("%b");
                indice_char_array = impresion.indexOf("%s");
                if (indice_entero != -1 || indice_decimal != -1 && indice_caracter != -1 || indice_booleano != -1 || indice_char_array != -1) {
                    Errores.agregar_error("Impresion de consola erronea debido a falta de parametros", fila, columna, "Semantico");

                    return null;
                }
                Display.mensajes.addFirst(impresion);
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        JOptionPane op;
                        op = new JOptionPane(Display.mensajes.pop(), JOptionPane.INFORMATION_MESSAGE);
                        JDialog dialog = op.createDialog("Informacion");
                        dialog.setAlwaysOnTop(true);
                        dialog.setModal(true);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);

                    }
                });

            } else {
                Errores.agregar_error("El primer parametro para imprimir debe de ser de tipo chr[]", fila, columna, "Semantico");
                return null;
            }
        } else {
            Errores.agregar_error("El primer parametro para imprimir debe de ser de tipo chr[]", fila, columna, "Semantico");
            return null;
        }
        return null;
    }

}
