/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;
import olc2.proyecto1_201403624.Principal_IDE;

/**
 *
 * @author Fernando
 */
public class IMPRIMIR extends Instruccion{
    
    LinkedList<Expresion> valores;
    int fila;
    int columna;

    public IMPRIMIR(LinkedList<Expresion> valores, int fila, int columna) {
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }
    
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        Expresion val = (Expresion)valores.get(0).Ejecutar(tablasimbolos);
        String impresion = "";
        if(val instanceof DEVOLVER_SIMBOLO)
        {
            DEVOLVER_SIMBOLO val_exp = (DEVOLVER_SIMBOLO)val;
            Simbolo sim = val_exp.simbolo_retorno;
            if(sim.Tipo.tipo==Tipo.TipoSimbolo.objeto
                   &&sim.Tipo.tipo_Compuesto.equals("ARREGLO")
                   &&sim.tipo_datos.tipo==Tipo.TipoSimbolo.caracter)
            {
                TRANSFORMARENCADENA literal = new TRANSFORMARENCADENA(sim);
                String cadena = literal.transformar();
                impresion = cadena;
                //valores.pop();
                int indice_entero=0;
                int indice_decimal=0;
                int indice_caracter=0;
                int indice_booleano=0;
                int indice_char_array=0;
                indice_entero = impresion.indexOf("%e");
                indice_decimal = impresion.indexOf("%d");
                indice_caracter = impresion.indexOf("%c");
                indice_booleano = impresion.indexOf("%b");
                indice_char_array = impresion.indexOf("%s");
                //tengo que componer esto
                if(valores.size()>1&&(indice_entero==-1&&
                        indice_decimal==-1&&
                        indice_caracter==-1&&
                        indice_booleano==-1&&
                        indice_char_array==-1))
                {
                    Errores.agregar_error("No se puede imprimir debido a que ni la cadena ni los parametros coinciden", fila, columna, "Semantico");
                    return null;
                }
                if(valores.size()==1&&(indice_entero!=-1&&
                        indice_decimal!=-1&&
                        indice_caracter!=-1&&
                        indice_booleano!=-1&&
                        indice_char_array!=-1))
                {
                    Errores.agregar_error("No se puede imprimir debido a que no hay lugar para sustituir los parametros y coinciden", fila, columna, "Semantico");
                    return null;
                }
                if(valores.size()>1)
                {
                    
                    for(int i = 1;i<valores.size();i++)
                    {
                        Expresion exp = valores.get(i);
                        Expresion aux = (Expresion)exp.Ejecutar(tablasimbolos);
                        if(aux.tipo.tipo==Tipo.TipoSimbolo.entero&&indice_entero!=-1)
                        {
                            impresion = impresion.replaceFirst("%e",aux.valor.toString());
                            indice_entero=-1;
                        }
                        else if(aux.tipo.tipo==Tipo.TipoSimbolo.doble&&indice_decimal!=-1)
                        {
                            impresion = impresion.replaceFirst("%d",aux.valor.toString());
                            indice_decimal=-1;
                        }
                        else if(aux.tipo.tipo==Tipo.TipoSimbolo.caracter&&indice_caracter!=-1)
                        {
                            impresion = impresion.replaceFirst("%c",aux.valor.toString());
                            indice_caracter=-1;
                        }
                        else if(aux.tipo.tipo==Tipo.TipoSimbolo.booleano&&indice_booleano!=-1)
                        {
                            impresion = impresion.replaceFirst("%b",aux.valor.toString());
                            indice_booleano=-1;
                        }
                        else if(aux.tipo.tipo==Tipo.TipoSimbolo.objeto
                                &&aux.tipo.tipo_Compuesto.equals("ARREGLO")
                                &&indice_char_array!=-1)
                        {
                            
                            DEVOLVER_SIMBOLO val_exp_2 = (DEVOLVER_SIMBOLO)aux;
                            Simbolo sim_2 = val_exp_2.simbolo_retorno;
                            if(sim.tipo_datos.tipo==Tipo.TipoSimbolo.caracter)
                            {
                                TRANSFORMARENCADENA result_s = new TRANSFORMARENCADENA(sim_2);
                                String cadena2 = result_s.transformar();
                                impresion = impresion.replaceFirst("%s",cadena2);
                                indice_char_array=-1;
                            }else
                            {
                                Errores.agregar_error("El tipo de valor que se quiere imprimir no es valido", fila, columna, "Semantico");
                                return null;
                            }
                        }else 
                        {
                            Errores.agregar_error("El tipo de valor que se quiere imprimir no es valido", fila, columna, "Semantico");
                            return null;
                        }
                        indice_entero = impresion.indexOf("%e");
                        indice_decimal = impresion.indexOf("%d");
                        indice_caracter = impresion.indexOf("%c");
                        indice_booleano = impresion.indexOf("%b");
                        indice_char_array = impresion.indexOf("%s");
                    }
                }
                indice_entero = impresion.indexOf("%e");
                indice_decimal = impresion.indexOf("%d");
                indice_caracter = impresion.indexOf("%c");
                indice_booleano = impresion.indexOf("%b");
                indice_char_array = impresion.indexOf("%s");
                if(indice_entero!=-1||indice_decimal!=-1&&indice_caracter!=-1||indice_booleano!=-1||indice_char_array!=-1)
                {
                    Errores.agregar_error("Impresion de consola erronea debido a falta de parametros", fila, columna, "Semantico");
                    
                    return null;
                }
                Principal_IDE.consola.append(""+impresion+"");
            }
            else
            {
                Errores.agregar_error("El primer parametro para imprimir debe de ser de tipo chr[]", fila, columna,"Semantico");
                return null;
            }
        }
        else
        {
            Errores.agregar_error("El primer parametro para imprimir debe de ser de tipo chr[]", fila, columna,"Semantico");
            return null;
        }
        return null;
    }
    
}
