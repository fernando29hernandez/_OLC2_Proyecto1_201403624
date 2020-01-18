/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol_OLC.Entorno;

/**
 *
 * @author Fernando
 */
public class Simbolo {
    public String id;
    public Object valor;
    public String ruta_absoluta;
    public Boolean recien_declarado;
    public Boolean Raiz = false;
    public Boolean nivel=false;
    public Simbolo(String id, Object valor) {
        this.id = id;
        this.valor = valor;
        this.recien_declarado=true;
        this.nivel=true;
    }
    
}
