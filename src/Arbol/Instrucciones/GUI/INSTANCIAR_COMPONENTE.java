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
import Arbol.Instrucciones.Instruccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

/**
 *
 * @author Fernando
 */
public class INSTANCIAR_COMPONENTE extends Instruccion {

    String nombre;
    LinkedList<Expresion> valores;
    int fila;
    int columna;

    public INSTANCIAR_COMPONENTE(String nombre, LinkedList<Expresion> valores, int fila, int columna) {
        this.nombre = nombre;
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {

//        if(Display.extension_archivo_activo.equals("b"))
//        {
        Simbolo buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
        if (buscar_ventana != null && Display.Constructor) {
            if (valores.size() > 0 && valores.size() == 1) {
                Expresion prueba = (Expresion) valores.get(0);
                prueba = (Expresion) prueba.Ejecutar(tablasimbolos);
                if (prueba.tipo.tipo == Tipo.TipoSimbolo.componente) {
                    VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                    if (prueba.tipo.tipo_comp == Tipo.TipoComponente.et) {
                        JLabel mi_etiqueta = new JLabel("          ");
                        mi_etiqueta.setSize(50, 20);
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) prueba;
                        Simbolo temp_sim = temp.simbolo_retorno;
                        COMPONENTE nuevo = new COMPONENTE(temp_sim.Tipo, mi_etiqueta);
                        temp_sim.Valor = nuevo;
                        ventana.ventana.add(mi_etiqueta);
                        return null;
                    } else if (prueba.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                        JTextField mi_caja = new JTextField("          ");
                        mi_caja.setSize(50, 20);
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) prueba;
                        Simbolo temp_sim = temp.simbolo_retorno;
                        COMPONENTE nuevo = new COMPONENTE(temp_sim.Tipo, mi_caja);
                        temp_sim.Valor = nuevo;
                        ventana.ventana.add(mi_caja);
                        return null;
                    } else if (prueba.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                        JTextArea mi_caja = new JTextArea("          ");
                        JScrollPane sp = new JScrollPane(mi_caja);
                        mi_caja.setSize(50, 20);
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) prueba;
                        Simbolo temp_sim = temp.simbolo_retorno;
                        COMPONENTE nuevo = new COMPONENTE(temp_sim.Tipo, sp, mi_caja);
                        temp_sim.Valor = nuevo;
                        ventana.ventana.add(sp);
                        return null;
                    } else if (prueba.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                        JSpinner  mi_caja = new JSpinner (new SpinnerNumberModel());
                        //JFormattedTextField mi_caja = new JFormattedTextField(mi_caja2);
                        /**
                         * NO USAR jformatted da muchos problemas el spinner aunque acepte letras no las analiza 
                         * 555a55
                         * el resultado cuando se analiza es 555 porque desde la a es error
                         */
                        mi_caja.setSize(50, 20);
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) prueba;
                        Simbolo temp_sim = temp.simbolo_retorno;
                        COMPONENTE nuevo = new COMPONENTE(temp_sim.Tipo, mi_caja);
                        temp_sim.Valor = nuevo;
                        ventana.ventana.add(mi_caja);
                        return null;
                    } else if (prueba.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                        JPasswordField mi_caja = new JPasswordField();
                        mi_caja.setSize(50, 20);
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) prueba;
                        Simbolo temp_sim = temp.simbolo_retorno;
                        COMPONENTE nuevo = new COMPONENTE(temp_sim.Tipo, mi_caja);
                        temp_sim.Valor = nuevo;
                        ventana.ventana.add(mi_caja);
                        return null;
                    } else if (prueba.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                        JButton mi_caja = new JButton();
                        mi_caja.setSize(50, 20);
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) prueba;
                        Simbolo temp_sim = temp.simbolo_retorno;
                        COMPONENTE nuevo = new COMPONENTE(temp_sim.Tipo, mi_caja);
                        nuevo.padre = ventana;
                        temp_sim.Valor = nuevo;
                        ventana.ventana.add(mi_caja);
                        mi_caja.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent ae) {
                                Display.Constructor = true;
                                Display.nombre_ventana=ventana.nombre;
                                Display.Bandera_CLICK = true;
                                LLAMADA_FUNCION ejecucion = new LLAMADA_FUNCION(temp.simbolo_retorno.Identificador, null, 0, 0);
                                ejecucion.Ejecutar(Display.GLOBAL);
                                Display.Bandera_CLICK = false;
                                Display.Constructor = false;
                                Display.nombre_ventana=ventana.nombre;
                    
                                //System.out.println("SI FUNCIONA");
                            }
                        });
                        return null;
                    }
                } else {
                    Errores.agregar_error("No se permite un parametro que no sea del tipo componente", fila, columna, "Semantico");
                    return null;
                }
            } else {
                Errores.agregar_error("No se puede usar _Nuevo_GUI debido a la cantidad parametros invalidos", fila, columna, "Semantico");
                return null;
            }
        } else {
            Errores.agregar_error("La instruccion no esta dentro de un contructor de ventana", fila, columna, nombre);
            return null;
        }
        return null;
//        }else
//        {
//            Errores.agregar_error("No se encuentra dentro de un archivo .B por lo que no se puede instancia componente", fila, columna,"Semantico");
//            return null;
//        }

    }

}
