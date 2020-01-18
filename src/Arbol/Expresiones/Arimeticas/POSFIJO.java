/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones.Arimeticas;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.PRIMITIVO;

/**
 *
 * @author Fernando
 */
public class POSFIJO extends Expresion {
    String identificador;
    String incremento;
    int fila;
    int columna;
    public POSFIJO(String identificador, String incremento, int fila, int columna)
    {
        this.identificador = identificador;
        this.incremento = incremento;
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error),"error", fila, columna);
        
        Simbolo simbolo_recuperado = tablasimbolos.buscar(identificador,fila,columna);
        if (simbolo_recuperado!=null)
        {

            if (simbolo_recuperado.Tipo.tipo.equals(Tipo.TipoSimbolo.entero))
            {
                if (incremento.equals("++"))
                {
                    Double valor_ = Double.parseDouble(simbolo_recuperado.Valor.toString());
                    int valor_antiguo = valor_.intValue() ;
                    simbolo_recuperado.Valor = valor_antiguo + 1;
                    return new PRIMITIVO(simbolo_recuperado.Tipo, valor_antiguo+1, fila, columna);
                }
                else
                {
                    Double valor_ = Double.parseDouble(simbolo_recuperado.Valor.toString());
                    int valor_antiguo = valor_.intValue();
                    simbolo_recuperado.Valor = valor_antiguo - 1;
                    return new PRIMITIVO(simbolo_recuperado.Tipo, valor_antiguo-1, fila, columna);
                }

            }
            else if (simbolo_recuperado.Tipo.tipo.equals(Tipo.TipoSimbolo.doble))
            {
                if (incremento.equals("++"))
                {
                    Double valor_antiguo = Double.parseDouble(simbolo_recuperado.Valor.toString());
                    simbolo_recuperado.Valor = valor_antiguo + 1.0;
                    return new PRIMITIVO(simbolo_recuperado.Tipo, valor_antiguo+1.0, fila, columna);

                }
                else
                {
                    Double valor_antiguo = Double.parseDouble(simbolo_recuperado.Valor.toString());
                    simbolo_recuperado.Valor = valor_antiguo - 1.0;
                    return new PRIMITIVO(simbolo_recuperado.Tipo, valor_antiguo-1.0, fila, columna);
                }
            }
//            else if (simbolo_recuperado.Tipo.tipo.equals(Tipo.TipoSimbolo.caracter))
//            {
//                if (incremento.equals("++"))
//                {
//                    int valor_antiguo = simbolo_recuperado.Valor.toString().charAt(0);
//                    int valor_nuevo = (int) (valor_antiguo + 1.0);
//                    simbolo_recuperado.Valor = String.valueOf(valor_nuevo);
//                    return new PRIMITIVO(simbolo_recuperado.Tipo, valor_antiguo, fila, columna);
//
//                }
//                else
//                {
//                    int valor_antiguo = simbolo_recuperado.Valor.toString().charAt(0);
//                    int valor_nuevo = (int) (valor_antiguo - 1.0);
//                    simbolo_recuperado.Valor = String.valueOf(valor_nuevo);
//                    return new PRIMITIVO(simbolo_recuperado.Tipo, valor_antiguo, fila, columna);
//                }
//            }
            return error;
        }
        return error;
    }
}  
