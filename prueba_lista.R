#definir gravedad 9.8
#definir arreglo {{5,2},{8,9}}
#importar "funciones.m"
ent hola=12.5; 

fusion persona {

ent edad;
chr nombre[20];
bul hola;
persona p1;

};
zro main() {
_imp("IMPRESION CON TODOS LOS TIPOS\n");
_imp("Hola mundo %e %b %d %c\n",hola,true,85.5,'Q');
lista mi_lista = _reservar(_pesode(lista));
_imp("INSERCION LISTA SIMPLE ENLAZADA\n");
_imp("Hola mundo %e\n", ins_en_lista (mi_lista, "Fernando12", 23));
_imp("Hola mundo %e\n",ins_en_lista (mi_lista, "ANtoNio123", 22));
_imp("Hola mundo %e\n",ins_en_lista (mi_lista, "Pedro12345", 24));
_imp("Hola mundo %e\n",ins_en_lista (mi_lista, "Pablo12345", 25));
_imp("Hola mundo %e\n",ins_en_lista (mi_lista, "Mario12345", 26));
_imp("Hola mundo %e\n",ins_en_lista (mi_lista, "Mauricio12", 27));
_imp("Hola mundo %e\n",ins_en_lista (mi_lista, "Daniel1234", 28));
_imp("Hola mundo %e\n",ins_en_lista (mi_lista, "Breiner123", 29));
_imp("RECORRIENDO LA LISTA\n");
nodo tmp = mi_lista.inicio;
while (tmp <> nlo){
_imp("El nombre del nodo eses: %s \n",tmp.nombre);
tmp = tmp.siguiente;		
}
_imp("FIN LISTA\n");
_imp("INSERCION ARBOL BINARIO\n");
Insertar(12,"COMPILADOR");
Insertar(5,"COMPILADOR");
Insertar(26,"COMPILADOR");
Insertar(33,"COMPILADOR");
Insertar(59,"COMPILADOR");
Insertar(27,"COMPILADOR");
Insertar(15,"COMPILADOR");
Insertar(47,"COMPILADOR");
Insertar(74,"COMPILADOR");
Insertar(84,"COMPILADOR");
Insertar(88,"COMPILADOR");
Insertar(90,"COMPILADOR");
Insertar(124,"COMPILADOR");
Insertar(612,"COMPILADOR");

_imp("InOrden\n");
InOrden(raiz);
_imp("FIN RECORRIDO\n");
}  

fusion nodo {
chr nombre[10];
ent edad;
nodo siguiente;
};


nodo nuevo_nodo(chr name[], ent yold){
	nodo nuevo = _reservar(_pesode(nodo));
	nuevo.nombre = name;
	nuevo.edad = yold;
	regresar nuevo;
}
fusion lista {
	nodo inicio;
	nodo fin;
};

ent ins_en_lista (lista lista, chr dato1[], ent dato2){

	if (lista.inicio == nlo){
		nodo nuevo = nuevo_nodo(dato1, dato2);
		lista.inicio = nuevo;
		lista.fin = nuevo;
		regresar 1;
	}
	else{
		nodo nuevo = nuevo_nodo(dato1,dato2);
		lista.fin.siguiente = nuevo;
		lista.fin = nuevo;
		regresar 2;
	}

}


ent sizeOf(lista laLista){
	
	ent size = -1; // valor 0
	if (laLista == nlo){
		_imp("tremendo error");
	}
	else{
		size = 0;
		nodo tmp = laLista.inicio;
		while (tmp <> nlo){
			size++;
			tmp = tmp.siguiente;
		}
	}
	regresar size;
}


fusion NodoB{
ent clave;
chr nombre[10];
NodoB izq;
NodoB der;
};

NodoB raiz = nlo;

bul Insertar(ent clave, chr nombre[]){
	NodoB nuevo = _reservar(_pesode(NodoB));
	nuevo.clave = clave;
	nuevo.nombre = nombre;
	if(raiz == nlo){
	           raiz = nuevo;
regresar true;
	}else{
			NodoB padre = nlo;
			NodoB actual = raiz;
			while(actual <> nlo){
				padre = actual;
				if(actual.clave > clave){
					actual = actual.izq;
				}else{
					actual = actual.der;
				}
			}
			if(padre.clave > clave){
				padre.izq = nuevo;
			}else{
				padre.der = nuevo;
			}
regresar true;
		
	}
	
}

ent preOrden(NodoB actual){
	if(actual  <> nlo){
		_imp("Clave: %e Nombre: %s\n",actual.clave,actual.nombre);
		PreOrden(actual.izq);
		PreOrden(actual.der);
	}
	regresar 0;
}

ent InOrden(NodoB actual){
	if(actual <> nlo){
		InOrden(actual.izq);
		_imp("Clave: %e Nombre: %s \n",actual.clave,actual.nombre);
		InOrden(actual.der);
	}
	regresar 0;
}

ent PosOrden(NodoB actual){
	if(actual <> nlo){
		PosOrden(actual.izq);
		PosOrden(actual.der);
	_imp("Clave: %e Nombre: %s\n",actual.clave,actual.nombre);
	}
	regresar 0;
}
