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
import Arbol.Expresiones.LLAMADA_FUNCION;
import Arbol.Instrucciones.GUI.COMPONENTE;
import Arbol.Instrucciones.GUI.VENTANA;
import Arbol.Instrucciones.Instruccion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Fernando
 */
public class FUNCION extends Instruccion {

    public Tipo tipo;
    public String identificador;
    public LinkedList<Instruccion> parametros;
    public LinkedList<Instruccion> instruciones;
    public String evento;
    public int fila;
    public int columna;
    public int dimensiones;
    public String nombre_archivo_origen;
    public String extension_origen;

    public FUNCION(Tipo tipo, String identificador, LinkedList<Instruccion> parametros, LinkedList<Instruccion> instruciones, String evento, int fila, int columnna) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.parametros = parametros;
        this.instruciones = instruciones;
        this.evento = evento;
        this.fila = fila;
        this.columna = columnna;
        this.dimensiones = 0;
    }

    public FUNCION(Tipo tipo, String identificador, LinkedList<Instruccion> parametros, LinkedList<Instruccion> instruciones, String evento, int dimensiones, int fila, int columnna) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.parametros = parametros;
        this.instruciones = instruciones;
        this.evento = evento;
        this.fila = fila;
        this.columna = columnna;
        this.dimensiones = dimensiones;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        String nombre_unico_funcion = "FUNCION_";
        if (evento == null) {
            nombre_unico_funcion = "FUNCION_" + identificador;
            if (parametros != null) {
                for (Instruccion dec : parametros) {
                    if (dec instanceof DECLARACION) {
                        DECLARACION temporal = (DECLARACION) dec;
                        nombre_unico_funcion += "_D_" + temporal.tipo.tipo + "_D_" + temporal.tipo.tipo_Compuesto;

                    } else if (dec instanceof DECLARACION_ARREGLO) {
                        DECLARACION_ARREGLO temporal = (DECLARACION_ARREGLO) dec;
                        nombre_unico_funcion += "_D_" + Tipo.TipoSimbolo.objeto + "_D_" + "ARREGLO" + "_D_" + temporal.dimensiones + "_D_" + temporal.tipo.tipo + "_D_" + temporal.tipo.tipo_Compuesto;

                    }
                }
            }
            tipo.dimensiones = this.dimensiones;
            this.nombre_archivo_origen = Display.ruta_archivo;
            this.extension_origen = Display.extension_archivo_activo;
            Simbolo nueva_funcion = new Simbolo(tipo, nombre_unico_funcion, this);
            tablasimbolos.insertar(nombre_unico_funcion, nueva_funcion, fila, columna);
            return null;
        } else if (evento != null && Display.extension_archivo_activo.equals("b")) {
            if (evento.equals("iniciar_ventana")) {
                this.extension_origen = Display.extension_archivo_activo;
                this.nombre_archivo_origen = Display.ruta_archivo;

                nombre_unico_funcion = "VENTANA_" + Display.nombre_archivo_activo;
                VENTANA nueva = new VENTANA(nombre_unico_funcion, instruciones,this.nombre_archivo_origen);
                Simbolo nueva_ventana = new Simbolo(new Tipo(Tipo.TipoSimbolo.ventana), nombre_unico_funcion, nueva);
                Display.GLOBAL.insertar(nombre_unico_funcion, nueva_ventana, fila, columna);
                return null;
            } else if (evento.equals("al_dar_click")) {
                this.extension_origen = Display.extension_archivo_activo;
                this.nombre_archivo_origen = Display.ruta_archivo;
                Simbolo nueva_funcion = new Simbolo(tipo, "BOTON_" + identificador, this);
                /**
                 * CAMBIO A BOTON_
                 */
                tablasimbolos.insertar("FUNCION_" + identificador, nueva_funcion, fila, columna);
                return null;
            }

        }
        Errores.agregar_error("Declaracion de funcion tipo ventana no valida fuera de .B", fila, columna, "Semantico");
        return null;
    }

}
