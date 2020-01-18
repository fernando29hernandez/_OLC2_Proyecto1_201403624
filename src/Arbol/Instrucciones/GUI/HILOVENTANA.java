/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.GUI;

import Arbol.Entorno.Display;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Fernando
 */
public class HILOVENTANA implements Runnable{
    VENTANA ventana;

    public HILOVENTANA(VENTANA ventana) {
        this.ventana = ventana;
    }
    
    
    @Override
    public void run() {
        
//        ventana.ventana= new JFrame();
//        ventana.PRECONFIGURAR_VENTANA();
//        ventana.ventana.setVisible(true);
//        ventana.ventana.addWindowListener(new WindowListener() {
//                        @Override
//                        public void windowOpened(WindowEvent we) {
//                            Display.Bandera_ACTIVA=true;
//                            System.out.println("ACTIVO");
//                        }
//
//                        @Override
//                        public void windowClosing(WindowEvent we) {
//                            Display.Bandera_ACTIVA=false;
//                            System.out.println("DESACTIVADA");
//                        }
//
//                        @Override
//                        public void windowClosed(WindowEvent we) {
//                            
//                        }
//
//                        @Override
//                        public void windowIconified(WindowEvent we) {
//                        }
//
//                        @Override
//                        public void windowDeiconified(WindowEvent we) {
//                        }
//
//                        @Override
//                        public void windowActivated(WindowEvent we) {
//                        }
//
//                        @Override
//                        public void windowDeactivated(WindowEvent we) {
//                        
//                        }
//                    });
//        
    }
    
}
