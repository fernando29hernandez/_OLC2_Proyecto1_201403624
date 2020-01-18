/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;

/**
 *
 * @author Fernando
 */
public class ID extends Expresion{
    public String identificador;

    public ID(String identificador, int fila, int columna)
    {
        this.identificador = identificador;
        this.columna = columna;
        this.fila = fila;
    }
    int columna;
    int fila;
    

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
            
        Simbolo simbolo = tablasimbolos.buscar(identificador, fila, columna);
        if (simbolo != null)
        { 
            DEVOLVER_SIMBOLO retorno;
            retorno = new DEVOLVER_SIMBOLO(simbolo);
            return retorno;
        }
        
        Errores.agregar_error("Acceso no se puede efectuar porque no se encontro variable", fila, columna, "Semantico");
        //tengo que devolver un error aqui
        return error;
    }

}
