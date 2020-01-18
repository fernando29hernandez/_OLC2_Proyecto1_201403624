/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.GUI;

import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;
import javax.swing.JFrame;

/**
 *
 * @author Fernando
 */
public class VENTANA {
    public String nombre;
    public JFrame ventana;
    public LinkedList<Instruccion>instrucciones;
    
    public VENTANA(String nombre, LinkedList<Instruccion> instrucciones) {
        this.nombre=nombre;
        this.instrucciones = instrucciones;
        //this.ventana=new JFrame();
    }
    public void PRECONFIGURAR_VENTANA()
    {
        this.ventana.setSize(600,400);
        this.ventana.setResizable(false);
        this.ventana.setLocationRelativeTo(null);
    
    }
}
