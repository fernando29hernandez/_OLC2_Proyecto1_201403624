/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Entorno;

/**
 *
 * @author Fernando
 */
public class Tamano {
    public int tamano_actual;
    public Tamano Siguiente;

    public Tamano(int tamano_actual) {
        this.tamano_actual = tamano_actual;
        this.Siguiente = null;
    }
    public static int comparar_listas(Tamano n1,Tamano n2,Boolean pertenece)
    {
        Tamano aux = n1;
        Tamano aux2 = n2;
        int entrada = 0;
        while(entrada==0)
        {
            /**
             * Antes lo tenia con != preguntare porque si tiene que ser menor o igual sino que chiste
             * 
             */
            if(aux.tamano_actual!=aux2.tamano_actual)
            {
                if(pertenece&&aux.tamano_actual<aux2.tamano_actual)
                {
                    Errores.agregar_error("Los arreglos no son del mismo tamano", 0, 0, "Semantico");
                    return 0;
                }
                else if(pertenece==false&&aux.tamano_actual<aux2.tamano_actual)
                {
                    Errores.agregar_error("Los arreglos no son del mismo tamano", 0, 0, "Semantico");
                
                    return 0;
                }
                else if(pertenece==false&&aux.tamano_actual>aux2.tamano_actual)
                {
                    Errores.agregar_error("Los arreglos no son del mismo tamano", 0, 0, "Semantico");
                
                    return 0;
                }
                else if(pertenece==false)
                {
                        Errores.agregar_error("Los arreglos no son del mismo tamano", 0, 0, "Semantico");
                
                    return 0;
                }
                
            }
            else if(aux.tamano_actual!=aux2.tamano_actual&&pertenece)
            {
                if(aux.tamano_actual<aux2.tamano_actual)
                {
                    Errores.agregar_error("Los arreglos no son del mismo tamano", 0, 0, "Semantico");
                    return 0;
                }
                
            }
            if(aux.Siguiente==null||aux2.Siguiente==null)
            {
                entrada=1;
            }
            aux=aux.Siguiente;
            aux2=aux2.Siguiente;
        }
//        if(aux.tamano_actual!=aux2.tamano_actual&&pertenece)
//        {
//            if(aux.tamano_actual<aux2.tamano_actual)
//            {
//                //Errores.agregar_error("Los arreglos no son del mismo tamano", 0, 0, "Semantico");
//                return 0;
//            }
//
//        }
        return 1;
        
    }
}
