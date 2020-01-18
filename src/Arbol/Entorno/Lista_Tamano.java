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
public class Lista_Tamano {
    public Tamano cabeza;

    public Lista_Tamano() {
        this.cabeza = null;
    }
    public void Insertar(int tam)
    {
        if(cabeza==null)
        {
          cabeza = new Tamano(tam);
        }
        else
        {
            Tamano aux=cabeza;
            while(aux.Siguiente!=null)
            {
                aux=aux.Siguiente;
            }
            aux.Siguiente = new Tamano(tam);
        }
    }
    public void InsertarTamano(Tamano tam)
    {
        Tamano aux=cabeza;
        while(aux.Siguiente!=null)
        {
            aux=aux.Siguiente;
        }
        aux.Siguiente = tam;
        
    }
    public void eliminar_primero()
    {
        cabeza=cabeza.Siguiente;
    }
    
    public void copiar_valores(Lista_Tamano copia)
    {
        if(cabeza==null)
        {
        }
        else
        {
            Tamano aux = cabeza;
            while(aux!=null)
            {
                copia.Insertar(aux.tamano_actual);
                aux=aux.Siguiente;
            }
        }
    }
    public static int comparar_listas(Tamano n1,Tamano n2)
    {
        Tamano aux = n1;
        Tamano aux2 = n2;
        
        while(aux!=null)
        {
            if(aux.tamano_actual!=aux2.tamano_actual)
            {
                return 0;
            
            }
            aux=aux.Siguiente;
            aux=aux2.Siguiente;
        }
        return 1;
        
    }
}
