/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefile.vista;

import com.jgc.clasefile.controlador.ControladorArchivo;
import com.jgc.clasefile.controlador.ControladorCarpeta;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 21 sept 2024
 */
public class VentanaGrafica extends JFrame implements InterfazVista {
   // elementos interfaz grafica | JFrame =>
    // elementos boton | boton por metodo ==>
  private final JButton crearCarpetaRutaCompleta;
  private final JButton crearCarpetaRutaPadreYNombre;
  private final JButton crearCarpetaConFileYNombre;
  private final JButton crearArchivo;
  private final JButton customDir;
  private final JButton customDelete;
  private final JButton renombrarFichero;
  private final JButton copiarArchivo;
  private final JButton moverArchivo;
  private final JButton customDeleteRecursive;
  
    // elementos JLabel | etiquetas ==>
  private final JLabel impresionPantalla;
  
    // elementos campo de texto | num. indefinidos ==>
  private JTextField campoRutaInput;
  private JTextField campoNombreInput;
  
  //------------------------------------------------>
   // contructor | crea e inicializa todos los componentes.
  public VentanaGrafica () {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panelPrincipal = new JPanel();
    panelPrincipal.setLayout (new BorderLayout (30, 30));
    
    JPanel panelAux = new JPanel();
    JPanel panelAux2 = new JPanel();
    
    campoRutaInput = new JTextField(10);
    panelAux.add(campoRutaInput);
    panelPrincipal.add(panelAux, BorderLayout.NORTH);
    
    campoNombreInput = new JTextField(10);
    panelAux.add(campoNombreInput);
    panelPrincipal.add(panelAux, BorderLayout.NORTH);
    
    impresionPantalla = new JLabel ("No se ha realizado ninguna operacion.");
    panelAux2.add(impresionPantalla);
    panelPrincipal.add(panelAux2, BorderLayout.CENTER);
    
     // asignacion de comandos a los botones -->
    crearCarpetaRutaCompleta = new JButton("Crear Carpeta Ruta Completa");
    crearCarpetaRutaCompleta.setActionCommand(CREAR_CARPETA_CON_RUTA_COMPLETA);
    
    crearCarpetaRutaPadreYNombre = new JButton("Crear Carpeta Ruta Padre y Nombre");
    crearCarpetaRutaPadreYNombre.setActionCommand(CREAR_CARPETA_CON_RUTA_PADRE_Y_NOMBRE);
    
    crearCarpetaConFileYNombre = new JButton("Crear Carpeta File y Nombre");
    crearCarpetaConFileYNombre.setActionCommand(CREAR_CARPETA_CON_RUTA_PADRE_Y_NOMBRE);
    
    crearArchivo = new JButton ("Crear Archivo");
    crearArchivo.setActionCommand(CREAR_ARCHIVO_CON_RUTA_Y_NOMBRE);
    
    customDir = new JButton ("DIR en Carpeta");
    customDir.setActionCommand(OBTENER_CONTENIDO_CARPETA);
    
    customDelete = new JButton ("Eliminar Ficheros Carpeta");
    customDelete.setActionCommand(BORRAR_FICHEROS_CARPETA);
    
    renombrarFichero = new JButton ("Renombrar Fichero");
    renombrarFichero.setActionCommand(RENOMBRAR_ARCHIVO_EXISTENTE);
    
    copiarArchivo = new JButton ("Copiar Fichero");
    copiarArchivo.setActionCommand(COPIAR_ARCHIVO_NUEVA_RUTA);
    
    moverArchivo = new JButton ("Mover Fichero");
    moverArchivo.setActionCommand(MOVER_ARCHIVO_NUEVA_RUTA);
    
    customDeleteRecursive = new JButton ("Borrar Contenido Carpeta");
    customDeleteRecursive.setActionCommand(BORRAR_FICHEROS_CARPETA_RECURSIVO);
    
     // resto de paneles -->
    JPanel botonera = new JPanel();
    botonera.add(crearCarpetaRutaCompleta);
    botonera.add(crearCarpetaRutaPadreYNombre);
    botonera.add(crearCarpetaConFileYNombre);
    botonera.add(crearArchivo);
    botonera.add(customDir);
    botonera.add(customDelete);
    botonera.add(renombrarFichero);
    botonera.add(copiarArchivo);
    botonera.add(moverArchivo);
    botonera.add(customDeleteRecursive);
    
    panelPrincipal.add(botonera, BorderLayout.SOUTH);
    getContentPane().add(panelPrincipal);
  }
  
  //------------------------------------------------>
   // metodo "seleccionaDirectorio" | extrae una carpeta o fichero usando el explorador =>
  public static String seleccionaDirectorio () {
    JFileChooser fichero = new JFileChooser();
    fichero.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    fichero.showSaveDialog(null);
    
    return (fichero.getSelectedFile().toString());
  }
  
  //------------------------------------------------>
   // metodo "setControlaodr" | asigna los controladores =>
  @Override
  public void setControladorArchivo (ControladorArchivo cA) {
    crearArchivo.addActionListener(cA);
    renombrarFichero.addActionListener(cA);
    copiarArchivo.addActionListener(cA);
    moverArchivo.addActionListener(cA);
  }
  
  @Override
  public void setControladorCarpeta (ControladorCarpeta cC) {
    crearCarpetaRutaCompleta.addActionListener(cC);
    crearCarpetaRutaPadreYNombre.addActionListener(cC);
    crearCarpetaConFileYNombre.addActionListener(cC);
    customDir.addActionListener(cC);
    customDelete.addActionListener(cC);
    customDeleteRecursive.addActionListener(cC);
  }
  
   // metodo "arranca" | coloca los componentes y los hace visibles =>
  @Override
  public void arranca () {
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }
  
   // metodo "operacion exitosa" | escribe mensaje de exito =>
  @Override
  public void operacionExitosa () {
    impresionPantalla.setText("La operacion ha sido un exito.");
  }
  
  @Override
  public void escribeResultado (String cadenaOutput) {
    impresionPantalla.setText(cadenaOutput);
  }
  
  public void limpiarCampos () {
    campoRutaInput.setText(null);
    campoNombreInput.setText(null);
  }
  
  //------------------------------------------------>
   // metodo "getRuta" | pida una ruta al usuario =>
  @Override
  public String getRuta () {
    return campoRutaInput.getText();
  }
  
  @Override
  public String getNombre () {
    return campoNombreInput.getText();
  }
  
  @Override
  public String getNuevaRuta () {
    return campoNombreInput.getText();
  }
  
  @Override
  public String getNombreBase () {
    return campoRutaInput.getText();
  }
}
