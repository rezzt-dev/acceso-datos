/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.ejercicio3examen.vista;

import com.jgc.ejercicio3examen.controlador.ControladorProducto;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 nov 2024
 */
public class VentanaTexto implements InterfazVista {
  private final BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
  private ControladorProducto controlProduct;
  
  private void procesarOperacion () {
    int opcion = 1;
    this.controlProduct.actionPerformed(new ActionEvent(this, opcion, AGREGAR_PRODUCTO));
  }
  
  
  @Override
  public void setControlador(ControladorProducto inputControlador) {
    controlProduct = inputControlador;
  }

  @Override
  public void arranca() {
    procesarOperacion();
  }

  @Override
  public void operacionExitosa() {
    escribeCadena(" > Operacion Realizada con Exito");
  }

  @Override
  public void escribeCadena(String cadenaTexto) {
    System.out.println(cadenaTexto);
  }

  @Override
  public String getNombre() {
    try {
      return in.readLine();
    } catch (IOException ex) {
      System.out.println("ERROR!");
      return "";
    }
  }

  @Override
  public String getNombreSuper() {
    try {
      return in.readLine();
    } catch (IOException ex) {
      System.out.println("ERROR!");
      return "";
    }
  }

  @Override
  public int getCantidad() {
    try {
      String raw = in.readLine();
      return Integer.parseInt(raw);
    } catch (IOException ex) {
      System.out.println("ERROR!");
      return 0;
    }
  }
}
