/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Lista_Tamano;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tamano;
import Arbol.Entorno.Tipo;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 * 
 * 
 */
public class CADENA extends Expresion {
    String entrada;
    int fila;
    int columna;

    public CADENA(Tipo tipo,String entrada, int fila, int columna) {
        this.tipo = tipo;
        this.entrada = entrada;
        this.fila = fila;
        this.columna = columna;
    }
       
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        OBJETO_ARREGLO arreglo_nuevo = new OBJETO_ARREGLO(new Tipo(Tipo.TipoSimbolo.caracter),1);
        if(entrada.toString().length()==0)
        {
             arreglo_nuevo.lista_elementos.add(new Simbolo(new Tipo(Tipo.TipoSimbolo.caracter),"Anonimo","0"));
            
        }
        for(int i =0;i<entrada.toString().length();i++)
        {
             arreglo_nuevo.lista_elementos.add(new Simbolo(new Tipo(Tipo.TipoSimbolo.caracter),"Anonimo",Character.toString(entrada.charAt(i))));
        }
        Lista_Tamano tam = new Lista_Tamano();
        if(entrada.toString().length()==0)
        {
            tam.Insertar(1);
        
        }else{
            tam.Insertar(entrada.toString().length());
        }
        Simbolo nuevo_sim = new Simbolo(new Tipo(Tipo.TipoSimbolo.objeto,"ARREGLO"), "Anonimo", arreglo_nuevo,new Tipo(Tipo.TipoSimbolo.caracter),1,tam.cabeza); 
        nuevo_sim.Pertenece=true;
        DEVOLVER_SIMBOLO retorno = new DEVOLVER_SIMBOLO(nuevo_sim);
        return retorno;
        
    }
}