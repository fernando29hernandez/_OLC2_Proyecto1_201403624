

Rlbl nombre;
Rtxt caja;
RtxtA cajagrande;
RtxtP cajapass;
RtxtN cajanumero;
Rbton boton;
Rbton boton2;
Rstring cadena= "ESTO ES UNA CADENA DE TEXTO QUE APARECERA EN PANTALLA";
ent tamanox=200;
ent tamanoy=50;
ent posx=10;
ent posy=10;
zro mi_ventana:iniciar_ventana()
{
_Nuevo_GUI(nombre);
_Nuevo_GUI(caja);
_Nuevo_GUI(cajagrande);
_Nuevo_GUI(cajapass);
_Nuevo_GUI(cajanumero);
_Nuevo_GUI(boton);
_Nuevo_GUI(boton2);

nombre.Settexto("FERNANDO ANTONIO");
caja.Settexto("COMPILADORES 2");
cajagrande.Settexto("ESTO ES UN TEXT AREA");
cajapass.Settexto("PRUEBA");
cajanumero.Settexto("125");
boton.Settexto("PRESS");
nombre.Setancho(tamanox);
nombre.Setalto(tamanoy);
nombre.Setpos	(posx,posy);
caja.Setancho(tamanox);
caja.Setalto(tamanoy);
caja.Setpos(posx,posy+60);
cajagrande.Setancho(tamanox);
cajagrande.Setalto(tamanoy);
cajagrande.Setpos(posx,posy+120);
cajapass.Setancho(tamanox);
cajapass.Setalto(tamanoy);
cajapass.Setpos(posx,posy+180);
cajanumero.Setancho(tamanox);
cajanumero.Setalto(tamanoy);
cajanumero.Setpos(posx,posy+250);
boton.Setancho(tamanox);
boton.Setalto(tamanoy);
boton.Setpos(posx,posy+320);
boton2.Setancho(tamanox);
boton2.Setalto(tamanoy);
boton2.Setpos(posx,posy+400);
boton2.Settexto("BOTON 2 ");
_alto_y_ancho(900,500);

}
zro boton:al_dar_click()
{
caja.Settexto(cadena);
_imp("ESTO ES UNA IMPRESION\n");
_imp("El valor es %e\n",hola());
boton.Setancho(tamanox+50);
Rmensage("BITE ZA DUSTO");
//_read("miarchivo.txt",cadena);
//_close();
}
zro boton2:al_dar_click()
{
caja.Settexto("VACIONES 2019");
_imp("USO DE ATXT %s \n",_atxt(cadena));
_imp("El valor es %e\n",hola()+5);
boton.Setancho(tamanox-50);
Rstring textocaja = cajagrande.Gettexto();
ent getaltura = cajagrande.Getalto();
ent getancho = cajagrande.Getancho();
ent getposiciones[]=cajagrande.Getpos();
Rmensage("La altura es de %e , el ancho es de %e y las posicion en x,y son  %e, %e",getaltura,getancho,getposiciones[0],getposiciones[1]);
Rmensage("%s",_atxt(textocaja));
}
