/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tamano;
import Arbol.Entorno.Tipo;
import Arbol.Instrucciones.GUI.COMPONENTE;
import Arbol.Instrucciones.GUI.OBJETO_RSTRING;
import Arbol.Instrucciones.GUI.VENTANA;
import static Arbol.Instrucciones.General.DECLARACION_ARREGLO.CREAR_FALTANTES;
import Arbol.Instrucciones.General.FUSION;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Fernando
 */
public class FUNCION_NATIVA extends Expresion {

    String tipo;
    LinkedList<Expresion> valores;
    int fila;
    int columna;
    Object componente;
    Simbolo buscar_ventana;

    public FUNCION_NATIVA(String tipo, LinkedList<Expresion> valores, int fila, int columna) {
        this.tipo = tipo;
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
    }

    public void setComponente(Object componente) {
        this.componente = componente;

    }

    private boolean IsInt_ByException(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private boolean IsDouble_ByException(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "error", 0, 0);
        switch (tipo) {
            case "pesode":
                if (valores.size() > 0 && valores.size() == 1) {
                    Expresion exp = valores.get(0);
                    if (!(exp instanceof ID)) {
                        Errores.agregar_error("No se puede efectuar _pesode dado a que el parametro no es ID", fila, columna, "Semantico");
                        return error;
                    }
                    ID resultado = (ID) exp;
                    Simbolo fusion_buscado = tablasimbolos.buscarsinreportar("FUSION_" + resultado.identificador, fila, columna);
                    if (fusion_buscado != null) {
                        FUSION temporal = (FUSION) fusion_buscado.Valor;
                        return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), temporal.variables.size(), 0, 0);
                    }
                    Simbolo variable_buscada = tablasimbolos.buscarsinreportar(resultado.identificador, fila, columna);
                    if (variable_buscada != null) {
                        if (variable_buscada.Valor == null) {
                            Errores.agregar_error("NO se puede calcular el pesode un valor nulo", fila, columna, "Semantico");

                            return error;
                        }
                        if (variable_buscada.Valor instanceof OBJETO) {
                            OBJETO temp = (OBJETO) variable_buscada.Valor;
                            return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), temp.entorno_struct.tabla.size(), 0, 0);

                        } else if (variable_buscada.Valor instanceof OBJETO_ARREGLO) {
                            OBJETO_ARREGLO temp = (OBJETO_ARREGLO) variable_buscada.Valor;
                            if (variable_buscada.dimensiones > 1) {
                                return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), variable_buscada.dimensiones, 0, 0);
                            }
                            return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), temp.lista_elementos.size(), 0, 0);

                        } else if (variable_buscada.Valor instanceof OBJETO_RSTRING) {
                            OBJETO_RSTRING temp = (OBJETO_RSTRING) variable_buscada.Valor;
                            if (temp.cadena.equals("")) {
                                return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), 1, 0, 0);

                            }
                            System.out.println("el valor de RSTRING COMO PESO DE ES:"+temp.valor.toString().length());
                            return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), temp.valor.toString().length(), 0, 0);

                        } else {
                            return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), 1, 0, 0);
                        }

                    }
                    Errores.agregar_error("NO se encontro variable o fusion alguna para obtener su tamaño", fila, columna, "Semantico");

                    return error;
                } else {
                    Errores.agregar_error("La funcion _pesode necesita un parametro", fila, columna, "Semantico");
                    return error;
                }
            case "reservar":
                if (valores.size() > 0 && valores.size() == 1) {
                    Expresion exp = valores.get(0);
                    Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                    if (resultado.tipo.tipo == Tipo.TipoSimbolo.entero) {
                        return new INSTANCIAOBJETO(Integer.parseInt(resultado.valor.toString()));
                    }
                    Errores.agregar_error("La funcion no soporta parametros que no sean de tipo INT", fila, columna, "Semantico");
                    return error;
                } else {
                    Errores.agregar_error("La funcion _pesode necesita un parametro", fila, columna, "Semantico");
                    return error;
                }

            case "atexto":
                if (valores.size() > 0 && valores.size() == 1) {
                    Expresion exp = valores.get(0);
                    Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                    if (resultado.tipo.tipo == Tipo.TipoSimbolo.entero) {
                        String valor_a_convertir = resultado.valor.toString();
                        CADENA arr = new CADENA(new Tipo(Tipo.TipoSimbolo.cadena), valor_a_convertir, 0, 0);
                        Expresion exp_arr = (Expresion) arr.Ejecutar(tablasimbolos);
                        return exp_arr;
                    } else if (resultado.tipo.tipo == Tipo.TipoSimbolo.doble) {
                        String valor_a_convertir = resultado.valor.toString();
                        CADENA arr = new CADENA(new Tipo(Tipo.TipoSimbolo.cadena), valor_a_convertir, 0, 0);
                        Expresion exp_arr = (Expresion) arr.Ejecutar(tablasimbolos);
                        return exp_arr;
                    } else if (resultado.tipo.tipo == Tipo.TipoSimbolo.componente && resultado.tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                        if (resultado.valor == null) {
                            return error;
                        }
                        OBJETO_RSTRING cad = (OBJETO_RSTRING) resultado.valor;
                        String valor_a_convertir = cad.cadena;
                        CADENA arr = new CADENA(new Tipo(Tipo.TipoSimbolo.cadena), valor_a_convertir, 0, 0);
                        Expresion exp_arr = (Expresion) arr.Ejecutar(tablasimbolos);
                        return exp_arr;
                    }
                    // aqui va el RSTRING
                    Errores.agregar_error("La funcion no soporta parametros que no sean de tipo pritivos", fila, columna, "Semantico");
                    return error;
                } else {
                    Errores.agregar_error("La funcion _atxt necesita un parametro", fila, columna, "Semantico");
                    return error;
                }
            case "concatenar":
                if (valores.size() > 0 && valores.size() == 2) {
                    Expresion exp = valores.get(0);
                    Expresion exp2 = valores.get(1);
                    Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                    Expresion resultado2 = (Expresion) exp2.Ejecutar(tablasimbolos);
                    if (!resultado.tipo.tipo_Compuesto.equals("ARREGLO") || !resultado2.tipo.tipo_Compuesto.equals("ARREGLO")) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");
                        return error;
                    }
                    if (!(resultado instanceof DEVOLVER_SIMBOLO) && !(resultado instanceof DEVOLVER_SIMBOLO)) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");
                        return error;
                    }
                    DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) resultado;
                    Simbolo sim = (Simbolo) temp.simbolo_retorno;
                    DEVOLVER_SIMBOLO temp2 = (DEVOLVER_SIMBOLO) resultado2;
                    Simbolo sim2 = (Simbolo) temp2.simbolo_retorno;
                    int comprobartamano = sim.tams.tamano_actual - sim.tams.tamano_actual;
                    if (sim.dimensiones == sim2.dimensiones
                            && comprobartamano >= 0
                            && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter && Tipo.TipoSimbolo.caracter == sim2.tipo_datos.tipo) {

                        OBJETO_ARREGLO arreglo_destino = (OBJETO_ARREGLO) sim.Valor;
                        OBJETO_ARREGLO arreglo_partida = (OBJETO_ARREGLO) sim2.Valor;
                        int primer_disponible = 1;
                        for (Simbolo a : arreglo_destino.lista_elementos) {
                            if ((a.Valor.toString().equals(""))) {
                                break;
                            }
                            primer_disponible++;
                        }
                        int verificacion = sim.tams.tamano_actual - (primer_disponible-1);
                        Boolean resultado_pasar = verificacion >= sim2.tams.tamano_actual;
                        if (resultado_pasar) {
                            primer_disponible = primer_disponible - 1;
                            for (int i = 0; i < arreglo_partida.lista_elementos.size(); i++) {
                                Simbolo temporal = arreglo_partida.lista_elementos.get(i);
                                Simbolo destino = arreglo_destino.lista_elementos.get(primer_disponible);
                                destino.Valor = temporal.Valor;
                                primer_disponible++;
                            }
                            return temp;
                        } else {
                            Errores.agregar_error("El primer arreglo de caracteres no tiene el tamano para soportar concatenar la segunda cadena", fila, columna, tipo);
                            return error;
                        }
                    }
                    // suponiendo que tenemos chr[20] con 15 ocupados y chr [6] con todos ocupados  20-
                    // aqui va el RSTRING
                    Errores.agregar_error("La funcion no soporta parametros que no sean de tipo chr[]", fila, columna, "Semantico");
                    return error;
                } else {
                    Errores.agregar_error("La funcion _aconc necesita dos parametro", fila, columna, "Semantico");
                    return error;
                }

            case "aentero":
                if (valores.size() > 0 && valores.size() == 1) {
                    Expresion exp = valores.get(0);
                    Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                    if (!resultado.tipo.tipo_Compuesto.equals("ARREGLO")) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");
                        return error;
                    }
                    if (!(resultado instanceof DEVOLVER_SIMBOLO)) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");

                        return error;
                    }
                    DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) resultado;
                    Simbolo sim = (Simbolo) temp.simbolo_retorno;
                    if (sim.dimensiones == 1
                            && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter) {
                        TRANSFORMARENCADENA cad = new TRANSFORMARENCADENA(sim);
                        String transform = cad.transformar();
                        Boolean es_ent = IsInt_ByException(transform);
                        if (es_ent) {
                            int valor_retorno = Integer.parseInt(transform);
                            return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), valor_retorno, 0, 0);
                        } else {
                            Errores.agregar_error("La cadena ingresada para la conversion no representa un entero", fila, columna, "Semantico");
                            return error;
                        }
                    }
                    // aqui va el RSTRING
                    Errores.agregar_error("La funcion no soporta parametros que no sean de tipo chr[]", fila, columna, "Semantico");
                    return error;
                } else {
                    Errores.agregar_error("La funcion _aconc necesita dos parametro", fila, columna, "Semantico");
                    return error;
                }
            case "adecimal":
                if (valores.size() > 0 && valores.size() == 1) {
                    Expresion exp = valores.get(0);
                    Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                    if (!resultado.tipo.tipo_Compuesto.equals("ARREGLO")) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");
                        return error;
                    }
                    if (!(resultado instanceof DEVOLVER_SIMBOLO)) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");

                        return error;
                    }
                    DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) resultado;
                    Simbolo sim = (Simbolo) temp.simbolo_retorno;
                    if (sim.dimensiones == 1
                            && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter) {
                        TRANSFORMARENCADENA cad = new TRANSFORMARENCADENA(sim);
                        String transform = cad.transformar();
                        Boolean es_ent = IsDouble_ByException(transform);
                        if (es_ent) {
                            Double valor_retorno = Double.valueOf(transform);
                            return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.doble), valor_retorno, 0, 0);
                        } else {
                            Errores.agregar_error("La cadena ingresada para la conversion no representa un entero", fila, columna, "Semantico");
                            return error;
                        }
                    }
                    // aqui va el RSTRING
                    Errores.agregar_error("La funcion no soporta parametros que no sean de tipo chr[]", fila, columna, "Semantico");
                    return error;
                } else {
                    Errores.agregar_error("La funcion _aconc necesita dos parametro", fila, columna, "Semantico");
                    return error;
                }
            case "compequals":
                if (valores.size() > 0 && valores.size() == 2) {
                    Expresion exp = valores.get(0);
                    Expresion exp2 = valores.get(1);
                    Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                    Expresion resultado2 = (Expresion) exp2.Ejecutar(tablasimbolos);
                    if (!resultado.tipo.tipo_Compuesto.equals("ARREGLO") && !resultado2.tipo.tipo_Compuesto.equals("ARREGLO")) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");
                        return error;
                    }
                    if (!(resultado instanceof DEVOLVER_SIMBOLO) && !(resultado2 instanceof DEVOLVER_SIMBOLO)) {
                        Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");

                        return error;
                    }
                    DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) resultado;
                    Simbolo sim = (Simbolo) temp.simbolo_retorno;
                    DEVOLVER_SIMBOLO temp2 = (DEVOLVER_SIMBOLO) resultado2;
                    Simbolo sim2 = (Simbolo) temp2.simbolo_retorno;
                    int comprobartamano = sim.tams.tamano_actual - sim.tams.tamano_actual;
                    if (sim.dimensiones == sim2.dimensiones
                            && comprobartamano == 0
                            && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter && Tipo.TipoSimbolo.caracter == sim2.tipo_datos.tipo) {

                        OBJETO_ARREGLO arreglo_destino = (OBJETO_ARREGLO) sim.Valor;
                        OBJETO_ARREGLO arreglo_partida = (OBJETO_ARREGLO) sim2.Valor;
                        for (int i = 0; i < arreglo_partida.lista_elementos.size(); i++) {
                            Simbolo temporal = arreglo_partida.lista_elementos.get(i);
                            Simbolo destino = arreglo_destino.lista_elementos.get(i);
                            if (!(temporal.Valor.toString().equals(destino.Valor.toString()))) {
                                return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.booleano), false, 0, 0);
                            }
                        }
                        return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.booleano), true, 0, 0);
                    }
                    return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.booleano), false, 0, 0);

                } else {
                    Errores.agregar_error("La funcion _aconc necesita dos parametro", fila, columna, "Semantico");
                    return error;
                }
            case "tamform":
                Simbolo buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {
                    if (valores.size() > 0 && valores.size() == 2) {
                        Expresion prueba = (Expresion) valores.get(0);
                        Expresion prueba2 = (Expresion) valores.get(1);
                        prueba = (Expresion) prueba.Ejecutar(tablasimbolos);
                        prueba2 = (Expresion) prueba2.Ejecutar(tablasimbolos);
                        if (Tipo.TipoSimbolo.entero == prueba.tipo.tipo && Tipo.TipoSimbolo.entero == prueba2.tipo.tipo) {
                            VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                            ventana.ventana.setSize(Integer.parseInt(prueba.valor.toString()), Integer.parseInt(prueba2.valor.toString()));
                            ventana.ventana.repaint();
                            return null;
                        } else {
                            Errores.agregar_error("Los valores de configuracion de la ventana no son entero", fila, columna, "Semantico");
                            return null;
                        }

                    }
                    Errores.agregar_error("Los cantidad de parametros invalida", fila, columna, "Semantico");
                    return null;

                }
                Errores.agregar_error("Imposible usar la funcion fuera de un constructor de ventana", fila, columna, "Semantico");
                return null;

            case "settexto":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {
                    if (valores.size() > 0 && valores.size() == 1) {
                        Expresion exp = valores.get(0);
                        Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                        if (resultado.tipo.tipo == Tipo.TipoSimbolo.componente && resultado.tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                            if (resultado.valor == null) {
                                return error;
                            }
                            /**
                             * *
                             * Aqui creo que solo el RESULTADO COMO TAL PORQUE
                             * EL VALOR ES EL STRING
                             */
                            String transform = "";

                            if (resultado instanceof DEVOLVER_SIMBOLO) {
                                DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) resultado;
                                Simbolo sim = (Simbolo) temp.simbolo_retorno;
                                OBJETO_RSTRING cad = (OBJETO_RSTRING) sim.Valor;
                                transform = cad.cadena;

                            } else if (resultado instanceof OBJETO_RSTRING) {
                                OBJETO_RSTRING cad = (OBJETO_RSTRING) resultado;
                                transform = cad.cadena;
                            }

                            VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                            if (!(componente instanceof COMPONENTE)) {
                                Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                                return error;
                            }
                            COMPONENTE comp = (COMPONENTE) componente;
                            if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                                JLabel temp_comp = (JLabel) comp.componente;
                                temp_comp.setText(transform);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                                JTextField temp_comp = (JTextField) comp.componente;
                                temp_comp.setText(transform);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                                JTextArea temp_comp = (JTextArea) comp.caja;
                                temp_comp.setText(transform);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                                JSpinner  temp_comp = (JSpinner ) comp.componente;
                                temp_comp.setValue(transform);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                                JPasswordField temp_comp = (JPasswordField) comp.componente;
                                temp_comp.setText(transform);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                                JButton temp_comp = (JButton) comp.componente;
                                temp_comp.setText(transform);
                            }
                            ventana.ventana.repaint();
                            return null;
                        } else if (resultado.tipo.tipo_Compuesto.equals("ARREGLO")) {
                            DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) resultado;
                            Simbolo sim = (Simbolo) temp.simbolo_retorno;
                            if (sim.dimensiones == 1
                                    && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter
                                    && sim.Pertenece) {
                                TRANSFORMARENCADENA cad = new TRANSFORMARENCADENA(sim);
                                String transform = cad.transformar();
                                VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                                if (!(componente instanceof COMPONENTE)) {
                                    Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                                    return error;
                                }

                                COMPONENTE comp = (COMPONENTE) componente;
                                if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                                    JLabel temp_comp = (JLabel) comp.componente;
                                    temp_comp.setText(transform);
                                } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                                    JTextField temp_comp = (JTextField) comp.componente;
                                    temp_comp.setText(transform);
                                } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                                    JTextArea temp_comp = (JTextArea) comp.caja;
                                    temp_comp.setText(transform);
                                } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                                    JSpinner  temp_comp = (JSpinner ) comp.componente;
                                    temp_comp.setValue(transform);
                                } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                                    JPasswordField temp_comp = (JPasswordField) comp.componente;
                                    temp_comp.setText(transform);
                                } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                                    JButton temp_comp = (JButton) comp.componente;
                                    temp_comp.setText(transform);
                                }
                                ventana.ventana.repaint();
                                return null;
                            } else {
                                Errores.agregar_error("No se aceptan chr[]", fila, columna, "Semantico");
                            }
                        } //aqui va el otro caso con RSTRING
                        else {
                            Errores.agregar_error("Los parametros enviados no son del tipo chr[]", fila, columna, "Semantico");
                            return error;

                        }
                    } else {
                        Errores.agregar_error("Los valores de configuracion de la ventana no son entero", fila, columna, "Semantico");
                        return null;
                    }

                }

                Errores.agregar_error("Imposible usar la funcion fuera de un constructor de ventana", fila, columna, "Semantico");
                return null;

            case "setalto":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {
                    if (valores.size() > 0 && valores.size() == 1) {
                        Expresion exp = valores.get(0);
                        Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                        //aqui meto tambien el RSTRING
                        if (resultado.tipo.tipo == Tipo.TipoSimbolo.entero) {

                            int valor_ = Integer.parseInt(resultado.valor.toString());
                            VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                            if (!(componente instanceof COMPONENTE)) {
                                Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                                return error;
                            }
                            COMPONENTE comp = (COMPONENTE) componente;
                            if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                                JLabel temp_comp = (JLabel) comp.componente;
                                temp_comp.setSize(temp_comp.getSize().width, valor_);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                                JTextField temp_comp = (JTextField) comp.componente;
                                temp_comp.setSize(temp_comp.getSize().width, valor_);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                                JScrollPane temp_comp = (JScrollPane) comp.componente;
                                temp_comp.setSize(temp_comp.getSize().width, valor_);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                                JSpinner  temp_comp = (JSpinner ) comp.componente;
                                temp_comp.setSize(temp_comp.getSize().width, valor_);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                                JPasswordField temp_comp = (JPasswordField) comp.componente;
                                temp_comp.setSize(temp_comp.getSize().width, valor_);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                                JButton temp_comp = (JButton) comp.componente;
                                temp_comp.setSize(temp_comp.getSize().width, valor_);
                            }
                            ventana.ventana.repaint();
                            return null;
                        } //aqui va el otro caso con RSTRING
                        else {
                            Errores.agregar_error("Los parametros enviados no son del tipo entero", fila, columna, "Semantico");
                            return error;

                        }
                    } else {
                        Errores.agregar_error("Los cantidad de parametros invalida", fila, columna, "Semantico");
                        return null;
                    }
                }

                Errores.agregar_error("Imposible usar la funcion fuera de un constructor de ventana", fila, columna, "Semantico");
                return null;

            case "setancho":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {
                    if (valores.size() > 0 && valores.size() == 1) {
                        Expresion exp = valores.get(0);
                        Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                        //aqui meto tambien el RSTRING
                        if (resultado.tipo.tipo == Tipo.TipoSimbolo.entero) {

                            int valor_ = Integer.parseInt(resultado.valor.toString());
                            VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                            if (!(componente instanceof COMPONENTE)) {
                                Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                                return error;
                            }
                            COMPONENTE comp = (COMPONENTE) componente;
                            if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                                JLabel temp_comp = (JLabel) comp.componente;
                                temp_comp.setSize(valor_, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                                JTextField temp_comp = (JTextField) comp.componente;
                                temp_comp.setSize(valor_, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                                JScrollPane temp_comp = (JScrollPane) comp.componente;
                                temp_comp.setSize(valor_, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                                JSpinner  temp_comp = (JSpinner ) comp.componente;
                                temp_comp.setSize(valor_, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                                JPasswordField temp_comp = (JPasswordField) comp.componente;
                                temp_comp.setSize(valor_, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                                JButton temp_comp = (JButton) comp.componente;
                                temp_comp.setSize(valor_, temp_comp.getSize().height);
                            }
                            ventana.ventana.repaint();
                            return null;
                        } //aqui va el otro caso con RSTRING
                        else {
                            Errores.agregar_error("Los parametros enviados no son del tipo entero", fila, columna, "Semantico");
                            return error;

                        }
                    } else {
                        Errores.agregar_error("Los cantidad de parametros invalida", fila, columna, "Semantico");
                        return null;
                    }
                }

                Errores.agregar_error("Imposible usar la funcion fuera de un constructor de ventana", fila, columna, "Semantico");
                return null;

            case "setpos":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {
                    if (valores.size() > 0 && valores.size() == 2) {
                        Expresion exp = valores.get(0);
                        Expresion resultado = (Expresion) exp.Ejecutar(tablasimbolos);
                        Expresion exp2 = valores.get(1);
                        Expresion resultado2 = (Expresion) exp2.Ejecutar(tablasimbolos);

//aqui meto tambien el RSTRING
                        if (resultado.tipo.tipo == Tipo.TipoSimbolo.entero && resultado2.tipo.tipo == Tipo.TipoSimbolo.entero) {

                            int valor_ = Integer.parseInt(resultado.valor.toString());
                            int valor_2 = Integer.parseInt(resultado2.valor.toString());

                            VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                            if (!(componente instanceof COMPONENTE)) {
                                Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                                return error;
                            }
                            COMPONENTE comp = (COMPONENTE) componente;
                            if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                                JLabel temp_comp = (JLabel) comp.componente;
                                temp_comp.setBounds(valor_, valor_2, temp_comp.getSize().width, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                                JTextField temp_comp = (JTextField) comp.componente;
                                temp_comp.setBounds(valor_, valor_2, temp_comp.getSize().width, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                                JScrollPane temp_comp = (JScrollPane) comp.componente;
                                temp_comp.setBounds(valor_, valor_2, temp_comp.getSize().width, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                                JSpinner  temp_comp = (JSpinner ) comp.componente;
                                temp_comp.setBounds(valor_, valor_2, temp_comp.getSize().width, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                                JPasswordField temp_comp = (JPasswordField) comp.componente;
                                temp_comp.setBounds(valor_, valor_2, temp_comp.getSize().width, temp_comp.getSize().height);
                            } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                                JButton temp_comp = (JButton) comp.componente;
                                temp_comp.setBounds(valor_, valor_2, temp_comp.getSize().width, temp_comp.getSize().height);
                            }
                            ventana.ventana.repaint();
                            return null;
                        } //aqui va el otro caso con RSTRING
                        else {
                            Errores.agregar_error("Los parametros enviados no son del tipo entero", fila, columna, "Semantico");
                            return error;

                        }
                    } else {
                        Errores.agregar_error("Los cantidad de parametros invalida", fila, columna, "Semantico");
                        return null;
                    }
                }

                Errores.agregar_error("Imposible usar la funcion fuera de un constructor de ventana", fila, columna, "Semantico");
                return null;

            case "gettexto":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {

                    VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                    if (!(componente instanceof COMPONENTE)) {
                        Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                        return error;
                    }
                    COMPONENTE comp = (COMPONENTE) componente;
                    String cadena_extraida = "";
                    if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                        JLabel temp_comp = (JLabel) comp.componente;
                        cadena_extraida = temp_comp.getText();

                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                        JTextField temp_comp = (JTextField) comp.componente;
                        cadena_extraida = temp_comp.getText();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                        JScrollPane temp_comp = (JScrollPane) comp.componente;
                        JTextArea caja = comp.caja;
                        cadena_extraida = caja.getText();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                        JSpinner  temp_comp = (JSpinner ) comp.componente;
                        cadena_extraida = temp_comp.getValue().toString();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                        JPasswordField temp_comp = (JPasswordField) comp.componente;
                        cadena_extraida = temp_comp.getText();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                        JButton temp_comp = (JButton) comp.componente;
                        cadena_extraida = temp_comp.getText();
                    } else {
                        Errores.agregar_error("No es un componente para poder obtener su texto", fila, columna, "Semantico");
                        return error;
                    }
                    OBJETO_RSTRING cadena_final = new OBJETO_RSTRING(cadena_extraida, new Tipo(Tipo.TipoSimbolo.componente, Tipo.TipoComponente.rstring));
                    return cadena_final;
                }
            case "getancho":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {

                    VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                    if (!(componente instanceof COMPONENTE)) {
                        Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                        return error;
                    }
                    COMPONENTE comp = (COMPONENTE) componente;
                    int tamano = 0;
                    if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                        JLabel temp_comp = (JLabel) comp.componente;
                        tamano = temp_comp.getWidth();

                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                        JTextField temp_comp = (JTextField) comp.componente;
                        tamano = temp_comp.getWidth();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                        JScrollPane temp_comp = (JScrollPane) comp.componente;
                        tamano = temp_comp.getWidth();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                        JSpinner  temp_comp = (JSpinner ) comp.componente;
                        tamano = temp_comp.getWidth();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                        JPasswordField temp_comp = (JPasswordField) comp.componente;
                        tamano = temp_comp.getWidth();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                        JButton temp_comp = (JButton) comp.componente;
                        tamano = temp_comp.getWidth();
                    } else {
                        Errores.agregar_error("No es un componente para obtener su ancho", fila, columna, "Semantico");
                        return error;
                    }
                    PRIMITIVO tamanoancho = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), tamano, fila, columna);
                    return tamanoancho;
                }
            case "getalto":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {

                    VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                    if (!(componente instanceof COMPONENTE)) {
                        Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                        return error;
                    }
                    COMPONENTE comp = (COMPONENTE) componente;
                    int tamano = 0;
                    if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                        JLabel temp_comp = (JLabel) comp.componente;
                        tamano = temp_comp.getHeight();

                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                        JTextField temp_comp = (JTextField) comp.componente;
                        tamano = temp_comp.getHeight();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                        JScrollPane temp_comp = (JScrollPane) comp.componente;
                        tamano = temp_comp.getHeight();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                        JSpinner  temp_comp = (JSpinner ) comp.componente;
                        tamano = temp_comp.getHeight();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                        JPasswordField temp_comp = (JPasswordField) comp.componente;
                        tamano = temp_comp.getHeight();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                        JButton temp_comp = (JButton) comp.componente;
                        tamano = temp_comp.getHeight();
                    } else {
                        Errores.agregar_error("No es un componente para obtener su alto", fila, columna, "Semantico");
                        return error;
                    }
                    PRIMITIVO tamanoancho = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), tamano, fila, columna);
                    return tamanoancho;

                }
            case "getpos":
                buscar_ventana = tablasimbolos.buscar(Display.nombre_ventana, fila, columna);
                if (buscar_ventana != null && Display.Constructor) {

                    VENTANA ventana = (VENTANA) buscar_ventana.Valor;
                    if (!(componente instanceof COMPONENTE)) {
                        Errores.agregar_error("No se puede acceder a la propiedad texto de un archivo que no es componente del GUI", fila, columna, "Semantico");
                        return error;
                    }
                    COMPONENTE comp = (COMPONENTE) componente;
                    int posx = 0;
                    int posy = 0;
                    if (comp.tipo.tipo_comp == Tipo.TipoComponente.et) {
                        JLabel temp_comp = (JLabel) comp.componente;
                        posx = temp_comp.getX();
                        posy = temp_comp.getY();

                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.caja) {
                        JTextField temp_comp = (JTextField) comp.componente;
                        posx = temp_comp.getX();
                        posy = temp_comp.getY();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {
                        JScrollPane temp_comp = (JScrollPane) comp.componente;
                        posx = temp_comp.getX();
                        posy = temp_comp.getY();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajanum) {
                        JSpinner  temp_comp = (JSpinner ) comp.componente;
                        posx = temp_comp.getX();
                        posy = temp_comp.getY();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.cajapass) {
                        JPasswordField temp_comp = (JPasswordField) comp.componente;
                        posx = temp_comp.getX();
                        posy = temp_comp.getY();
                    } else if (comp.tipo.tipo_comp == Tipo.TipoComponente.boton) {
                        JButton temp_comp = (JButton) comp.componente;
                        posx = temp_comp.getX();
                        posy = temp_comp.getY();
                    } else {
                        Errores.agregar_error("No es un componente para sacarle las coordenadas", fila, columna, "Semantico");
                        return error;
                    }
                    PRIMITIVO valorx = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), posx, fila, columna);
                    PRIMITIVO valory = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero), posy, fila, columna);
                    LinkedList<Expresion> valores_primitivos = new LinkedList();
                    valores_primitivos.add(valorx);
                    valores_primitivos.add(valory);
                    INSTANCIA_ARREGLO nueva = new INSTANCIA_ARREGLO(valores_primitivos, fila, columna);
                    return nueva.Ejecutar(tablasimbolos);

                }
        }
        return error;
    }

}
