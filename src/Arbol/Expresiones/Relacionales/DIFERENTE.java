/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones.Relacionales;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.PRIMITIVO;

/**
 *
 * @author Fernando
 */
public class DIFERENTE extends Expresion{
    Expresion op1;
    Expresion op2;
    String oper;
    int columna;
    int fila;
    public DIFERENTE(Expresion op1, Expresion op2, String oper, int fila, int columna)
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
        if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.entero))
        {
            if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.entero))
            {
                Double valor_ = Double.valueOf(resultado1.valor.toString());
                int valor__ = valor_.intValue();
                Double valor_2 = Double.valueOf(resultado2.valor.toString());
                int valor__2 = valor_2.intValue();
                resultado.valor = (valor__ != valor__2);
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.doble))
            {
                Double valor_ = Double.valueOf(resultado1.valor.toString());
                Double valor_2 = Double.valueOf(resultado2.valor.toString());
                resultado.valor = (valor_.compareTo(valor_2)!=0);
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.caracter))
            {
                Double valor_ = Double.valueOf(resultado1.valor.toString());
                int valor__ = valor_.intValue();
                int valor__2 = resultado2.valor.toString().charAt(0);
                resultado.valor = valor__ != valor__2;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;


        }
        else if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.doble))
        {
            if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.entero))
            {
                Double valor_ = Double.valueOf(resultado1.valor.toString());
                Double valor_2 = Double.valueOf(resultado2.valor.toString());
                resultado.valor = (valor_.compareTo(valor_2)!=0);
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;

            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.doble))
            {
                Double valor_ = Double.valueOf(resultado1.valor.toString());
                Double valor_2 = Double.valueOf(resultado2.valor.toString());
                resultado.valor = (valor_.compareTo(valor_2)!=0);
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.caracter))
            {
                Double valor_ = Double.valueOf(resultado1.valor.toString());
                int valor__2 = resultado2.valor.toString().charAt(0);
                resultado.valor = valor_ != valor__2;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;


        }
        else if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.caracter))
        {
            if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.entero))
            {
                int valor__ = resultado1.valor.toString().charAt(0);
                Double valor_2 = Double.valueOf(resultado2.valor.toString());
                int valor__2 = valor_2.intValue();
                resultado.valor = valor__ != valor__2;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.doble))
            {
                int valor_ = resultado1.valor.toString().charAt(0);
                Double valor_2 = Double.valueOf(resultado2.valor.toString());
                resultado.valor = valor_ != valor_2;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.caracter))
            {
                int valor__ = resultado1.valor.toString().charAt(0);
                int valor__2 = resultado2.valor.toString().charAt(0);
                resultado.valor = valor__ != valor__2;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;

        }
        else if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.booleano))
        {
            if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.booleano))
            {

                resultado.valor = Boolean.parseBoolean(resultado1.valor.toString()) != Boolean.parseBoolean(resultado2.valor.toString());
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;

        }
        else if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.nulo))
        {
            if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.objeto))
            {
                resultado.valor = resultado1.valor != resultado2.valor;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.nulo))
            {
                resultado.valor = resultado1.valor != resultado2.valor;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;

        }
        else if (resultado1.tipo.tipo.equals(Tipo.TipoSimbolo.objeto))
        {
            if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.nulo))
            {
                resultado.valor = resultado1.valor != resultado2.valor;
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            else if (resultado2.tipo.tipo.equals(Tipo.TipoSimbolo.objeto))
            {
                resultado.valor = resultado1.valor.hashCode() != (resultado2.valor.hashCode());
                resultado.tipo.tipo = Tipo.TipoSimbolo.booleano;
                return resultado;
            }
            Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
            return error;
        }
        Errores.agregar_error("La operacion no es posible debido a que el resultado es null ",fila, columna,"Semantica");
        // si llegue aqui es un error despues lo reporto
        return error;
    }
    
}
