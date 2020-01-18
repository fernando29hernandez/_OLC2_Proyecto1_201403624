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
import Arbol.Entorno.Tamano;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.INSTANCIAOBJETO;
import Arbol.Expresiones.OBJETO;
import Arbol.Expresiones.OBJETO_ARREGLO;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.GUI.OBJETO_RSTRING;
import static Arbol.Instrucciones.General.DECLARACION_ARREGLO.CREAR_FALTANTES;
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class DECLARACION extends Instruccion {

    public Tipo tipo;
    public String identificador;
    public Expresion expresion;
    public int dimensiones;
    public LinkedList<Expresion> dimensionesexplicitas;
    int fila;
    int columna;

    public DECLARACION(Tipo tipo, String identificador, Expresion expresion, int fila, int columna) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.expresion = expresion;
        this.fila = fila;
        this.columna = columna;
        this.dimensiones = 0;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        if (expresion != null) {
            Expresion valor = (Expresion) expresion.Ejecutar(tablasimbolos);
            Simbolo nuevo_simbolo;
            tipo.dimensiones = this.dimensiones;
            if (tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                if (valor == null) {
                    Errores.agregar_error("No se puede declarar esta variable porque la asignacion es incorrecta", fila, columna, "Semantico");
                    return null;
                }
                if (valor instanceof DEVOLVER_SIMBOLO) {
                    DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) valor;
                    if (sim.referencia) {
                        if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                            tablasimbolos.insertar(identificador, sim.simbolo_retorno, fila, columna);
                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                            //error
                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                            //error 
                            return null;
                        }

                    }

                }
                if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                    Double valor_ = Double.valueOf(valor.valor.toString());
                    int valor__ = valor_.intValue();
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor__);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                    Double valor_ = Double.valueOf(valor.valor.toString());
                    int valor__ = valor_.intValue();
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor__);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                    int valor__ = valor.toString().charAt(0);
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor__);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                }
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                if (valor == null) {
                    Errores.agregar_error("No se puede declarar esta variable porque la asignacion es incorrecta", fila, columna, "Semantico");
                    return null;
                }
                if (valor instanceof DEVOLVER_SIMBOLO) {
                    DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) valor;
                    if (sim.referencia) {
                        if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                            tablasimbolos.insertar(identificador, sim.simbolo_retorno, fila, columna);
//error
                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                            //error 
                            return null;
                        }

                    }

                }
                if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                    Double valor_ = Double.valueOf(valor.valor.toString());
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor_);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                    Double valor_ = Double.valueOf(valor.valor.toString());
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor_);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                    int valor_ = valor.valor.toString().charAt(0);
                    Double valor__ = Double.valueOf(Integer.toString(valor_));
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor__);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                }
                //error
                return null;
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

                if (valor == null) {
                    Errores.agregar_error("No se puede declarar esta variable porque la asignacion es incorrecta", fila, columna, "Semantico");
                    return null;
                }
                if (valor instanceof DEVOLVER_SIMBOLO) {
                    DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) valor;
                    if (sim.referencia) {
                        if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {

                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                            tablasimbolos.insertar(identificador, sim.simbolo_retorno, fila, columna);
//error
                            //error 
                            return null;
                        }

                    }

                }
                if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                    char valor_ = (char) Integer.parseInt(valor.valor.toString());
                    String valor__ = String.valueOf(valor_);
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor__);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {

                    nuevo_simbolo = new Simbolo(tipo, identificador, valor.valor);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                }
                //errorr
                return null;
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                if (valor == null) {
                    Errores.agregar_error("No se puede declarar esta variable porque la asignacion es incorrecta", fila, columna, "Semantico");
                    return null;
                }
                if (valor instanceof DEVOLVER_SIMBOLO) {
                    DEVOLVER_SIMBOLO sim = (DEVOLVER_SIMBOLO) valor;
                    if (sim.referencia) {
                        if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {

                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                            //error 
                            return null;
                        } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                            tablasimbolos.insertar(identificador, sim.simbolo_retorno, fila, columna);
                            return null;
                        }

                    }

                }
                if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                    int valor_ = Integer.parseInt(valor.valor.toString());
                    Boolean valor__ = valor_ > 0;
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor__);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                    char valor_ = (char) Integer.parseInt(valor.valor.toString());
                    int valor__ = valor_;
                    Boolean valor___ = valor__ > 0;
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor___);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (valor.tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                    nuevo_simbolo = new Simbolo(tipo, identificador, valor.valor);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                }
                //error
                return null;
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.objeto)) {

                Simbolo fusion_encontrado = tablasimbolos.buscar("FUSION_" + tipo.tipo_Compuesto, fila, columna);

                if (fusion_encontrado != null) {
                    if (valor.tipo.tipo == Tipo.TipoSimbolo.objeto) {
                        if (valor.valor == null) {
                            nuevo_simbolo = new Simbolo(tipo, identificador, null);
                            tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                            return null;

                        }
                        OBJETO varResultado = (OBJETO) valor.valor;

                        if (varResultado == null) {
                            nuevo_simbolo = new Simbolo(tipo, identificador, varResultado);
                            tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                            return null;
                        }
                        if (varResultado.tipo.tipo_Compuesto.equals(tipo.tipo_Compuesto)) {
                            nuevo_simbolo = new Simbolo(tipo, identificador, varResultado);
                            tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                            return null;
                        }
                        if (valor.tipo.tipo == Tipo.TipoSimbolo.nulo) {
                            nuevo_simbolo = new Simbolo(tipo, identificador, varResultado);
                            tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                            return null;
                        }
                        return null;
                    } else if (valor.tipo.tipo == Tipo.TipoSimbolo.instancia) {
                        FUSION temporal = (FUSION) fusion_encontrado.Valor;
                        LinkedList<Instruccion> declaraciones_struct = temporal.variables;
                        INSTANCIAOBJETO ins = (INSTANCIAOBJETO) valor;
                        if (temporal.variables.size() != ins.tamano_memoria_apartada) {
                            Errores.agregar_error("NO se aparto la memoria correspondiente para instancia este objeto", fila, columna, "Semantico");
                            return null;
                        }
                        Entorno nuevo_ent = new Entorno(tablasimbolos);
                        OBJETO nuevo_objeto = new OBJETO(identificador, nuevo_ent);
                        nuevo_objeto.tipo = tipo;
                        for (int i = 0; i < declaraciones_struct.size(); i++) {
                            if (declaraciones_struct.get(i) instanceof DECLARACION) {
                                DECLARACION nueva_variable = (DECLARACION) declaraciones_struct.get(i);
                                nueva_variable.Ejecutar(nuevo_ent);
                            } else if (declaraciones_struct.get(i) instanceof DECLARACION_ARREGLO) {
                                DECLARACION_ARREGLO nueva_variable = (DECLARACION_ARREGLO) declaraciones_struct.get(i);
                                nueva_variable.Ejecutar(nuevo_ent);
                            }

                        }
                        nuevo_simbolo = new Simbolo(tipo, identificador, nuevo_objeto);
                        tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                        return null;

                    } else if (valor.tipo.tipo == Tipo.TipoSimbolo.nulo) {
                        nuevo_simbolo = new Simbolo(tipo, identificador, null);
                        tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                        return null;

                    } else if (valor.valor == null) {
                        nuevo_simbolo = new Simbolo(tipo, identificador, null);
                        tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                        return null;

                    } else if (valor == null) {
                        nuevo_simbolo = new Simbolo(tipo, identificador, null);
                        tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                        return null;

                    }
                } else {
                    Errores.agregar_error("NO existe el una fusion con el nombre especificado para uso", fila, columna, "Semantico");
                    return null;
                }

            } //&&!Display.extension_archivo_activo.equals("r")// arreglar lo de un caracter
            else if (tipo.tipo == Tipo.TipoSimbolo.componente) {
                if (tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                    Expresion exp = valor;
                    if (exp.tipo.tipo_Compuesto.equals("ARREGLO")) {

                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO) exp;
                        Simbolo sim = (Simbolo) temp.simbolo_retorno;
                        if (sim.dimensiones == 1
                                && sim.Pertenece == true
                                && sim.tipo_datos.tipo == Tipo.TipoSimbolo.caracter) {

                            sim.Pertenece = false;
                            TRANSFORMARENCADENA transform = new TRANSFORMARENCADENA(sim);
                            String valor__ = transform.transformar();
                            OBJETO_RSTRING cad = new OBJETO_RSTRING(valor__, tipo);
                            nuevo_simbolo = new Simbolo(tipo, identificador, cad);
                            tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                            return null;

                        } else {
                            Errores.agregar_error("No se puede llevar cabo de declaracion porque Rstring solo admite cadenas ", fila, columna, "Semantico");
                            return null;

                        }
                    } else if (exp.tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                        OBJETO_RSTRING valor__ = (OBJETO_RSTRING) exp;

                        String valorcadena = valor__.cadena;
                        OBJETO_RSTRING cad = new OBJETO_RSTRING(valorcadena, tipo);
                        nuevo_simbolo = new Simbolo(tipo, identificador, cad);
                        tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                        return null;

                    } else {
                        Errores.agregar_error("El valor a asignar no es una cadena", fila, columna, "Semantico");
                        return null;

                    }

                }

                return null;
            }
            return null;
        } else {
            Simbolo nuevo_simbolo;
            if (tipo.tipo.equals(Tipo.TipoSimbolo.entero)) {
                nuevo_simbolo = new Simbolo(tipo, identificador, 0);
                tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                return null;
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.doble)) {
                nuevo_simbolo = new Simbolo(tipo, identificador, 0.0);
                tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                return null;
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.caracter)) {
                nuevo_simbolo = new Simbolo(tipo, identificador, "");

                tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                return null;
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.booleano)) {
                nuevo_simbolo = new Simbolo(tipo, identificador, false);
                tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                return null;
            } else if (tipo.tipo.equals(Tipo.TipoSimbolo.objeto)) {

                Simbolo fusion_encontrado = tablasimbolos.buscar("FUSION_" + tipo.tipo_Compuesto, fila, columna);

                if (fusion_encontrado != null) {
                    nuevo_simbolo = new Simbolo(tipo, identificador, null);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else {
                    Errores.agregar_error("NO existe el una fusion con el nombre especificado para uso", fila, columna, "Semantico");
                    return null;
                }

            } else if (tipo.tipo == Tipo.TipoSimbolo.componente && !(Display.extension_archivo_activo.equals("r") && Display.extension_archivo_activo.equals("m")) || Display.Constructor) {
                if (tipo.tipo_comp == Tipo.TipoComponente.et) {
                    nuevo_simbolo = new Simbolo(tipo, identificador, null);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (tipo.tipo_comp == Tipo.TipoComponente.caja) {
                    nuevo_simbolo = new Simbolo(tipo, identificador, null);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (tipo.tipo_comp == Tipo.TipoComponente.cajaarea) {

                    nuevo_simbolo = new Simbolo(tipo, identificador, null);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;
                } else if (tipo.tipo_comp == Tipo.TipoComponente.cajanum) {

                    nuevo_simbolo = new Simbolo(tipo, identificador, null);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;

                } else if (tipo.tipo_comp == Tipo.TipoComponente.cajapass) {

                    nuevo_simbolo = new Simbolo(tipo, identificador, null);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;

                } else if (tipo.tipo_comp == Tipo.TipoComponente.boton) {

                    nuevo_simbolo = new Simbolo(tipo, identificador, null);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;

                } else if (tipo.tipo == Tipo.TipoSimbolo.componente) {
                    if (tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                        OBJETO_RSTRING obj = new OBJETO_RSTRING(" ", tipo);
                        nuevo_simbolo = new Simbolo(tipo, identificador, obj);
                        tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                        return null;

                    }
                }

            } else if (tipo.tipo == Tipo.TipoSimbolo.componente || Display.Constructor) {
                if (tipo.tipo_comp == Tipo.TipoComponente.rstring) {
                    OBJETO_RSTRING obj = new OBJETO_RSTRING(" ", tipo);
                    nuevo_simbolo = new Simbolo(tipo, identificador, obj);
                    tablasimbolos.insertar(identificador, nuevo_simbolo, fila, columna);
                    return null;

                }
            } else {
                Errores.agregar_error("Las variables de tipo" + tipo.tipo + " no se pueden declarar fuera de un archivo B", fila, columna, "Semantico");
                return null;
            }

        }
        return null;

    }

}
