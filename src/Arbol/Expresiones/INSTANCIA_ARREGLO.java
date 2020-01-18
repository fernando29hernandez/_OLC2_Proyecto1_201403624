/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Expresiones;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Lista_Tamano;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tipo;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class INSTANCIA_ARREGLO extends Expresion {

    LinkedList<Expresion> valores;
    LinkedList<Expresion> valores_ejecutados;

    LinkedList<Simbolo> valores_;

    int fila;
    int columna;

    public INSTANCIA_ARREGLO(LinkedList<Expresion> valores, int fila, int columna) {
        this.valores = valores;
        this.fila = fila;
        this.columna = columna;
        this.valores_ = new LinkedList<>();
        this.valores_ejecutados = new LinkedList<>();
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        PRIMITIVO error = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "error", fila, columna);

        if (valores != null) {
            Entorno arreglo_ent = new Entorno(tablasimbolos);
            Tipo tipo_nuevo = null;
            int primero = 0;
            int bandera = 0;
            this.valores_ejecutados = new LinkedList<>();
            for (Expresion exp : valores) {
                Expresion temp = (Expresion) exp.Ejecutar(arreglo_ent);
                valores_ejecutados.addLast(temp);
                if (primero == 0) {
                    tipo_nuevo = temp.tipo;
                    primero++;
                }
                if (primero > 0) {
                    if (tipo_nuevo.tipo == temp.tipo.tipo && tipo_nuevo.tipo_Compuesto == temp.tipo.tipo_Compuesto) {
                        bandera = 0;
                    } else {
                        bandera = 1;
                    }
                }
            }
            if (bandera == 0) {
                Expresion temp = valores_ejecutados.get(0);
                if (tipo_nuevo.tipo == Tipo.TipoSimbolo.objeto) {
                    if (tipo_nuevo.tipo_Compuesto.equals("ARREGLO")) {
                        Lista_Tamano lista = new Lista_Tamano();
                        lista.Insertar(valores_ejecutados.size());
                        DEVOLVER_SIMBOLO temporal = (DEVOLVER_SIMBOLO) temp;
                        Simbolo sim = (Simbolo) temporal.simbolo_retorno;
                        OBJETO_ARREGLO arreglo_nuevo = new OBJETO_ARREGLO(sim.tipo_datos, sim.dimensiones + 1);
                        lista.InsertarTamano(sim.tams);
                        arreglo_nuevo.lista_elementos.add(new Simbolo(sim.Tipo, sim.Identificador, sim.Valor, sim.tipo_datos, sim.dimensiones, sim.tams));
                        int tam_dimension;
                        valores_ejecutados.pop();
                        for (Expresion exp : valores_ejecutados) {
                            DEVOLVER_SIMBOLO temporal_1 = (DEVOLVER_SIMBOLO) exp;
                            Simbolo sim_1 = (Simbolo) temporal_1.simbolo_retorno;
                            arreglo_nuevo.lista_elementos.add(new Simbolo(sim_1.Tipo, sim_1.Identificador, sim_1.Valor, sim_1.tipo_datos, sim_1.dimensiones, sim_1.tams));

                        }
                        Simbolo nuevo_sim = new Simbolo(new Tipo(Tipo.TipoSimbolo.objeto, "ARREGLO"), "Anonimo", arreglo_nuevo, sim.tipo_datos, sim.dimensiones + 1, lista.cabeza);
                        DEVOLVER_SIMBOLO retorno;
                        retorno = new DEVOLVER_SIMBOLO(nuevo_sim);
                        return retorno;
                    } else {
                        Lista_Tamano lista = new Lista_Tamano();
                        lista.Insertar(valores_ejecutados.size());
                        DEVOLVER_SIMBOLO temporal = (DEVOLVER_SIMBOLO) temp;
                        Simbolo sim = (Simbolo) temporal.simbolo_retorno;
                        OBJETO_ARREGLO arreglo_nuevo = new OBJETO_ARREGLO(sim.Tipo, sim.dimensiones);
                        arreglo_nuevo.lista_elementos.add(new Simbolo(sim.Tipo, sim.Identificador, sim.Valor));
                        valores_ejecutados.pop();
                        for (Expresion exp : valores_ejecutados) {
                            DEVOLVER_SIMBOLO temporal_1 = (DEVOLVER_SIMBOLO) exp;
                            Simbolo sim_1 = (Simbolo) temporal_1.simbolo_retorno;
                            arreglo_nuevo.lista_elementos.add(new Simbolo(sim_1.Tipo, sim_1.Identificador, sim_1.Valor));

                        }
                        Simbolo nuevo_sim = new Simbolo(new Tipo(Tipo.TipoSimbolo.objeto, "ARREGLO"), "Anonimo", arreglo_nuevo, sim.tipo_datos, sim.dimensiones, lista.cabeza);
                        DEVOLVER_SIMBOLO retorno;
                        retorno = new DEVOLVER_SIMBOLO(nuevo_sim);
                        
                        return retorno;

                    }
                } else {
                    Lista_Tamano lista = new Lista_Tamano();
                     System.out.println("EL TAMANO DE LA LISTA ANTES DE ASIGNA ES :"+valores_ejecutados.size());
                    lista.Insertar(valores_ejecutados.size());
                    OBJETO_ARREGLO arreglo_nuevo = new OBJETO_ARREGLO(temp.tipo, 1);
                    arreglo_nuevo.lista_elementos.add(new Simbolo(temp.tipo, "Anonimo", temp.valor));
                    valores_ejecutados.pop();

                    for (Expresion exp : valores_ejecutados) {
                        if (exp instanceof PRIMITIVO) {
                            PRIMITIVO temporal_1 = (PRIMITIVO) exp;
                            Simbolo sim_1 = new Simbolo(temporal_1.tipo, "Anonimo", temporal_1.valor);
                            arreglo_nuevo.lista_elementos.add(new Simbolo(sim_1.Tipo, sim_1.Identificador, sim_1.Valor));

                        } else if (exp instanceof DEVOLVER_SIMBOLO){
                            DEVOLVER_SIMBOLO tempo = (DEVOLVER_SIMBOLO) exp;
                            Simbolo sim_1 = tempo.simbolo_retorno;
                            arreglo_nuevo.lista_elementos.add(new Simbolo(sim_1.Tipo, sim_1.Identificador, sim_1.Valor));
                        }

                    }

                    Simbolo nuevo_sim = new Simbolo(new Tipo(Tipo.TipoSimbolo.objeto, "ARREGLO"), "Anonimo", arreglo_nuevo, temp.tipo, 1, lista.cabeza);
                    DEVOLVER_SIMBOLO retorno;
                    retorno = new DEVOLVER_SIMBOLO(nuevo_sim);
                    System.out.println("EL TAMANO DE LA LISTA A ASIGNA ES :"+lista.cabeza.tamano_actual);
                    return retorno;

                }
            } else {
                Errores.agregar_error("Los datos en este arreglo no son del mismo tipo", fila, columna, "Semantico");
                return error;
            }
        } else {
            Errores.agregar_error("No se puede instanciar el arreglo porque esta vacio", fila, columna, "Semantico");
            return error;
        }

    }

}
