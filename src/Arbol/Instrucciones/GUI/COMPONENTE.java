/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.GUI;

import Arbol.Entorno.Tipo;
import javax.swing.JTextArea;

/**
 *
 * @author Fernando
 */
public class COMPONENTE {
    public Tipo tipo;
    public Object componente;
    public JTextArea caja;//TEXT AREA NECESITO GUARDAR APARTE EL ELEMENTO PORQUE EN COMPONENTE SE GUARDA UN JSCROLLPANE;EN TODOS LOS DEMAS CASOS ES NULL
    public VENTANA padre;// PARA SABER A QUE VENTANA PERTENECE UN BOTON ;EN TODOS LOS DEMAS CASOS ES NULL
    /**
     * 
     * @param tipo
     * @param componente 
     */
    public COMPONENTE(Tipo tipo, Object componente) {
        this.tipo = tipo;
        this.componente = componente;
    }
    /***
     * 
     * @param tipo
     * @param componente
     * @param caja 
     */
    public COMPONENTE(Tipo tipo, Object componente,JTextArea caja) {
        this.tipo = tipo;
        this.componente = componente;
        this.caja=caja;
    }
    
}
