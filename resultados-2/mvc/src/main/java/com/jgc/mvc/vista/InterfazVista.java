/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.jgc.mvc.vista;

import com.jgc.mvc.controlador.ControlConversor;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 sept 2024
 */
public interface InterfazVista {
  /**
   * constantes para las operaciones
   */
  static final String AEUROS = "Pesetas a Euros";
  static final String APESETAS = "Euros a Pesetas";
  static final String COMISION = "Sumar Comision";
  //-----------------------------------------------------------------------------
  
  void setControlador (ControlConversor c);
  void arranca ();
  
  double getCantidad ();
  void escribeCambio (String s);
  
  int getComision();
}
