/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.jgc.clasefile.vista;

import com.jgc.clasefile.controlador.ControlCarpeta;

/**
 * interfaz con los metodos que deben implementar las vistas
 * que se generen para la aplicacion
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 18 sept 2024
 */
public interface InterfazVista {
  static final String CREAR_CARPETA_CON_RUTA_COMPLETA = "crear carpeta con ruta completa";
  
 //----------------------------------------------------------------------|
  void setControlador (ControlCarpeta c);
  void arranca ();
  
  String getRuta();
}
