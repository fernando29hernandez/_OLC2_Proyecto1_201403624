
ent potencia(ent base, ent exp) {
    if(exp==0)
    {
	regresar 1;
    }else
    {
         regresar base * potencia(base, exp - 1);
    }
}

ent fibonaci(ent n){
    if(n==1 || n==2)
{
regresar 1;
} 
regresar fibonaci(n-1) + fibonaci(n-2);
} 	

ent ackermann(ent m, ent n) {
    if (m == 0) {
        regresar (n + 1);
    } else if (m > 0 && n == 0) {
        regresar ackermann(m - 1, 1);
    } else {
        regresar ackermann(m - 1, ackermann(m, n - 1));
    }
}

zro Hanoi(ent discos, chr origen[], chr aux[], chr destino[]) {
    if (discos == 1) {
        _imp("mover disco de %s %s %s\n", origen , " a " , destino);
    } else {
        Hanoi(discos - 1, origen, destino, aux);
        _imp("mover disco de %s %s %s\n", origen , " a " , destino);
        Hanoi(discos - 1, aux, origen, destino);
    }
}

ent par(ent nump) {
    if (nump == 0) {
        regresar 1;
    }
    regresar impar(nump - 1);
}

ent impar(ent numi) {
    if (numi == 0) {
        regresar 0;
    }
    regresar par(numi - 1);
}



zro Calculos(){
    _imp("1) Potencia con operador ternario \n");
    _imp("%e\n",potencia(5,5));
    _imp("3) Factorial\n");
    _imp("%e\n",factorial(7));
    _imp("4) Fibonacci\n");
    _imp("%e\n",fibonaci(19));
    _imp("5) Hanoi\n");
    Hanoi(3,"1","2","3");
    _imp("7) Paridad Par e Impar \n");
    _imp("%e\n",par(26));
    _imp("%e\n",impar(27));
    _imp("8) Ackermann 3,4:\n");
    _imp("%e",ackermann(3,4));
}
