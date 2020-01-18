/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones.Logicas;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.PRIMITIVO;

/**
 *
 * @author Fernando
 */
public class NOT extends Expresion{
    Expresion op1;
    String oper;
    int fila;
    int columna;
    public NOT(Expresion op1,String oper,int fila, int columna)
    {
        this.op1 = op1;
        this.oper = oper;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
        
        PRIMITIVO resultado = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.booleano), false, fila, columna); //Por defecto voy a retornar un booleano
        Expresion resultado1 = (Expresion)op1.Ejecutar(tablasimbolos);
        if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.booleano))
        {
            resultado.valor = !Boolean.parseBoolean(resultado1.valor.toString());
            resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
            return resultado;
        }
        Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
         
        // si llegue aqui es un error despues lo reporto
        return error;
    }
}