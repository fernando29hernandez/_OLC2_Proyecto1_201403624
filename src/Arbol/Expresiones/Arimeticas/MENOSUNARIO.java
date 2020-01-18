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
public class MENOSUNARIO extends Expresion{
    String signo;
    Expresion expresion;
    int fila;
    int columna;
    public MENOSUNARIO(String signo, Expresion expresion, int fila, int columna)
    {
        this.signo = signo;
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
        
        
        Expresion resultado =(Expresion) expresion.Ejecutar(tablasimbolos);

        if (resultado.tipo.tipo.equals(Tipo.TipoSimbolo.entero))
        {
            Double valor_ = Double.valueOf(resultado.valor.toString());
            int valor__ = valor_.intValue();
            resultado.valor = -1 * valor__;
            return resultado;

        }
        else if (resultado.tipo.tipo.equals(Tipo.TipoSimbolo.doble))
        {
            Double valor_ = Double.valueOf(resultado.valor.toString());
            resultado.valor = -1 * valor_;
            return resultado;
        }
//        else if (resultado.tipo.tipo.equals(Tipo.TipoSimbolo.caracter))
//        {
//            int valor_ = resultado.valor.toString().charAt(0);
//            resultado.valor = -1 * valor_;
//            return resultado;
//        }
        Errores.agregar_error("La operacion no es posible debido a que los tipos no se pueden operar",fila, columna,"Semantica");
            // si llegue aqui es un error despues lo reporto
        return error;
    }
}
