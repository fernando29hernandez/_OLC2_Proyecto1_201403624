/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Instrucciones.Instruccion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class CERRAR extends Instruccion{
    int fila;
    int columna;

    public CERRAR(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }
    
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        
        if(Display.archivo_abrir!=null)
        {
            
            try {
                if(Display.br!=null)
                {
                    Display.br.close();
                
                }
                if(Display.bw!=null)
                {
                    Display.bw.close();
                }
            } catch (IOException ex) {
                //Logger.getLogger(CERRAR.class.getName()).log(Level.SEVERE, null, ex);
            }
            Display.bw=null;
            Display.br=null;
            Display.archivo_abrir=null;
            
        }else
        {
            Display.bw=null;
            Display.br=null;
            Display.archivo_abrir=null;
            
            Errores.agregar_error("No se encuentra ningun archivo de texto en uso", fila, columna, "Semantico");
            return null;
        
        }
        return null;
    }
    /***
     * ME FALTA QUE EN LA ASIGNACION SE PUEDAN PASAR RSTRING
     * 
     */
    
}
