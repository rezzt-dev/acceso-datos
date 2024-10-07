/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.jgc.clasefile.vista;

import com.jgc.clasefile.controlador.ControladorArchivo;
import com.jgc.clasefile.controlador.ControladorCarpeta;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Sep 20, 2024
 */
public interface InterfazVista {
  static final String CREAR_CARPETA_CON_RUTA_COMPLETA = "crear carpeta con ruta completa";
  static final String CREAR_CARPETA_CON_RUTA_PADRE_Y_NOMBRE = "crear carpeta con ruta padre y nombre";
  static final String CREAR_CARPETA_CON_FILE_Y_NOMBRE = "crear carpeta con ruta y nombre";
  static final String OBETENER_CONTENIDO_CARPETA = "obtener el contenido de una carpeta";
  static final String BORRAR_FICHEROS_CARPETA = "borrar los ficheros dentro de una carpeta";
  static final String BORRAR_FICHEROS_CARPETA_RECURSIVO = "borrar todo dentro de una carpeta";

    //-------------------------------------------|
  static final String CREAR_ARCHIVO_CON_RUTA_Y_NOMBRE = "crear archivo con ruta y nombre";
  static final String RENOMBRAR_ARCHIVO_EXISTENTE = "renombra un archivo existente";
  static final String COPIAR_ARCHIVO_NUEVA_RUTA = "copiar archivo a otra ubicacion";
  static final String MOVER_ARCHIVO_NUEVA_RUTA = "mover archivo a otra ubicacion";
  
  //------------------------------------------------>
  void setControladorArchivo (ControladorArchivo cA);
  void setControladorCarpeta (ControladorCarpeta cC);
  void arranca ();
  void operacionExitosa();
  void escribeResultado(String cadenaTexto);
  void limpiarCampos();
  
  //------------------------------------------------>
  String getRuta ();
  String getNombre();
  String getNuevaRuta ();
  String getNombreBase ();
  //------------------------------------------------>
}
