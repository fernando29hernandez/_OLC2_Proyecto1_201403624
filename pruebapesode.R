zro main(){


_imp("el peso de es: %e",_pesode(persona));
persona p1= _reservar(2);
_imp("el peso de es: %e",_pesode(p1));
chr arr[40]="HOLA";
_imp("el peso de es: %e",_pesode(arr));
_imp("el peso de es: %e\n",1);
_imp("el peso de es: %s",arr);
}
fusion persona {
ent edad;
chr nombre[20];
};