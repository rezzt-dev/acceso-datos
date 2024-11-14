/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.jgc.ejercicio3examen.vista;

import com.jgc.ejercicio3examen.controlador.ControladorProducto;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 nov 2024
 */
public interface InterfazVista {

  static final String AGREGAR_PRODUCTO = "agrega un producto al fichero";
  
  void setControlador(ControladorProducto inputControlador);
  void arranca();
  void operacionExitosa();
  void escribeCadena(String cadenaTexto);

  String getNombre();
  String getNombreSuper();
  int getCantidad();
}
