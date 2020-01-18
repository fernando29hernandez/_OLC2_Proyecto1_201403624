
#importar "ventana_principal.b"

zro main()
{
chr nombre[25]="COMPILADORES 2";
	chr copia[20]="COMPILADORES 2";
	chr fecha[]=" DIC 2019";
	_conc(nombre,fecha);
	_imp("El valor  convertido es: %e\n",_aent("25"));
	_imp("El valor  convertido es: %d\n",_adec("550.55"));
	_imp("El valor  convertido es: %s\n",_atxt(9*6-5*2.5));
	_imp("El valor  convertido es: %s\n",nombre);
	_imp("El valor  convertido1 es: %b\n",_eqls(nombre,copia));
	_imp("El valor  convertido2 es: %b\n",_eqls(fecha,copia));

	_abrir_ventana("ventana_principal");

	_imp("DESPUES DE CERRAR\n");
	_abrir_ventana("ventana_principal");
                             _imp("El valor  convertido es: %e\n",_aent("25"));
	_imp("El valor  convertido es: %d\n",_adec("550.55"));
	_imp("El valor  convertido es: %s\n",_atxt(9*6-5*2.5));
_imp("El valor es %e\n",hola());
}
ent hola()

{

regresar 1;
}
