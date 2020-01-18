/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.GUI.HILOVENTANA;
import Arbol.Instrucciones.GUI.VENTANA;
import Arbol.Instrucciones.Instruccion;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Fernando
 */
public class MOSTRAR_VENTANA extends Instruccion{
    Expresion expresion;
    int fila;
    int columna;

    public MOSTRAR_VENTANA(Expresion expresion, int fila, int columna) {
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
    }
    
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        Expresion exp =(Expresion)expresion.Ejecutar(tablasimbolos);
        if(exp instanceof DEVOLVER_SIMBOLO)
        {
            DEVOLVER_SIMBOLO val_exp = (DEVOLVER_SIMBOLO)exp;
            Simbolo sim = val_exp.simbolo_retorno;
            if(sim.Tipo.tipo==Tipo.TipoSimbolo.objeto
                   &&sim.Tipo.tipo_Compuesto.equals("ARREGLO")
                   &&sim.tipo_datos.tipo==Tipo.TipoSimbolo.caracter)
            {
                TRANSFORMARENCADENA literal = new TRANSFORMARENCADENA(sim);
                String cadena = literal.transformar();
                Simbolo ventana_buscada = tablasimbolos.buscar("VENTANA_"+cadena    , fila, columna);
                if(ventana_buscada!=null)
                {
                    VENTANA mi_ventana=(VENTANA)ventana_buscada.Valor; 
                    mi_ventana.ventana= new JFrame();
                    mi_ventana.ventana.setLayout(null);
                    mi_ventana.PRECONFIGURAR_VENTANA();
                    Display.Constructor=true;
                    Display.nombre_ventana=mi_ventana.nombre;
                    Entorno nuevo =new Entorno(Display.GLOBAL);
                    Display.display.addFirst(Display.Tipo_Instruccion.funcion);
                    for(Instruccion ins:mi_ventana.instrucciones)
                    {
                        ins.Ejecutar(nuevo);
                    }
                    Display.display.removeFirst();
                    Display.Constructor=false;
                    Display.nombre_ventana="";
                    
                    mi_ventana.ventana.setVisible(true);
                    Display.Bandera_ACTIVA=true;
                    System.out.println("ACTIVA");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mi_ventana.ventana.addWindowListener(new WindowListener() {
                                    @Override
                                    public void windowOpened(WindowEvent we) {
                                        Display.Bandera_ACTIVA=true;
                                        System.out.println("ACTIVA");
                                    
                                    }

                                    @Override
                                    public void windowClosing(WindowEvent we) {
                                        Display.Bandera_ACTIVA=false;
                                        System.out.println("DESACTIVA"+Display.Bandera_ACTIVA);
                                    }

                                    @Override
                                    public void windowClosed(WindowEvent we) {
                                    
                                    }

                                    @Override
                                    public void windowIconified(WindowEvent we) {
                                    }

                                    @Override
                                    public void windowDeiconified(WindowEvent we) {
                                    }

                                    @Override
                                    public void windowActivated(WindowEvent we) {
                                    
                                    }

                                    @Override
                                    public void windowDeactivated(WindowEvent we) {
                                    
                                    }
                                });
                        }
                    });
                    thread.start();
                    
                }
                else
                {
                    Errores.agregar_error("No Existe una ventana con este nombre", fila, columna,"Semantico");
                    return null;
                }
            }
            else
            {
                Errores.agregar_error("El paramatro para llamar ventana no es correcto", fila, columna,"Semantico");
                return null;
            }
        }else
        {
            Errores.agregar_error("El paramatro para llamar ventana no es correcto", fila, columna,"Semantico");
            return null;
        }
        return null;
    }
    
}
