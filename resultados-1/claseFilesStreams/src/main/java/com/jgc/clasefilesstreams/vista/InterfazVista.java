/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jgc.clasefilesstreams.vista;

import com.jgc.clasefilesstreams.controlador.ControladorFileStreams;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public interface InterfazVista {
 // constantes y atributos -->
   // constantes - leer fichero =>
  static final String LEER_FICHERO_CARACTER = "leer un fichero caracter a caracter";
  static final String LEER_FICHERO_CADENA = "leer un fichero a partir de cadena de caracteres";
  static final String LEER_FICHERO_LINEA = "leer un fichero linea a linea";
  
   // constantes - escribir fichero =>
  static final String ESCRIBIR_FICHERO_CARACTER = "escribir un caracter en un fichero";
  static final String ESCRIBIR_FICHERO_CADENA = "escribir una cadena de caracteres en un fichero";
  static final String ESCRIBIR_FICHERO_LINEA = "escribir una linea en un fichero";
  
   // constantes - des/encriptar fichero =>
  static final String ENCRIPTAR_FICHERO = "encripta un fichero y devuelve el archivo encriptado";
  static final String DESENCRIPTAR_FICHERO = "desencripta un archivo cifrado y lo devuleve en formato fichero";
  static final String LEER_CONTENIDO_ENCRIPTADO = "lee el contenido de un fichero encriptado.";
  static final String LEER_CONTENIDO_DESENCRIPTADO = "lee el contenido de un fichero desencriptado.";

  
 //----------------------------------------------------------------------------->
 // metodos publicos -->
  void setControladorFileStreams(ControladorFileStreams cFS);
  void arranca();
  void operacionTerminada();
  void escribeResultado (String outputResult);
  boolean sobreescribirFichero ();
  char leerChar();
  String leerString();
  
 //----------------------------------------------------------------------------->
 // getters -->
  String getPath ();
}
