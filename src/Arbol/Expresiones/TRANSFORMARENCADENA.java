/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;

/**
 *
 * @author Fernando
 */
public class TRANSFORMARENCADENA {
   
    Simbolo cadena;
    
    public TRANSFORMARENCADENA(Simbolo cadena) {
        this.cadena = cadena;
    }
    
    public String transformar()
    {
        String resultado ="";
        
        OBJETO_ARREGLO obj = (OBJETO_ARREGLO)cadena.Valor;
        for(Simbolo exp: obj.lista_elementos)
        {
            if(exp.Valor.toString().equals(""))
            {
            
            }
            else
            {
                resultado+=exp.Valor.toString();
            }
        }
        return resultado;
    }
    
}
