/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Lista_Tamano;
import Arbol.Entorno.Simbolo;
import Arbol.Entorno.Tamano;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.OBJETO_ARREGLO;
import Arbol.Expresiones.PRIMITIVO;
import Arbol.Instrucciones.Instruccion;
import java.util.LinkedList;

/**
 *
 * @author Fernando
 */
public class DECLARACION_ARREGLO extends Instruccion {
    public Tipo tipo;
    public String identificador;
    public Expresion expresion;
    public int dimensiones;
    public LinkedList<Expresion> dimensionesexplicitas;
    int fila;
    int columna;
    /**
     * VERIFICACION DE TAMNOS DE DIMENSIONES DE MENOR O IGUAL 
     * 
     */
    /**
     * 
     * @param tipo
     * @param identificador
     * @param expresion
     * @param dimensiones
     * @param fila
     * @param columna
     * @param arreglo 
     */
    public DECLARACION_ARREGLO(Tipo tipo, String identificador, Expresion expresion, int dimensiones, int fila, int columna,boolean arreglo) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.expresion = expresion;
        this.dimensiones = dimensiones;
        this.fila = fila;
        this.columna = columna;
        this.dimensionesexplicitas=null;
    }
    
    /**
     * 
     * 
     * @param tipo
     * @param identificador
     * @param expresion
     * @param dimensionesexplicitas
     * @param fila
     * @param columna 
     */
    public DECLARACION_ARREGLO(Tipo tipo, String identificador, Expresion expresion, LinkedList<Expresion> dimensionesexplicitas, int fila, int columna) {
        this.tipo = tipo;
        this.identificador = identificador;
        this.expresion = expresion;
        this.dimensiones=dimensionesexplicitas.size();
        this.dimensionesexplicitas = dimensionesexplicitas;
        this.fila = fila;
        this.columna = columna;
    }
    /**
     * 
     * @param tablasimbolos
     * @param cantidadDimensiones
     * @param dimensionActual
     * @param tamañoDimensiones
     * @param arregloPadre
     * @param tams 
     */
    public static void  CREAR_FALTANTES(OBJETO_ARREGLO arregloPadre,int tams) {
            for (int i = 0; i < tams; i++) {
                    PRIMITIVO temporal = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.caracter),"",0,0);
                    arregloPadre.lista_elementos.add(new Simbolo(temporal.tipo,"Sin Nombre InicializarArreglo", temporal.valor));
                
            }
    }
    private void CREAR_ARBOL(Entorno tablasimbolos, int cantidadDimensiones, int dimensionActual, LinkedList<Integer> tamañoDimensiones, OBJETO_ARREGLO arregloPadre,Lista_Tamano tams) {
        if (dimensionActual == tamañoDimensiones.size()) { //Ya llegué a la declaración de los nodos hoja
            Expresion temporal;
            for (int i = 0; i < tamañoDimensiones.get(dimensionActual - 1); i++) {
                switch (tipo.tipo) {
                    case entero:
                        temporal = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.entero),0,0,0);
                        arregloPadre.lista_elementos.add(new Simbolo(temporal.tipo,"Sin Nombre InicializarArreglo", temporal.valor));
                        break;
                    case doble:
                        temporal = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.doble),0.0,0,0);
                        arregloPadre.lista_elementos.add(new Simbolo(temporal.tipo,"Sin Nombre InicializarArreglo", temporal.valor));
                        break;
                    case caracter:
                        temporal = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.caracter),"",0,0);
                        arregloPadre.lista_elementos.add(new Simbolo(temporal.tipo,"Sin Nombre InicializarArreglo", temporal.valor));
                        break;
                    case booleano:
                        temporal = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.booleano),false,0,0);
                        break;
                    case objeto: //Si es un arreglo de objetos
                        Simbolo fusion_encontrado = tablasimbolos.buscar("FUSION_" + tipo.tipo_Compuesto, fila, columna);
                        if (fusion_encontrado != null) { //Si la clase existe si puedo crear el arreglo con estos tipos de datos
                            //Meto objetos con valores nulos
                            arregloPadre.lista_elementos.add(new Simbolo(tipo, "Objeto Sin Nombre InicializarArreglo", null));
                        } else { //Si la clase NO existe
                            //errpr
                            temporal = new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "@Error@",0,0);
                            arregloPadre.lista_elementos.add(new Simbolo(temporal.tipo, "Error Sin Nombre InicializarArreglo", temporal.valor));
                        }
                        break;
                }
            }
            return; 
        }
        Lista_Tamano lista = new Lista_Tamano();
        tams.copiar_valores(lista);
        lista.eliminar_primero();
        for (int i = 0; i < tamañoDimensiones.get(dimensionActual - 1); i++) {
            OBJETO_ARREGLO nuevoarreglo = new OBJETO_ARREGLO(tipo,cantidadDimensiones);
            CREAR_ARBOL(tablasimbolos, cantidadDimensiones - 1, dimensionActual + 1, tamañoDimensiones, nuevoarreglo,lista);
            arregloPadre.lista_elementos.add(new Simbolo(new Tipo(Tipo.TipoSimbolo.objeto,"ARREGLO"), "Sin Nombre InicializarArreglo",nuevoarreglo,tipo,cantidadDimensiones,lista.cabeza));
        }
    }
    /**
     * 
     * @param tablasimbolos
     * @return 
     */
    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        //tipo.dimensiones=this.dimensiones;
        if (expresion != null) { 
            if(dimensionesexplicitas!=null)
            {
                LinkedList<Integer> tamaño_dimensiones = new LinkedList<>();
                
                Lista_Tamano lista = new Lista_Tamano();
                
                for (Expresion expresion : dimensionesexplicitas) {
                    int valorentero=0;
                    Expresion resultado = (Expresion)expresion.Ejecutar(tablasimbolos);
                    if (resultado.tipo.tipo != Tipo.TipoSimbolo.entero) { 
                        Errores.agregar_error("NO se permite declarar un arreglo en donde la dimension no sea un entero", fila, columna,"Semantico");
                        return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "@Error@",0,0);
                    }
                    valorentero = Integer.parseInt(resultado.valor.toString());
                    if (valorentero < 0) {
                        Errores.agregar_error("NO se permite declarar un arreglo  en donde el valor de la dimension sea un valor negativo", fila, columna,"Semantico");
                        return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "@Error@",0,0); 
                    }
                    tamaño_dimensiones.add(valorentero);
                    lista.Insertar(Integer.parseInt(resultado.valor.toString()));
                }
                Expresion exp = (Expresion)expresion.Ejecutar(tablasimbolos);
                if(exp.tipo.tipo_Compuesto.equals("ARREGLO"))
                    {
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO)exp;
                        Simbolo sim = (Simbolo)temp.simbolo_retorno;
                        System.out.println("las dimensiones de los dos son "+this.dimensiones+" "+sim.dimensiones);
                            Tamano aux = lista.cabeza;
                            while(aux!=null)
                            {
                                System.out.println("1 el tamano de la dimension es: "+aux.tamano_actual);
                                aux = aux.Siguiente;
                            }
                            aux = sim.tams;
                            while(aux!=null)
                            {
                                System.out.println("2 el tamano de la dimension es: "+aux.tamano_actual);
                                aux = aux.Siguiente;
                            }
                        if(sim.dimensiones==this.dimensiones
                                &&Tamano.comparar_listas(lista.cabeza,sim.tams,sim.Pertenece)==1
                                &&sim.tipo_datos.tipo==tipo.tipo)
                        {
                            
                            OBJETO_ARREGLO arreglo_nuevo = (OBJETO_ARREGLO)sim.Valor;
                            Simbolo simbolo = new Simbolo(sim.Tipo, identificador,arreglo_nuevo,tipo,this.dimensiones,lista.cabeza);
                            simbolo.Pertenece=false;
                            sim.Pertenece=false;
                            tablasimbolos.insertar(identificador, simbolo, fila, columna);
                            
//                            if(this.dimensiones>1){
//                            System.out.println("los tamanos son de izq "+lista.cabeza.tamano_actual+" "+lista.cabeza.Siguiente.tamano_actual);
//                            System.out.println("los tamanos son de izq "+sim.tams.tamano_actual+" "+sim.tams.Siguiente.tamano_actual);
//                            }
                            if(sim.dimensiones==1&&sim.tipo_datos.tipo==Tipo.TipoSimbolo.caracter)
                            {
                                // creo los faltantes
                                if(sim.tams.tamano_actual<lista.cabeza.tamano_actual)
                                {
                                    CREAR_FALTANTES(arreglo_nuevo,lista.cabeza.tamano_actual-sim.tams.tamano_actual);
                                }
                            }
                            return null;
                        }
                        else
                        {
                            Errores.agregar_error("No se puede llevar cabo de declaracion porque los tipos de arreglos son diferentes", fila, columna,"Semantico");
                            return null;
                        
                        }
                    }
                    else
                    {
                        Errores.agregar_error("El valor a asignar no es un arreglo", fila, columna,"Semantico");
                        return null;
                    
                    }
                
            }
            else
            {
                Expresion exp = (Expresion)expresion.Ejecutar(tablasimbolos);
                if(exp.tipo.tipo==Tipo.TipoSimbolo.objeto)
                {
                    if(exp.tipo.tipo_Compuesto.equals("ARREGLO"))
                    {
                        DEVOLVER_SIMBOLO temp = (DEVOLVER_SIMBOLO)exp;
                        Simbolo sim = (Simbolo)temp.simbolo_retorno;
                        if(sim.dimensiones==this.dimensiones)
                        {
                            OBJETO_ARREGLO arreglo_nuevo = (OBJETO_ARREGLO)sim.Valor;
                            Simbolo simbolo = new Simbolo(new Tipo(Tipo.TipoSimbolo.objeto,"ARREGLO"), identificador,arreglo_nuevo,tipo,this.dimensiones,sim.tams);
                            tablasimbolos.insertar(identificador, simbolo, fila, columna);
                        }
                        else
                        {
                            Errores.agregar_error("Las dimensiones no son igual entre la declaracion y la asignacion del arreglo", fila, columna,"Semantico");
                            return null;
                        }
                    }
                    else
                    {
                        Errores.agregar_error("El valor a asignar no es un arreglo", fila, columna,"Semantico");
                        return null;
                    }
                    
                }
                else
                {
                    Errores.agregar_error("El valor a asignar no es un arreglo", fila, columna,"Semantico");
                    return null;
                    
                }
            }
        } else { 
            
            if(dimensionesexplicitas!=null)
            {
                LinkedList<Integer> tamaño_dimensiones = new LinkedList<>();
                
                Lista_Tamano lista = new Lista_Tamano();
                for (Expresion expresion : dimensionesexplicitas) {
                int valorentero;
                    Expresion resultado = (Expresion)expresion.Ejecutar(tablasimbolos);
                    if (resultado.tipo.tipo != Tipo.TipoSimbolo.entero) { 
                        Errores.agregar_error("NO se permite declarar un arreglo en donde la dimension no sea un entero", fila, columna,"Semantico");
                        return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "@Error@",0,0);
                    }
                    valorentero = Integer.parseInt(resultado.valor.toString());
                    if (valorentero < 0) {
                        Errores.agregar_error("NO se permite declarar un arreglo  en donde el valor de la dimension sea un valor negativo", fila, columna,"Semantico");
                        return new PRIMITIVO(new Tipo(Tipo.TipoSimbolo.error), "@Error@",0,0); 
                    }
                    tamaño_dimensiones.add(valorentero);
                    lista.Insertar(Integer.parseInt(resultado.valor.toString()));
                }
                
                
                OBJETO_ARREGLO arreglo_nuevo = new OBJETO_ARREGLO(tipo,tamaño_dimensiones.size());
                CREAR_ARBOL(tablasimbolos, tamaño_dimensiones.size() - 1, 1, tamaño_dimensiones, arreglo_nuevo,lista);
                //aqui le cambie para que en vez de tomar el tamano del simbolo tome el que supuestamente tiene
                //lista.cabeza
                Simbolo sim = new Simbolo(new Tipo(Tipo.TipoSimbolo.objeto,"ARREGLO"), identificador, arreglo_nuevo,tipo,tamaño_dimensiones.size(),lista.cabeza);
                tablasimbolos.insertar(identificador, sim, fila, columna);
            }
            else
            {
                Errores.agregar_error("NO se permite declarar un arreglo sin valor o sin dimensiones de manera explicita", fila, columna,"Semantico");
                return null;
            }
        }
        return null;
    }
    
}
