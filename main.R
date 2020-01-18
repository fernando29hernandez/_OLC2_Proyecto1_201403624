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

_imp("Hola mundo %e %b %d %c\n",hola,true,85.5,'Q');

persona persona1 = _reservar(_pesode(persona));
persona1.p1 = _reservar(_pesode(persona));
persona1.edad=12;
persona1.nombre="FERNANDO";
persona1.p1.edad = 38.5;
_imp("El la edad es: %e\n",persona1.edad);
_imp("El la edad es: %s\n",persona1.nombre);

_imp("El la edad es: %e \n",persona1.p1.edad);
_imp("Mi peso es de : %d \n",15*9.8);
ent h = 5;
_imp("El factorial de 5 es %e",factorial(5));
_imp("%e \n",h);
for(ent x=0;x<8;x++)
{
_imp("%c",persona1.nombre[x]);
}
_imp("%e %e\n",arreglo[0][0]^arreglo[0][1],arreglo[1][0]*arreglo[1][1]);
ent copia2[][] ={{2,2,2,2},{3,3,3,3}};
ent copia3[][] = valores(1,2,copia2);
_imp("%e %e %e %e \n",copia3[0][0],copia3[0][1],copia3[0][2],copia3[0][3]);
Calculos();
}  

ent prueba (ent n)
{
n=n*2;
regresar n;
}
ent factorial(ent n)
{
if (n==1){
regresar 1;
}else{
regresar n * factorial(n-1);
}

}
ent [][] valores(ent uno,ent dos,ent valores[][])
{
ent sust[]={1,2,4,6};
valores[0]=sust;
regresar valores;
}
//probar el import y hacer las funciones nativas
