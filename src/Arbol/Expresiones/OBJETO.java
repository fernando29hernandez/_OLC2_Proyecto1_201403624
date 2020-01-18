/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import java.util.Set;

/**
 *
 * @author Fernando
 */
public class OBJETO extends Expresion{
    public String identificador;
    public Entorno entorno_struct;

    public OBJETO(String identificador,Entorno entorno_struct)
    {
        //this.tipo = new Tipo(Tipo.TipoSimbolo.objeto,identificador);
        //this.valor = "OBJETO_TIPO_" + identificador + "_" + this.GetHashCode();
        this.identificador = identificador;
        this.entorno_struct = entorno_struct;
        this.valor = getValor();
    }
    public String getValor()
    {
        String valor__ = "{";
        int indice = 0;
        Set<String> keys = entorno_struct.tabla.keySet();
        for(String key: keys){
        
            Simbolo aux = entorno_struct.tabla.get(key);
            if (aux.Valor == null)
            {
                valor__ += "null";
            }
            else if (aux.Valor instanceof OBJETO)
            {
                OBJETO aux_objeto = (OBJETO)aux.Valor;
                valor__ += aux_objeto.getValor().toString();

            }
            else
            {
                valor__ += aux.Valor.toString();
            }
            if (!(indice+1==entorno_struct.tabla.size()))
            {
                valor__ += ",";
            }
            indice++;
        }
        valor__ += "}";
        return valor__;
    }
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        return this;
    }
}
