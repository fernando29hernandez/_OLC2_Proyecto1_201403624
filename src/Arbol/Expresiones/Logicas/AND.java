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
public class AND extends Expresion{
    Expresion op1;
    Expresion op2;
    String oper;
    int fila;
    int columna;
    public AND(Expresion op1, Expresion op2, String oper, int fila, int columna)
    {
        this.op1 = op1;
        this.op2 = op2;
        this.oper = oper;
        this.columna = columna;
        this.fila = fila;
    }
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
        
        PRIMITIVO resultado = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.booleano), false, fila, columna); //Por defecto voy a retornar un booleano
        Expresion resultado1 = (Expresion)op1.Ejecutar(tablasimbolos);
        Expresion resultado2 = (Expresion)op2.Ejecutar(tablasimbolos);
        if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.booleano))
        {
            if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.booleano))
            {
                resultado.valor = Boolean.parseBoolean(resultado1.valor.toString()) &&  Boolean.parseBoolean(resultado2.valor.toString());
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;
        }
        Errores.agregar_error("La operacion no es posible debido a que el resultado es nulo",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
        return error;
    }

}

