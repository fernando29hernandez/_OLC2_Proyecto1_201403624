/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
* PENDIENTES
*
* ARREGLAR EL HILO DE EJECUCION DE VENTANAS  ESTO SUPUESTAMENTE YA ESTA 
* RETURN NLO <- ESTE ESTOY CASI SEGURO QUE SI YA TENGO QUE SEGUIR PROBANDO
* RETURN ARR <- ESTES FIJO SI 
* RETURN ARR[] <- ESTE NO SE
* FUNCIONES DE ESCRITURA Y LECTURA DE ARCHIVO
* PROBARLO TODO
 */
package olc2.proyecto1_201403624;

import Analizadores.Lexico_O;

import Analizadores.Lexico_R;
import Analizadores.Sintactico_O;
import Analizadores.Sintactico_R;
import Arbol.Arbol_R;
import Arbol.Entorno.Display;
import Arbol.Entorno.Entorno;
import Arbol.Entorno.Errores;
import Arbol.Entorno.Simbolo;
import Arbol.Instrucciones.General.IMPORTAR;
import Arbol_OLC.Arbol_OLC;
import Arbol_OLC.Entorno.Error_OLC;
import Arbol_OLC.Entorno.Errores_OLC;
import Arbol_OLC.Instrucciones.ARCHIVO;
import Arbol_OLC.Instrucciones.PROYECTO;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Element;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.bind.Marshaller.Listener;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;
import Analizadores.FLEXRSINTAX;

/**
 *
 * @author Fernando
 */
public class Principal_IDE extends javax.swing.JFrame {

    /**
     * Creates new form Principal_IDE
     */
    public Principal_IDE() {
        initComponents();
        setSize(1100, 650);
        setLocationRelativeTo(null);
        filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Voy a crear un filto para que solo pueda abrir archivo .coline
        //Parámetros (Descripción , extensión)
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.txt", "txt");
        //Le agrego el filtro al FileChoooser
        filechooser.setFileFilter(filtro);

        agregar_contador(caja_inicial, jScrollPane3);
        DefaultTreeModel model = (DefaultTreeModel) explorador.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();
        model.reload();
        model.setRoot(null);

    }

    public void agregar_contador(JTextArea caja_inicial, JScrollPane contenedor) {
        caja_inicial.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                JTextArea editArea = (JTextArea) ce.getSource();
                int linea = 1;
                int columna = 1;
                try {
                    int caretpos = editArea.getCaretPosition();
                    linea = editArea.getLineOfOffset(caretpos);
                    columna = caretpos - editArea.getLineStartOffset(linea);

                    // Ya que las líneas las cuenta desde la 0
                    linea += 1;
                } catch (Exception ex) {
                }

                // Actualizamos el estado
                actualizarEstado(linea, columna + 1);
            }
        });
        JTextArea lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        lines.setEditable(false);
        caja_inicial.getDocument().addDocumentListener(new DocumentListener() {
            public String getText() {
                int caretPosition = caja_inicial.getDocument().getLength();
                Element root = caja_inicial.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                    text += i + System.getProperty("line.separator");
                }
                return text;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                lines.setText(getText());
            }

            @Override
            public void insertUpdate(DocumentEvent de) {
                lines.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
        });
        contenedor.setRowHeaderView(lines);
    }

    public void agregar_contador_coloreado(RSyntaxTextArea caja_inicial, RTextScrollPane contenedor) {
        caja_inicial.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                JTextArea editArea = (JTextArea) ce.getSource();
                int linea = 1;
                int columna = 1;
                try {
                    int caretpos = editArea.getCaretPosition();
                    linea = editArea.getLineOfOffset(caretpos);
                    columna = caretpos - editArea.getLineStartOffset(linea);

                    // Ya que las líneas las cuenta desde la 0
                    linea += 1;
                } catch (Exception ex) {
                }

                // Actualizamos el estado
                actualizarEstado(linea, columna + 1);
            }
        });
        JTextArea lines = new JTextArea("1");
        lines.setBackground(Color.LIGHT_GRAY);
        lines.setEditable(false);
        caja_inicial.getDocument().addDocumentListener(new DocumentListener() {
            public String getText() {
                int caretPosition = caja_inicial.getDocument().getLength();
                Element root = caja_inicial.getDocument().getDefaultRootElement();
                String text = "1" + System.getProperty("line.separator");
                for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                    text += i + System.getProperty("line.separator");
                }
                return text;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                lines.setText(getText());
            }

            @Override
            public void insertUpdate(DocumentEvent de) {
                lines.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                lines.setText(getText());
            }
        });
        contenedor.setRowHeaderView(lines);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_Consola = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        consola = new javax.swing.JTextArea();
        pestañas = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        caja_inicial = new javax.swing.JTextArea();
        posicion_puntero = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        explorador = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel_Consola.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Consola"));

        consola.setBackground(new java.awt.Color(0, 0, 0));
        consola.setColumns(20);
        consola.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        consola.setForeground(new java.awt.Color(0, 153, 0));
        consola.setRows(5);
        jScrollPane2.setViewportView(consola);

        javax.swing.GroupLayout Panel_ConsolaLayout = new javax.swing.GroupLayout(Panel_Consola);
        Panel_Consola.setLayout(Panel_ConsolaLayout);
        Panel_ConsolaLayout.setHorizontalGroup(
            Panel_ConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_ConsolaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
                .addContainerGap())
        );
        Panel_ConsolaLayout.setVerticalGroup(
            Panel_ConsolaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_ConsolaLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addContainerGap())
        );

        caja_inicial.setColumns(20);
        caja_inicial.setRows(5);
        jScrollPane3.setViewportView(caja_inicial);

        pestañas.addTab("Nueva Pestaña", jScrollPane3);

        posicion_puntero.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        posicion_puntero.setText("Linea: 0 Columna: 0");

        explorador.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                exploradorValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(explorador);

        jMenu1.setText("Archivo");

        jMenuItem15.setText("Nuevo Proyecto");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem15);

        jMenuItem1.setText("Abrir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem13.setText("Abrir Proyecto");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem13);

        jMenuItem10.setText("Abrir Carperta");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuItem2.setText("Guardar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Guardar Como");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem16.setText("Guardar Proyecto");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editor");

        jMenuItem4.setText("Nueva Pestaña");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Cerrar Pestaña Actual");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem8.setText("Seleccion");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Ejecutar");

        jMenuItem14.setText("Configurar Archivo .R");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuItem7.setText("Ejecutar RMB(Precargado)");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem9.setText("Ejecutar Actual");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Consola");

        jMenuItem6.setText("Limpiar Consola");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuItem11.setText("Ver Reporte de Errrores");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuItem12.setText("Ver Tabla Simbolos");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(posicion_puntero)
                        .addGap(82, 82, 82))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Panel_Consola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pestañas, javax.swing.GroupLayout.PREFERRED_SIZE, 847, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pestañas, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(posicion_puntero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Panel_Consola, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
/**
     * *
     *
     * @param evt
     */
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
//        JTextArea txt = new JTextArea();
//        txt.setFont(new Font("Ubuntu", Font.PLAIN, 12));
//        JScrollPane scroll = new JScrollPane(txt);
//        agregar_contador(txt, scroll);
//        pestañas.addTab("Pestaña " + (pestañas.getComponentCount() + 1), scroll);
//        pestañas.setSelectedIndex(pestañas.getComponentCount() - 1);
        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setFont(new Font("Ubuntu", Font.PLAIN, 12));
        try {
            AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
            atmf.putMapping("text/myLanguage", "Analizadores.FLEXRSINTAX");
            textArea.setSyntaxEditingStyle("text/myLanguage");
                    textArea.setBackground(Color.decode("#292c29"));
                    textArea.setHighlightCurrentLine(false);
                    SyntaxScheme personalizado = textArea.getSyntaxScheme();
                    personalizado.getStyle(Token.DATA_TYPE).foreground = Color.cyan;
                    personalizado.getStyle(Token.RESERVED_WORD).foreground = Color.decode("#cc0073");
                    personalizado.getStyle(Token.FUNCTION).foreground =Color.decode("#a3fd44");
                    personalizado.getStyle(Token.IDENTIFIER).foreground = Color.white;
                    personalizado.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.decode("#ffff40");
                    personalizado.getStyle(Token.LITERAL_CHAR).foreground = Color.decode("#ffff40");
                    personalizado.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.decode("#826095");
                    textArea.setCodeFoldingEnabled(true);
                    
            
        } catch (Exception e) {

        }
            textArea.setCodeFoldingEnabled(true);
            RTextScrollPane sp = new RTextScrollPane(textArea);
            agregar_contador(textArea, sp);
            pestañas.addTab("Pestaña " + (pestañas.getComponentCount() + 1), sp);
            pestañas.setSelectedIndex(pestañas.getComponentCount() - 1);
        //textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);


    }//GEN-LAST:event_jMenuItem4ActionPerformed
    /**
     *
     * @param linea
     * @param columna
     */
    private void actualizarEstado(int linea, int columna) {
        posicion_puntero.setText("Linea: " + linea + " Columna: " + columna);
    }
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            JScrollPane scroll = (JScrollPane) pestañas.getSelectedComponent();
            JTextArea txt = (JTextArea) scroll.getViewport().getComponent(0);
            String ruta = txt.getName(); //Aquí tengo guardada la ruta del archivo
            File archivo = new File(ruta);
            if (archivo.exists()) {
                PrintWriter writer = new PrintWriter(archivo);
                writer.print(txt.getText());
                writer.close(); //Cierro el archivo
                actualizar_OLC();
            } else {
                JOptionPane.showMessageDialog(null, "Existió un error al momento de \"guardar\" porque el archivo no existe", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Existió un error al momento de \"guardar\" el archivo", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    /**
     * *
     * DEBO DE ARREGLAR LA CLASE PESODE
     */
    JFileChooser filechooser;

    /**
     *
     * @param evt
     */
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        int seleccion = filechooser.showSaveDialog(this);
        try {
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivo = filechooser.getSelectedFile();
                PrintWriter writer = new PrintWriter(archivo);
                JScrollPane scroll = (JScrollPane) pestañas.getSelectedComponent();
                JTextArea txt = (JTextArea) scroll.getViewport().getComponent(0);
                txt.setName(archivo.getPath()); //Actualizo la ruta del archivo
                pestañas.setTitleAt(pestañas.getSelectedIndex(), archivo.getName());//Solo jalo el nombre del archivo
                writer.print(txt.getText());
                writer.close(); //Cierro el archivo
                actualizar_OLC();
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Existió un error al momento de \"guardar como\" el archivo", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        int pos = pestañas.getSelectedIndex();
        if (pos >= 0) {
            pestañas.remove(pos);
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed
    /**
     * CUANDO ES _PESODE(chr[])
     *
     * @param evt
     */
    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        String cadenaBuscar = JOptionPane.showInputDialog("Ingrese el texto a buscar");
        String cadenaReemplazo = JOptionPane.showInputDialog("Ingrese el texto reemplazo");
        JScrollPane scroll = (JScrollPane) pestañas.getSelectedComponent();
        JTextArea txt = (JTextArea) scroll.getViewport().getComponent(0);
        txt.setText(txt.getText().replaceAll(cadenaBuscar, cadenaReemplazo));
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int seleccion = filechooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                /* Asigno el archivo que leí al variable global para tener el archivo en memoria */
                File archivo = filechooser.getSelectedFile();
                String contenido = "";
                Scanner scanner = new Scanner(archivo);
                while (scanner.hasNext()) {
                    contenido += scanner.nextLine() + "\n";
                }
                RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
                textArea.setFont(new Font("Ubuntu", Font.PLAIN, 12));
                try {
                    AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
                    atmf.putMapping("text/myLanguage", "Analizadores.FLEXRSINTAX");
                    textArea.setSyntaxEditingStyle("text/myLanguage");
                    textArea.setBackground(Color.decode("#292c29"));
                    textArea.setHighlightCurrentLine(false);
                    SyntaxScheme personalizado = textArea.getSyntaxScheme();
                    personalizado.getStyle(Token.DATA_TYPE).foreground = Color.cyan;
                    personalizado.getStyle(Token.RESERVED_WORD).foreground = Color.decode("#cc0073");
                    personalizado.getStyle(Token.FUNCTION).foreground =Color.decode("#a3fd44");
                    personalizado.getStyle(Token.IDENTIFIER).foreground = Color.white;
                    personalizado.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.decode("#ffd6af");
                    personalizado.getStyle(Token.LITERAL_CHAR).foreground = Color.decode("#ffd6af");
                    personalizado.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.decode("#826095");
                    textArea.setCodeFoldingEnabled(true);
                    
                    
                } catch (Exception e) {

                }
                RTextScrollPane sp = new RTextScrollPane(textArea);
                agregar_contador(textArea, sp);
                    
                pestañas.addTab("Pestaña " + (pestañas.getComponentCount() + 1), sp);
                    pestañas.setSelectedIndex(pestañas.getComponentCount() - 1);
                    textArea.setText(contenido);
                    textArea.setName(archivo.getPath()); //Esto es para guardarlo
                    pestañas.setTitleAt(pestañas.getSelectedIndex(), archivo.getName());//Solo jalo el nombre del archivo
//                JTextArea txt = new JTextArea();
//                txt.setFont(new Font("Ubuntu", Font.PLAIN, 12));
//                JScrollPane scroll = new JScrollPane(txt);
//                agregar_contador(txt, scroll);
//                pestañas.addTab("Pestaña " + (pestañas.getComponentCount() + 1), scroll);
//                pestañas.setSelectedIndex(pestañas.getComponentCount() - 1);
//                scroll = (JScrollPane) pestañas.getSelectedComponent();
//                txt = (JTextArea) scroll.getViewport().getComponent(0);
//                txt.setText(contenido);
//                txt.setName(archivo.getPath()); //Esto es para guardarlo
//                pestañas.setTitleAt(pestañas.getSelectedIndex(), archivo.getName());//Solo jalo el nombre del archivo
            } catch (HeadlessException | FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Existió un error al \"abrir\" el archivo seleccionado", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }

        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        consola.setText("");

    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        Errores.ListaErrores = new LinkedList();
        IMPORTAR.importados.clear();
        Display.display.clear();
        if (!PROYECTO.archivo_ejecucion.equals("")) {
            System.out.println("EL ARCHIVO PARA LA EJECUCION ES: " + PROYECTO.ruta_absoluta + PROYECTO.archivo_ejecucion);
            File archivo = new File(PROYECTO.ruta_absoluta + PROYECTO.archivo_ejecucion);
            IMPORTAR.importados.add(PROYECTO.ruta_absoluta + PROYECTO.archivo_ejecucion);
            if (archivo.exists()) {
                String contenido = "";
                Scanner scanner;
                int i = archivo.getName().lastIndexOf('.');
                Display.nombre_archivo_activo = archivo.getName().substring(0, i);
                Display.extension_archivo_activo = archivo.getName().substring(i + 1).toLowerCase();
                Display.ruta_archivo = archivo.getName();

                try {
                    scanner = new Scanner(archivo);
                    while (scanner.hasNext()) {
                        contenido += scanner.nextLine() + "\n";
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
                }

                Sintactico_R analizador;
                Lexico_R lexico;

                lexico = new Lexico_R(new StringReader(contenido));
                analizador = new Sintactico_R(lexico);
                Arbol_R arbol = null;
                try {
                    analizador.parse();
                    arbol = analizador.AST;
                    //arbol.ejecutar();
                } catch (Exception ex) {
                    Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (arbol != null) {
                    arbol.ejecutar();
                } else {
                    System.out.println("Errroes de compilacion en " + pestañas.getSelectedIndex());
                }
            } else {
                System.out.println("NO SE ENCONTRO EL ARCHIVO PARA EJECUCION");
                JOptionPane.showMessageDialog(this, "NO SE ENCONTRO EL ARCHIVO PARA EJECUCION", "AVISO", JOptionPane.ERROR_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "No se ha configurado nigun archivo para la ejecucion", "AVISO", JOptionPane.ERROR_MESSAGE);
            System.out.println("No se ha configurado nigun archivo para la ejecucion");
            ConfigurarEjecucion();
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        Errores.ListaErrores = new LinkedList();
        JScrollPane scroll = (JScrollPane) pestañas.getSelectedComponent();
        JTextArea txt = (JTextArea) scroll.getViewport().getComponent(0);
        //Display.ruta_archivo = pestañas.getTitleAt(pestañas.getSelectedIndex());
        Display.display.clear();
        Sintactico_R analizador;
        Lexico_R lexico;

        lexico = new Lexico_R(new StringReader(txt.getText()));
        analizador = new Sintactico_R(lexico);
        Arbol_R arbol = null;
        try {
            analizador.parse();
            arbol = analizador.AST;
            //arbol.ejecutar();
        } catch (Exception ex) {
            Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (arbol != null) {
            arbol.ejecutar();
        } else {
            System.out.println("Errroes de compilacion en " + pestañas.getSelectedIndex());
            ;
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        try {
            File archivo = new File("C:/Reportes/TablaErrrores.html");
            PrintWriter writer = new PrintWriter(archivo);
            writer.append("<HTML>\n");
            writer.append("<HEAD>"
                    + "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">"
                    + "</HEAD>");

            writer.append("<BODY>\n");
            writer.append("<h2>Reporte de Errrores</h2>");
            writer.append("<table class=\"table table-striped table-dark\">\n");
            writer.append("<thead>\n"
                    + "    <tr>\n"
                    + "      <th scope=\"col\">No.</th>\n"
                    + "      <th scope=\"col\">Tipo</th>\n"
                    + "      <th scope=\"col\">Descripcion</th>\n"
                    + "      <th scope=\"col\">Fila</th>\n"
                    + "      <th scope=\"col\">Columna</th>\n"
                    + "      <th scope=\"col\">Archivo</th>\n"
                    + "    </tr>\n"
                    + "  </thead>\n"
                    + " <tbody>\n");
            int i = 1;
            for (Arbol.Entorno.Error e : Errores.ListaErrores) {
                writer.append("<tr>\n"
                        + "      <th scope=\"row\">" + i + "</th>\n"
                        + "      <td>" + e.tipo + "</td>\n"
                        + "      <td>" + e.descripcion + "</td>\n"
                        + "      <td>" + e.fila + "</td>\n"
                        + "      <td>" + e.columna + "</td>\n"
                        + "      <td>" + e.ruta + "</td>\n"
                        + "    </tr>");
                i++;
            }
            for (Error_OLC e : Errores_OLC.ListaErrores) {
                writer.append("<tr>\n"
                        + "      <th scope=\"row\">" + i + "</th>\n"
                        + "      <td>" + e.tipo + "</td>\n"
                        + "      <td>" + e.descripcio + "</td>\n"
                        + "      <td>" + e.fila + "</td>\n"
                        + "      <td>" + e.columna + "</td>\n"
                        + "      <td>" + e.nombre_archivo + "</td>\n"
                        + "    </tr>");
                i++;
            }
            writer.append("  </tbody>\n" + "</table>\n");
            writer.append("</BODY>\n");
            writer.append("</HTML>\n");
            writer.close(); //Cierro el archivo

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Existió un error al momento de \"guardar como\" el archivo", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        try {
            File archivo = new File("C:/Reportes/TablaSimbolos.html");
            PrintWriter writer = new PrintWriter(archivo);
            writer.append("<HTML>\n");
            writer.append("<HEAD>"
                    + "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">"
                    + "</HEAD>");
            writer.append("<BODY>");
            writer.append("<h2>Tabla de Simbolos</h2>");
            writer.append("<table class=\"table table-striped table-dark\">\n");
            writer.append("<thead>\n"
                    + "    <tr>\n"
                    + "      <th scope=\"col\">No.</th>\n"
                    + "      <th scope=\"col\">Tipo</th>\n"
                    + "      <th scope=\"col\">Tipo Referencia</th>\n"
                    + "      <th scope=\"col\">Tipo Componente</th>\n"
                    + "      <th scope=\"col\">Identificador</th>\n"
                    + "      <th scope=\"col\">Valor</th>\n"
                    + "      <th scope=\"col\">Dimensiones</th>\n"
                    + "      <th scope=\"col\">Tipo Dato Dimensiones</th>\n"
                    + "      <th scope=\"col\">Es constante</th>\n"
                    + "    </tr>\n"
                    + "  </thead>\n"
                    + " <tbody>\n");
            int i = 1;
            int k = 0;
            for (Entorno e = Display.GLOBAL; e != null; e = e.entornoPadre) {
                LinkedList<String> ids = new LinkedList();

                for (String sim : e.tabla.keySet()) {
                    ids.add(sim);
                }
                for (Simbolo sim : e.tabla.values()) {
                    if (sim.Valor == null) {
                        writer.append("<tr>\n"
                                + "      <th scope=\"row\">" + i + "</th>\n"
                                + "      <td>" + sim.Tipo.tipo + "</td>\n"
                                + "      <td>" + sim.Tipo.tipo_Compuesto + "</td>\n"
                                + "      <td>" + sim.Tipo.tipo_comp + "</td>\n"
                                + "      <td>" + ids.get(k) + "</td>\n"
                                + "      <td>" + "Nulo" + "</td>\n"
                                + "      <td>" + sim.dimensiones + "</td>\n"
                                + "      <td>" + sim.tipo_datos.tipo + "</td>\n"
                                + "      <td>" + sim.constante + "</td>\n"
                                + "    </tr>");

                    } else {
                        writer.append("<tr>\n"
                                + "      <th scope=\"row\">" + i + "</th>\n"
                                + "      <td>" + sim.Tipo.tipo + "</td>\n"
                                + "      <td>" + sim.Tipo.tipo_Compuesto + "</td>\n"
                                + "      <td>" + sim.Tipo.tipo_comp + "</td>\n"
                                + "      <td>" + ids.get(k) + "</td>\n"
                                + "      <td>" + sim.Valor.toString() + "</td>\n"
                                + "      <td>" + sim.dimensiones + "</td>\n"
                                + "      <td>" + sim.tipo_datos.tipo + "</td>\n"
                                + "      <td>" + sim.constante + "</td>\n"
                                + "    </tr>");
                    }
                    i++;
                    k++;
                }

            }
            writer.append("  </tbody>\n" + "</table>\n");
            writer.append("</BODY>\n");
            writer.append("</HTML>\n");
            writer.close(); //Cierro el archivo

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Existió un error al momento de \"guardar como\" el archivo", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    public void cargar_OLC() {
        JFileChooser filechooser1;
        filechooser1 = new JFileChooser();
        filechooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.olc", "olc");
        filechooser1.setFileFilter(filtro);
        int seleccion = filechooser1.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                File archivo = filechooser1.getSelectedFile();
                File carpeta = filechooser1.getCurrentDirectory();
                Display.OLC_CARGADO = archivo.getName();
                Display.RUTA_OLC_CARGADO = carpeta.getAbsolutePath();
                String cambio = carpeta.getAbsolutePath();
                cambio = cambio.replace("\\", "/");
                Display.RUTA_OLC_CARGADO = cambio + "/";
                System.out.println("LA RUTA DEL OLC ES :" + Display.RUTA_OLC_CARGADO);
                String contenido = "";
                Scanner scanner = new Scanner(archivo);
                while (scanner.hasNext()) {
                    contenido += scanner.nextLine() + "\n";
                }
                Sintactico_O analizador;
                Lexico_O lexico;

                lexico = new Lexico_O(new StringReader(contenido));
                analizador = new Sintactico_O(lexico);
                Arbol_OLC arbol = null;
                try {
                    analizador.parse();
                    arbol = analizador.AST;
                    //arbol.ejecutar();
                } catch (Exception ex) {
                    Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (arbol != null) {
                    arbol.Ejecutar();
                    JOptionPane.showMessageDialog(null, "OLC cargado con exito", "Informacion", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    //System.out.println("Errroes de compilacion en " + pestañas.getSelectedIndex());
                    JOptionPane.showMessageDialog(this, "Existió un error al compilar el archivo seleccionado", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

                }
            } catch (HeadlessException | FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Existió un error al \"abrir\" el archivo seleccionado", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }

        }
    }
    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        Errores_OLC.ListaErrores.clear();
        cargar_OLC();
    }//GEN-LAST:event_jMenuItem13ActionPerformed
    /**
     * TENGO QUE LIMPIAR EL ARCHIVO JTREE Y ADEMAS TENGO QUE HACER EL CONFIGURAR
     * A MANO EL EJECUTABLE
     *
     * @param evt
     */
    private void exploradorValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_exploradorValueChanged
        DefaultMutableTreeNode nseleccionado = null; //Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
        try {
            nseleccionado = (DefaultMutableTreeNode) explorador.getLastSelectedPathComponent();
            TreeNode[] ruta = nseleccionado.getPath();
            String ruta_cadena = "";
            int i = 0;
            for (i = 0; i < ruta.length; i++) {
                TreeNode s = ruta[i];
                if (i == 0) {
                    //ruta_cadena += s.toString();

                } else if (i == 1) {
                    ruta_cadena += s.toString();
                } else {

                    ruta_cadena += "/" + s.toString();

                }
            }
            System.out.println(PROYECTO.ruta_absoluta + ruta_cadena);
            File archivo = new File(PROYECTO.ruta_absoluta + ruta_cadena);
            if (archivo.exists()) {
                String contenido = "";
                Scanner scanner = new Scanner(archivo);
                while (scanner.hasNext()) {
                    contenido += scanner.nextLine() + "\n";
                }
                RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
                textArea.setFont(new Font("Ubuntu", Font.PLAIN, 12));
                try {
                    AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory) TokenMakerFactory.getDefaultInstance();
                    atmf.putMapping("text/myLanguage", "Analizadores.FLEXRSINTAX");
                    textArea.setSyntaxEditingStyle("text/myLanguage");
                    textArea.setBackground(Color.decode("#292c29"));
                    textArea.setHighlightCurrentLine(false);
                    SyntaxScheme personalizado = textArea.getSyntaxScheme();
                    personalizado.getStyle(Token.DATA_TYPE).foreground = Color.cyan;
                    personalizado.getStyle(Token.RESERVED_WORD).foreground = Color.decode("#cc0073");
                    personalizado.getStyle(Token.FUNCTION).foreground =Color.decode("#a3fd44");
                    personalizado.getStyle(Token.IDENTIFIER).foreground = Color.white;
                    personalizado.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = Color.decode("#ffff40");
                    personalizado.getStyle(Token.LITERAL_CHAR).foreground = Color.decode("#ffff40");
                    personalizado.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = Color.decode("#826095");
                    textArea.setCodeFoldingEnabled(true);
                    
                    //agregar_contador(textArea, sp);
                    
                } catch (Exception e) {

                }
                RTextScrollPane sp = new RTextScrollPane(textArea);
                agregar_contador(textArea, sp);
                pestañas.addTab("Pestaña " + (pestañas.getComponentCount() + 1), sp);
                pestañas.setSelectedIndex(pestañas.getComponentCount() - 1);
                textArea.setText(contenido);
                textArea.setName(archivo.getPath()); //Esto es para guardarlo
                pestañas.setTitleAt(pestañas.getSelectedIndex(), archivo.getName());//Solo jalo el nombre del archivo
//                JTextArea txt = new JTextArea();
//                txt.setFont(new Font("Ubuntu", Font.PLAIN, 12));
//                JScrollPane scroll = new JScrollPane(txt);
//                agregar_contador(txt, scroll);
//                pestañas.addTab("Pestaña " + (pestañas.getComponentCount() + 1), scroll);
//                pestañas.setSelectedIndex(pestañas.getComponentCount() - 1);
//                scroll = (JScrollPane) pestañas.getSelectedComponent();
//                txt = (JTextArea) scroll.getViewport().getComponent(0);
//                txt.setText(contenido);
//                txt.setName(archivo.getPath()); //Esto es para guardarlo
//                pestañas.setTitleAt(pestañas.getSelectedIndex(), archivo.getName());//Solo jalo el nombre del archivo

            }
        } catch (Exception e) {
        }
        // visualiza el path del nodo
        //JOptionPane.showMessageDialog(this, nseleccionado.getPath());
        //System.out.println(Arrays.toString(nseleccionado.getPath()));
        explorador.clearSelection();

    }//GEN-LAST:event_exploradorValueChanged

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        ConfigurarEjecucion();
    }//GEN-LAST:event_jMenuItem14ActionPerformed
    public void actualizar_OLC_P() {
        try {
            /* Asigno el archivo que leí al variable global para tener el archivo en memoria */
            File archivo_pre = new File(PROYECTO.ruta_absoluta);
            DefaultTreeModel arbol = (DefaultTreeModel) explorador.getModel();
            DefaultMutableTreeNode nroot = new DefaultMutableTreeNode(
                    archivo_pre.getName());

            arbol.setRoot(nroot);
            System.out.println(archivo_pre.getPath());
            String cambio = archivo_pre.getAbsolutePath();
            cambio = cambio.replace("\\", "/");
            GENERAR_OLC GENERACION_ARCHIVO_OLC = new GENERAR_OLC();
            GENERACION_ARCHIVO_OLC.cadena = "{\n";
            GENERACION_ARCHIVO_OLC.cantidad_r = "\t";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "proyecto:{\n";
            GENERACION_ARCHIVO_OLC.cantidad_r += "\t";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "ruta:\"" + cambio + "/" + "\",\n";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "nombre:\"" + archivo_pre.getName() + "\",\n";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "correr:\"" + PROYECTO.archivo_ejecucion + "\",\n";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "configuracion:{\n";
            CargaEstructuraDirectorios2(arbol, nroot, archivo_pre.getPath(), GENERACION_ARCHIVO_OLC);
            GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
            GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
            GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
            System.out.println(GENERACION_ARCHIVO_OLC.cadena);
            //System.out.println("Archivo de OLC actualizado" + cambio + "/" + archivo_pre.getName() + ".olc");
            File archivo1 = new File(cambio + "/" + archivo_pre.getName() + ".olc");
            PrintWriter writer = new PrintWriter(archivo1);
            writer.print(GENERACION_ARCHIVO_OLC.cadena);
            writer.close(); //Cierro el archivo 
            JOptionPane.showMessageDialog(null, "Archivo de OLC actualizado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            Sintactico_O analizador;
            Lexico_O lexico;
            lexico = new Lexico_O(new StringReader(GENERACION_ARCHIVO_OLC.cadena));
            analizador = new Sintactico_O(lexico);
            Arbol_OLC arbol_olc = null;
            try {
                analizador.parse();
                arbol_olc = analizador.AST;
                //arbol.ejecutar();
            } catch (Exception ex) {
                Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (arbol_olc != null) {
                arbol_olc.Ejecutar();
            } else {
                //System.out.println("Errroes de compilacion en " + pestañas.getSelectedIndex());
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Existió un error al \"abrir\" el archivo seleccionado", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void actualizar_OLC() {
        try {
            /* Asigno el archivo que leí al variable global para tener el archivo en memoria */
            File archivo_pre = new File(PROYECTO.ruta_absoluta);
            DefaultTreeModel arbol = (DefaultTreeModel) explorador.getModel();
            DefaultMutableTreeNode nroot = new DefaultMutableTreeNode(
                    archivo_pre.getName());

            arbol.setRoot(nroot);
            System.out.println(archivo_pre.getPath());
            String cambio = archivo_pre.getAbsolutePath();
            cambio = cambio.replace("\\", "/");
            GENERAR_OLC GENERACION_ARCHIVO_OLC = new GENERAR_OLC();
            GENERACION_ARCHIVO_OLC.cadena = "{\n";
            GENERACION_ARCHIVO_OLC.cantidad_r = "\t";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "proyecto:{\n";
            GENERACION_ARCHIVO_OLC.cantidad_r += "\t";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "ruta:\"" + cambio + "/" + "\",\n";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "nombre:\"" + archivo_pre.getName() + "\",\n";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "correr:\"" + PROYECTO.archivo_ejecucion + "\",\n";
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "configuracion:{\n";
            CargaEstructuraDirectorios2(arbol, nroot, archivo_pre.getPath(), GENERACION_ARCHIVO_OLC);
            GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
            GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
            GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
            GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
            System.out.println(GENERACION_ARCHIVO_OLC.cadena);
            System.out.println("Archivo de OLC actualizado" + cambio + "/" + archivo_pre.getName() + ".olc");
            File archivo1 = new File(cambio + "/" + archivo_pre.getName() + ".olc");
            PrintWriter writer = new PrintWriter(archivo1);
            writer.print(GENERACION_ARCHIVO_OLC.cadena);
            writer.close(); //Cierro el archivo 
            JOptionPane.showMessageDialog(null, "Archivo guardado", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            Sintactico_O analizador;
            Lexico_O lexico;
            lexico = new Lexico_O(new StringReader(GENERACION_ARCHIVO_OLC.cadena));
            analizador = new Sintactico_O(lexico);
            Arbol_OLC arbol_olc = null;
            try {
                analizador.parse();
                arbol_olc = analizador.AST;
                //arbol.ejecutar();
            } catch (Exception ex) {
                Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (arbol_olc != null) {
                arbol_olc.Ejecutar();
            } else {
                //System.out.println("Errroes de compilacion en " + pestañas.getSelectedIndex());
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Existió un error al \"abrir\" el archivo seleccionado", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargar_Carpeta() {
        JFileChooser filechooser1;
        filechooser1 = new JFileChooser();
        filechooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int seleccion = filechooser1.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            try {
                /* Asigno el archivo que leí al variable global para tener el archivo en memoria */
                File archivo = filechooser1.getSelectedFile();
                File dir = filechooser1.getCurrentDirectory();

                DefaultTreeModel arbol = (DefaultTreeModel) explorador.getModel();
                DefaultMutableTreeNode nroot = new DefaultMutableTreeNode(
                        archivo.getName());

                arbol.setRoot(nroot);
                System.out.println(archivo.getPath());
                String cambio = archivo.getAbsolutePath();
                //Display.RUTA_OLC_CARGADO=archivo.getAbsolutePath();
                System.out.println("LA RUTA ABSOLUTA DEL OLC ES:" + Display.RUTA_OLC_CARGADO);
                cambio = cambio.replace("\\", "/");
                Display.RUTA_OLC_CARGADO = cambio + "/";
                GENERAR_OLC GENERACION_ARCHIVO_OLC = new GENERAR_OLC();
                GENERACION_ARCHIVO_OLC.cadena = "{\n";
                GENERACION_ARCHIVO_OLC.cantidad_r = "\t";
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "proyecto:{\n";
                GENERACION_ARCHIVO_OLC.cantidad_r += "\t";
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "ruta:\"" + cambio + "/" + "\",\n";
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "nombre:\"" + archivo.getName() + "\",\n";
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "correr:\"" + "\",\n";
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "configuracion:{\n";

                CargaEstructuraDirectorios2(arbol, nroot, archivo.getPath(), GENERACION_ARCHIVO_OLC);
                GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
                GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
                GENERACION_ARCHIVO_OLC.cantidad_r = GENERACION_ARCHIVO_OLC.cantidad_r.substring(1);
                GENERACION_ARCHIVO_OLC.cadena += GENERACION_ARCHIVO_OLC.cantidad_r + "}\n";
                System.out.println(GENERACION_ARCHIVO_OLC.cadena);
                System.out.println("OLC GUARDADO EN: " + cambio + "/" + archivo.getName() + ".olc");
                File archivo1 = new File(cambio + "/" + archivo.getName() + ".olc");
                Display.OLC_CARGADO = archivo.getName() + ".olc";
                PrintWriter writer = new PrintWriter(archivo1);
                writer.print(GENERACION_ARCHIVO_OLC.cadena);
                writer.close(); //Cierro el archivo
                JOptionPane.showMessageDialog(this, "Archivo OLC creado y cargado, Presione Ok para continuar", "Informacion", JOptionPane.INFORMATION_MESSAGE);
                Sintactico_O analizador;
                Lexico_O lexico;
                lexico = new Lexico_O(new StringReader(GENERACION_ARCHIVO_OLC.cadena));
                analizador = new Sintactico_O(lexico);
                Arbol_OLC arbol_olc = null;
                try {
                    analizador.parse();
                    arbol_olc = analizador.AST;
                    //arbol.ejecutar();
                } catch (Exception ex) {
                    Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (arbol_olc != null) {
                    arbol_olc.Ejecutar();
                } else {
                    //System.out.println("Errroes de compilacion en " + pestañas.getSelectedIndex());
                    JOptionPane.showMessageDialog(null, "Existió un error al cargar el OLC generado", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

                }

            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(null, "Existió un error al \"abrir\" el archivo seleccionado", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(Principal_IDE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        /**
         * CARGAR UNA CARPETA EN ESPECIFICO
         */
        Errores_OLC.ListaErrores.clear();

        JOptionPane.showMessageDialog(this, "Cargue una carpeta para crear el OLC", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        cargar_Carpeta();
        /**
         * *
         * CARGAR AHORA EL ARCHIVO OLC RECIEN GENERADO
         *
         */

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        Errores_OLC.ListaErrores.clear();

        JOptionPane.showMessageDialog(this, "Cree una nueva carpeta!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        cargar_Carpeta();
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        actualizar_OLC();
    }//GEN-LAST:event_jMenuItem16ActionPerformed
    /**
     *
     * @param arbol
     * @param padre
     * @param ruta
     * @param cadena_archivo
     */
    public void CargaEstructuraDirectorios2(DefaultTreeModel arbol,
            DefaultMutableTreeNode padre, String ruta, GENERAR_OLC cadena_archivo) {
        cadena_archivo.cantidad_r += "\t";
        DefaultMutableTreeNode aux = null;

        File archivo = new File(ruta);
        File[] archivos = archivo.listFiles();
        if (archivos != null) {
            for (int i = 0; i < archivos.length; i++) {

                aux = new DefaultMutableTreeNode(archivos[i].getName());
                arbol.insertNodeInto(aux, padre, i);
                Date date = new Date();
                DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                //System.out.println("Hora y fecha: "+hourdateFormat.format(date));
                if (archivos[i].isFile()) {
                    cadena_archivo.cadena += cadena_archivo.cantidad_r + "archivo:{\n";
                    cadena_archivo.cantidad_r += "\t";
                    cadena_archivo.cadena += cadena_archivo.cantidad_r + "nombre:\"" + archivos[i].getName() + "\",\n";
                    cadena_archivo.cadena += cadena_archivo.cantidad_r + "fecha_mod:\"" + hourdateFormat.format(date) + "\"\n";
                    cadena_archivo.cantidad_r = cadena_archivo.cantidad_r.substring(1);

                    if (i + 1 < archivos.length) {
                        cadena_archivo.cadena += cadena_archivo.cantidad_r + "},\n";

                    } else {
                        cadena_archivo.cadena += cadena_archivo.cantidad_r + "}\n";

                    }
                } else if (archivos[i].isDirectory()) {
                    try {

                        cadena_archivo.cadena += cadena_archivo.cantidad_r + "carpeta:{\n";
                        cadena_archivo.cantidad_r += "\t";
                        cadena_archivo.cadena += cadena_archivo.cantidad_r + "nombre:\"" + archivos[i].getName() + "\",\n";

                        CargaEstructuraDirectorios2(arbol, aux,
                                archivos[i].getAbsolutePath() + "/", cadena_archivo);
                        cadena_archivo.cantidad_r = cadena_archivo.cantidad_r.substring(1);

                        if (i + 1 < archivos.length) {
                            cadena_archivo.cadena += cadena_archivo.cantidad_r + "},\n";

                        } else {
                            cadena_archivo.cadena += cadena_archivo.cantidad_r + "}\n";

                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

            }

        }
    }

    public void ConfigurarEjecucion() {
        LinkedList<ARCHIVO> lista = PROYECTO.buscarEjecutable();
        String lista_s[] = new String[lista.size()];
        int i = 0;
        for (ARCHIVO t : lista) {

            lista_s[i] = t.nombre;
            i++;
        }
        String input = (String) JOptionPane.showInputDialog(null, "Elige un archivo",
                "AVISO", JOptionPane.QUESTION_MESSAGE, null, // Use

                lista_s,
                lista_s[0]);
        ARCHIVO temp = null;
        for (ARCHIVO t : lista) {
            if (t.nombre.equals(input)) {
                temp = t;
            }
        }
        String lista_rutas[] = new String[temp.rutas.size()];

        int j = 0;
        for (String t : temp.rutas) {
            lista_rutas[j] = t;
            j++;
        }
        String input2 = (String) JOptionPane.showInputDialog(null, "Elige una posible ruta",
                "AVISO", JOptionPane.QUESTION_MESSAGE, null, // Use
                lista_rutas,
                lista_rutas[0]);
        PROYECTO.archivo_ejecucion = input2;

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_Consola;
    private javax.swing.JTextArea caja_inicial;
    public static javax.swing.JTextArea consola;
    public static javax.swing.JTree explorador;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTabbedPane pestañas;
    public javax.swing.JLabel posicion_puntero;
    // End of variables declaration//GEN-END:variables
}
