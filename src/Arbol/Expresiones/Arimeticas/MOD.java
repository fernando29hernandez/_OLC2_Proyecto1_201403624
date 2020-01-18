/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones.Arimeticas;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.PRIMITIVO;

/**
 *
 * @author Fernando
 */
public class MOD extends Expresion{
    Expresion op1;
    Expresion op2;
    String oper;

    public MOD(Expresion op1, Expresion op2, String oper, int fila, int columna)
    {
        this.op1 = op1;
        this.op2 = op2;
        this.oper = oper;
        this.columna = columna;
        this.fila = fila;
    }
    int columna;
    int fila;
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
        
        PRIMITIVO resultado = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), 0, fila, columna);
        Expresion resultado1 = (Expresion)op1.Ejecutar(tablasimbolos);
        Expresion resultado2 = (Expresion)op2.Ejecutar(tablasimbolos);
        if(resultado2.valor.toString().equals("0"))
        {
            Errores.agregar_error("La operacion no es posible debido a que no se permite la division por 0",fila, columna,"Semantica");
            return null;
        }
        if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.entero))
        {
            switch (resultado2.tipo.tipo) {
                case entero:
                {
                    Double valor_ = Double.valueOf(resultado1.valor.toString());
                    int valor__ = valor_.intValue();
                    Double valor_2 = Double.valueOf(resultado2.valor.toString());
                    int valor__2 = valor_2.intValue();
                    resultado.valor = valor__%valor__2;
                    resultado.tipo.tipo = Tipo.TipoSimbolo.entero;
                    return resultado;
                }
                case doble:
                {
                    Double valor_ = Double.valueOf(resultado1.valor.toString());
                    Double valor_2 = Double.valueOf(resultado2.valor.toString());
                    resultado.valor = valor_ % valor_2;
                    resultado.tipo.tipo = Tipo.TipoSimbolo.doble;
                    return resultado;
                }
                case caracter:
                    Double valor_= Double.valueOf(resultado1.valor.toString());
                    resultado.valor =valor_.intValue()% resultado2.valor.toString().charAt(0);
                    resultado.tipo.tipo = Tipo.TipoSimbolo.entero;
                    return resultado;
                default:
                    break;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;
            
        }
        else if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.doble))
        {
            switch (resultado2.tipo.tipo) {
                case entero:
                {
                    Double valor_ = Double.valueOf(resultado1.valor.toString());
                    Double valor_2 = Double.valueOf(resultado2.valor.toString());
                    resultado.valor = valor_ % valor_2;
                    resultado.tipo.tipo = Tipo.TipoSimbolo.doble;
                    return resultado;
                    
                }
                case doble:
                {
                    Double valor_ = Double.valueOf(resultado1.valor.toString());
                    Double valor_2 = Double.valueOf(resultado2.valor.toString());
                    resultado.valor = valor_ % valor_2;
                    resultado.tipo.tipo = Tipo.TipoSimbolo.doble;
                    return resultado;
                }
                case caracter:
                    resultado.valor = Double.valueOf(resultado1.valor.toString())% resultado2.valor.toString().charAt(0);
                    resultado.tipo.tipo = Tipo.TipoSimbolo.doble;
                    return resultado;
                default:
                    break;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;
        }
        else if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.caracter))
        {
            switch (resultado2.tipo.tipo) {
                case entero:
                {
                    String  valor_ = resultado1.valor.toString();
                    int valor__ = valor_.charAt(0);
                    Double valor_2 = Double.valueOf(resultado2.valor.toString());
                    int valor__2 =valor_2.intValue();
                    resultado.valor = valor__%valor__2;
                    resultado.tipo.tipo = Tipo.TipoSimbolo.entero;
                    return resultado;
                }
                case doble:
                {
                    String  valor_ = resultado1.valor.toString();
                    int valor__ = valor_.charAt(0);
                    Double valor_2 = Double.valueOf(resultado2.valor.toString());
                    resultado.valor = valor__ % valor_2;
                    resultado.tipo.tipo = Tipo.TipoSimbolo.doble;
                    return resultado;
                }
                case caracter:
                    resultado.valor = resultado1.valor.toString().charAt(0)% resultado2.valor.toString().charAt(0);
                    resultado.tipo.tipo = Tipo.TipoSimbolo.entero;
                    return resultado;
                default:
                    break;
                }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return null;
        
        }
        
        Errores.agregar_error("La operacion no es posible debido el resultado es null",fila, columna,"Semantica");
        // si llegue aqui es un error despues lo reporto
        return error;
    
    }
}
