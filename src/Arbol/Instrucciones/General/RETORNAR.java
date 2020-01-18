/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Expresiones.Expresion;
import Arbol.Instrucciones.Instruccion;

/**
 *
 * @author Fernando
 */
public class RETORNAR extends Instruccion{
    public Expresion valor;
    public Expresion valor_de_retorno=null;
    int columna;
    int fila;

    public RETORNAR(Expresion valor, int fila, int columna)
    {
        this.valor = valor;
        this.columna = columna;
        this.fila = fila;
    }
    

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        if (Display.Verificador_tope(Display.Tipo_Instruccion.funcion)||Display.Verificador_tope(Display.Tipo_Instruccion.seleccciona)||Display.Verificador_tope(Display.Tipo_Instruccion.for_simple))
        {
            if (valor != null)
            {
                this.valor_de_retorno=(Expresion)valor.Ejecutar(tablasimbolos);
                RETORNAR nuevo = this;
                return nuevo;
            }
            return null;
        }
        Errores.agregar_error("La instruccion REGRESAR NO es valida en este ambito", fila, columna, "Semantico");
        return null;
    }
}