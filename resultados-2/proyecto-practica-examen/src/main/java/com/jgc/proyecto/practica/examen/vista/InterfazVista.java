/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jgc.proyecto.practica.examen.vista;

import com.jgc.proyecto.practica.examen.controlador.ControlUniversidades;

/**
 *
 * @author rezzt
 */
public interface InterfazVista {
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // constantes | opciones del menu ->
  final String CREAR_ARBOL_DIRECTORIOS = "crea la estructura de carpetas";
  final String DAR_ALTA_CARRERA_UNIVERSITARIA = "da de alta una carrera universitaria";
  final String GENERAR_ARCHIVO_XML_CARRERAS = "crea el fichero xml del fichero binario de carreras universitarias";
  final String GENERAR_PLANTILLA_XSL = "genera una plantilla xsl para poder crear un html a traves del fichero xml";
  final String MODIFICAR_CARRERA_UNIVERSITARIA = "modifica una carrera universitaria";
  final String GENERAR_PAGINA_WEB_CARRERAS = "genera un archivo html utilizando el fichero xml y la plantilla xsl";
 
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos abstractos | metodos de utilidad ->
  void setControladorUniversidades (ControlUniversidades controladorUnis);
  void arranca();
  void escribirCadena(String inputCadena);
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // getters abstractos | getters de utilidad ->
  String getRuta();
  long getIdentificador();
  String getNombreCarrera();
  String getCiudadCarrera();
  double getNotaCorte();
}
