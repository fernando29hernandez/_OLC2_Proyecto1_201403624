/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arbol.Instrucciones.General;

import Analizadores.Lexico_R;
import Analizadores.Sintactico_R;
import Arbol.Arbol_R;
import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Tipo;
import Arbol.Expresiones.DEVOLVER_SIMBOLO;
import Arbol.Expresiones.Expresion;
import Arbol.Expresiones.TRANSFORMARENCADENA;
import Arbol.Instrucciones.Instruccion;
import Arbol_OLC.Instrucciones.ARCHIVO;
import Arbol_OLC.Instrucciones.PROYECTO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import olc2.proyecto1_201403624.Principal_IDE;

/**
 *
 * @author Fernando
 */
public class IMPORTAR extends Instruccion {

    Expresion path;
    int fila;
    int columna;
    public static LinkedList<String> importados = new LinkedList();

    public IMPORTAR(Expresion path, int fila, int columna) {
        this.path = path;
        this.fila = fila;
        this.columna = columna;
    }

    public void LlenarTabla(Entorno tablasimbolos, String ruta, String ext) {
        //Voy a crear un AST, el cual crea un entorno global, ese es el que me interesa para hacer el import
        String ruta_temp = "";
        ruta_temp = Display.ruta_archivo;
        Display.ruta_archivo = ruta;
        try {
            Arbol_OLC.Entorno.Entorno temporal = PROYECTO.carpetas;
            String RUTA_RESULTADO = "";
            for (Arbol_OLC.Entorno.Simbolo sim : temporal.tabla.values()) {
                if (sim.valor instanceof ARCHIVO) {
                    ARCHIVO temp = (ARCHIVO) sim.valor;
                    for (String aux : temp.rutas) {
                        if (aux.equals(ruta)) {
                            RUTA_RESULTADO = temp.ruta_absoluta;
                        }
                    }
                }
            }
            System.out.println("LA RUTA FINAL ES :" + RUTA_RESULTADO);
            int i = 0;
            boolean b = false;
            for (i = 0; i < importados.size(); i++) {
                if (importados.get(i).equals(RUTA_RESULTADO)) {
                    Errores.agregar_error2("El archivo " + RUTA_RESULTADO + " ya se importo por lo que no puede ser llamado otra vez", fila, columna, "Semantico",ruta_temp);
                    b = true;
                }

            }
            importados.add(RUTA_RESULTADO);
            if (b == false) {
                FileInputStream nuevo = new FileInputStream(RUTA_RESULTADO);

                BufferedReader leer = new BufferedReader(new InputStreamReader(nuevo));
                String linea = "";
                String contenido = "";
                while ((linea = leer.readLine()) != null) {
                    contenido += linea + "\n";
                }
                leer.close();
                contenido = contenido.replace("“", "\"");
                contenido = contenido.replace("”", "\"");
                contenido = contenido.concat("\n");//Esto lo agrego por si viene un comentario al final reconozca el token
                StringReader miReader = new StringReader(contenido);
                Sintactico_R analizador;
                Lexico_R lexico;
                lexico = new Lexico_R(miReader);
                analizador = new Sintactico_R(lexico);
                Arbol_R arbol = null;
                try {
                    analizador.parse();
                    arbol = analizador.AST;
                } catch (Exception ex) {
                    System.out.println("ESTOY AQUI");
                    Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
                }
                /*
             * ESTE IF YA LO HAGO POR GUSTO PORQUE TODO ES UN SOLO ENTORNO GLOBAL
             * PERO LO DEJO PORQUE YA NO QUIERO TOCAR NADA
                 */
                if (arbol != null) {
                    Entorno entorno_import = new Entorno(null);
                    for (Entorno e = Display.GLOBAL; e != null; e = e.entornoPadre) {
                        if (e.entornoPadre == null) {
                            e.entornoPadre = entorno_import;
                            break;
                        }
                    }
                    arbol.ejecutar_import(entorno_import);
                } else {
                    Errores.agregar_error("El archivo tiene muchos errores en compilacion", fila, columna, "Semantico");
                }

            }

            Display.ruta_archivo = ruta_temp;
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Error fatal en compilación de entrada. import '" + ruta + "';");
            Display.ruta_archivo = ruta_temp;
        }
        Display.ruta_archivo = ruta_temp;
    }

    @Override
    public Object Ejecutar(Entorno tablasimbolos) {
        Expresion ruta = (Expresion) path.Ejecutar(tablasimbolos);

        if (ruta.tipo.tipo != Tipo.TipoSimbolo.objeto && !(ruta.tipo.tipo_Compuesto.equals("ARREGLO"))) {
            Errores.agregar_error("La ruta no es una cadena de caracteres", fila, columna, "Semantico");
            return null;
        }
        DEVOLVER_SIMBOLO dev = (DEVOLVER_SIMBOLO) ruta;
        if (dev.simbolo_retorno.tipo_datos.tipo != Tipo.TipoSimbolo.caracter) {
            Errores.agregar_error("La ruta no es una cadena de caracteres", fila, columna, "Semantico");
            return null;
        }

        TRANSFORMARENCADENA literal = new TRANSFORMARENCADENA(dev.simbolo_retorno);
        String cadena = literal.transformar();
        System.out.println("EL IMPORT ES " + cadena);
        if (cadena.startsWith("/")) {
            cadena = cadena.replaceFirst("/", "");
        }
        Arbol_OLC.Entorno.Entorno temporal = PROYECTO.carpetas;
        String RUTA_RESULTADO = "";
        for (Arbol_OLC.Entorno.Simbolo sim : temporal.tabla.values()) {
            if (sim.valor instanceof ARCHIVO) {
                ARCHIVO temp = (ARCHIVO) sim.valor;
                for (String aux : temp.rutas) {
                    if (aux.equals(cadena)) {
                        RUTA_RESULTADO = temp.ruta_absoluta;
                    }
                }
            }
        }
        File archivo = new File(RUTA_RESULTADO);
        if (archivo.exists()) {
            String extension;

            int i = archivo.getName().lastIndexOf('.');
            if (i > 0) {
                extension = archivo.getName().substring(i + 1);
                String temporal_archivo = Display.nombre_archivo_activo;
                String temporal_extension = Display.extension_archivo_activo;
                Display.nombre_archivo_activo = archivo.getName().substring(0, i);
                Display.extension_archivo_activo = archivo.getName().substring(i + 1).toLowerCase();
                System.out.println("Importar '" + cadena + "'");
                if (temporal_extension.toLowerCase().equals("r")) {
                    if (Display.extension_archivo_activo.toLowerCase().equals("r")) {

                        Errores.agregar_error("Un archivo .R no puede importar otros R", fila, columna, "Semantico");
                        return null;
                    }
                } else if (temporal_extension.toLowerCase().equals("m")) {
                    if (Display.extension_archivo_activo.toLowerCase().equals("r")) {

                        Errores.agregar_error("Un archivo .M no puede importar otros R", fila, columna, "Semantico");
                        return null;
                    } else if (Display.extension_archivo_activo.toLowerCase().equals("b")) {
                        Errores.agregar_error("Un archivo .M no puede importar otros R", fila, columna, "Semantico");
                        return null;

                    }
                } else if (temporal_extension.toLowerCase().equals("b")) {
                    if (Display.extension_archivo_activo.toLowerCase().equals("b")) {

                        Errores.agregar_error("Un archivo .B no puede importar otros B", fila, columna, "Semantico");
                        return null;
                    } else if (Display.extension_archivo_activo.toLowerCase().equals("r")) {
                        Errores.agregar_error("Un archivo .B no puede importar otros R", fila, columna, "Semantico");
                        return null;

                    }
                }
                switch (extension.toLowerCase()) {
                    case "r":
                        LlenarTabla(tablasimbolos, cadena, "r");
                        return null;
                    case "m":
                        LlenarTabla(tablasimbolos, cadena, "m");
                        Display.nombre_archivo_activo = temporal_archivo;
                        Display.extension_archivo_activo = temporal_extension;

                        return null;
                    case "b":
                        LlenarTabla(tablasimbolos, cadena, "b");
                        Display.nombre_archivo_activo = temporal_archivo;
                        Display.extension_archivo_activo = temporal_extension;

                        return null;

                    default:
                        Errores.agregar_error("La extension de la ruta no es compatible con ninguno de las esperadas", fila, columna, "Semantico");
                        return null;
                }
            } else {
                Errores.agregar_error("La extension de la ruta no es compatible con ninguno de las esperadas", fila, columna, "Semantico");
                return null;
            }
        } else {
            Errores.agregar_error("El archivo "+RUTA_RESULTADO+" no existe", fila, columna, "Semantico");
            return null;
        }
    }
}
