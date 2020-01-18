/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tamano;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.OBJETO_ARREGLO;
import Arbol.Expresiones.PRIMITIVO;
import Arbol.Instrucciones.Instruccion;

/**
 *
 * @author Fernando
 */
public class CONSTANTE extends Instruccion{
    String identificador;
    Expresion expresion;
    int fila;
    int columna;

    public CONSTANTE(String identificador, Expresion expresion, int fila, int columna) {
        this.identificador = identificador;
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }
    
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        Expresion exp = (Expresion)expresion.Ejecutar(tablasimbolos);
        if(exp instanceof DEVOLVER_SIMBOLO)
        {
           DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO)exp;
           Simbolo recuperado = temp.simbolo_retorno;
           if(recuperado.Tipo.tipo_Compuesto.equals("ARREGLO"))
           {
                Simbolo simbolo = recuperado;
                recuperado.Identificador=identificador;
                simbolo.constante=true;
                tablasimbolos.insertar(identificador, simbolo, fila, columna);
                return null;
           }
        }
        else if (exp instanceof PRIMITIVO)
        {
                PRIMITIVO prim = (PRIMITIVO)exp;
                Simbolo nuevo = new Simbolo(prim.tipo,identificador,prim.valor,true);
                nuevo.constante=true;
                tablasimbolos.insertar(identificador, nuevo, fila, columna);
                return null;
        }
        Errores.agregar_error("No se puede definir una constante del tipo objeto", fila, columna,"Semantico");
        return null;
    }
    
}
