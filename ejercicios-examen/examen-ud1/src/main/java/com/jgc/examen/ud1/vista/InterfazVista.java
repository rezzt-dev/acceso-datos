/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jgc.examen.ud1.vista;

import com.jgc.examen.ud1.controlador.ControlUniversidades;
import com.jgc.examen.ud1.modelo.Universidad;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public interface InterfazVista {
 // constantes & variables ->
  static final String CREAR_CARPETAS = "Crea 2 carpetas en la carpeta del proyecto";
  static final String DAR_ALTA_UNIVERSIDADES = "Dar de alta una universidad";
  static final String GENERAR_ARCHIVO_XML = "Genera un archivo XML de carreras universitarias";
  static final String GENERAR_PLANTILLA_XSL = "Genera un plantilla XSL";
  static final String MODIFICAR_CARRERA_XML = "Modificar datos de una carrera universitaria";
  static final String GENERAR_PAGINA_WEB_HTML = "Genera una pagina web a partir del XML";
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos abstractos ->
  void arranca();
  void setControlador(ControlUniversidades inputControlador);

 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // getters abstractos ->
  public String leerString();
  public Universidad getUniversidad();
  public int getIdUni();
}
