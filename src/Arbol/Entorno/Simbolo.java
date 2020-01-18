/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Entorno;

import java.util.LinkedList;

/**
 *
 * @author Fernando
 */

public class Simbolo {

    public String Identificador = null;
    public Object Valor = null;                 //Si es una variable    
    public Tipo Tipo;//Se declara como vacio al principio indicando que no esta inicializado
    public Tipo tipo_datos;
    public int dimensiones;
    public Tamano tams;
    public Boolean constante;
    public Boolean Pertenece;
    /**
     * Constructor para cuando sean VARIABLES SIN INICIALIZAR
     *
     * @param tipo
     * @param identificador
     * @param valor
     */
    
    public Simbolo(Tipo tipo, String identificador, Object valor) {
        this.Identificador = identificador;
        this.Tipo = tipo;
        this.Valor = valor;
        this.tipo_datos = new Tipo();
        this.dimensiones = 0;
        this.tams = new Tamano(0);
        this.constante = false;
        this.Pertenece=false;
    }

    /**
     *
     * @param tipo
     * @param identificador
     * @param valor
     * @param tipo_datos
     * @param dimensiones
     * @param tams
     */
    public Simbolo(Tipo tipo, String identificador, Object valor, Tipo tipo_datos, int dimensiones, Tamano tams) {
        this.Identificador = identificador;
        this.Tipo = tipo;
        this.Valor = valor;
        this.tipo_datos = tipo_datos;
        this.dimensiones = dimensiones;
        this.tams = tams;
        this.constante = false;
        this.Pertenece=false;
    
    }

    /**
     *
     * @param tipo
     * @param identificador
     * @param valor
     * @param constante
     */
    public Simbolo(Tipo tipo, String identificador, Object valor, Boolean constante) {
        this.Identificador = identificador;
        this.Tipo = tipo;
        this.Valor = valor;
        this.tipo_datos = new Tipo();
        this.dimensiones = 0;
        this.tams =new Tamano(0);
        this.constante = constante;
        this.Pertenece=false;
    
    }

    /**
     *
     * @param tipo
     * @param identificador
     * @param valor
     * @param tipo_datos
     * @param dimensiones
     * @param tams
     * @param constante
     */
    public Simbolo(Tipo tipo, String identificador, Object valor, Tipo tipo_datos, int dimensiones, Tamano tams, Boolean constante) {
        this.Identificador = identificador;
        this.Tipo = tipo;
        this.Valor = valor;
        this.tipo_datos = tipo_datos;
        this.dimensiones = dimensiones;
        this.tams = tams;
        this.constante = constante;
        this.Pertenece=false;
    
    }

    /**
     *
     * @return
     */
    public String getIdentificador() {
        return Identificador;
    }

    /**
     *
     * @return
     */
    public Object getValor() {
        return Valor;
    }
}
