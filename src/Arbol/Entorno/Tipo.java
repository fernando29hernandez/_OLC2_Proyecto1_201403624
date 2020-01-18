/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Entorno;

/**
 *
 * @author Fernando
 */
public class Tipo {
    public enum TipoSimbolo
    {
        funcion,
        objeto,
        entero,
        doble,
        booleano,
        cadena,
        caracter,
        nulo,
        error,
        componente,
        ventana,
        vacio,
        instancia,
        fusion,
        arreglo
    }
    public enum TipoComponente
    {
        et,caja,cajaarea,cajanum,cajapass,boton,rstring,mensaje,nulo
    }
    public TipoSimbolo tipo;
    public String tipo_Compuesto;
    public TipoComponente tipo_comp;
    public TipoSimbolo tipo_datos;
    public int dimensiones=0;
    
    /**
     * Tipos primitivos
     * 
     * @param tipo
     */
    public Tipo()
    {
        this.tipo=Tipo.TipoSimbolo.nulo;
        this.tipo_Compuesto="nada";
        /**
         * 
         */
        this.tipo_comp=Tipo.TipoComponente.nulo;
        this.tipo_datos=Tipo.TipoSimbolo.nulo;
        
    }
    public Tipo(TipoSimbolo tipo)
    {
        this.tipo = tipo;
        this.tipo_Compuesto = "nada";
        /***
         * 
         */
        this.tipo_comp=Tipo.TipoComponente.nulo;
        this.tipo_datos=Tipo.TipoSimbolo.nulo;

    }
    /**
     * 
     * Tipos objetos
     * 
     * @param tipo
     * @param tipo_compuesto
     */
    public Tipo(TipoSimbolo tipo, String tipo_compuesto)
    {
        this.tipo = tipo;
        this.tipo_Compuesto = tipo_compuesto;
        /**
         * 
         */
        this.tipo_comp=Tipo.TipoComponente.nulo;
        this.tipo_datos=Tipo.TipoSimbolo.nulo;
    }
    /**
     * 
     * Tipos componentes
     * 
     * @param tipo
     * @param tipo_comp
     */
    public Tipo(TipoSimbolo tipo,TipoComponente tipo_comp)
    {
        this.tipo = tipo;
        this.tipo_comp = tipo_comp;
        /**
         * 
         */
        this.tipo_datos=Tipo.TipoSimbolo.nulo;
        this.tipo_Compuesto="nada";
    }
}

