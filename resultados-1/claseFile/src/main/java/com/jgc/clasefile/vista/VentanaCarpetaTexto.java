/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefile.vista;

import com.jgc.clasefile.controlador.ControlCarpeta;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 18 sept 2024
 */
public class VentanaCarpetaTexto implements InterfazVista {
  private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  private ControlCarpeta controlador;
  private String ruta;
  
  
 //----------------------------------------------------------------------|
  public VentanaCarpetaTexto () {
    super();
  }
  
  
 //----------------------------------------------------------------------|
  private String leerString () {
    try {
      return in.readLine();
    } catch (IOException e) {
      System.out.println("Error! La cadena no ha sido introducida correctamente.");
      return null;
    }
  }
  
  private int leerOpcion () {
    try {
      String opcion = in.readLine();
      return Integer.parseInt(opcion);
    } catch (IOException | NumberFormatException e) {
      opcionInvalida();
      return 0;
    }
  }
  
  public void mostrarMenu () {
    System.out.println("Indica la operacion que quieres realizar: ");
    System.out.println("1: crear carpeta con ruta completa");
    System.out.println("0: salir");
  }
  
  private void procesarNuevaOperacion () {
    int opcion;
    mostrarMenu();
    opcion = leerOpcion();
    
    switch (opcion) {
      case 0 -> {
        System.out.println("");
        System.exit(0);
      }
      case 1 -> {
        
        controlador.actionPerformed(new ActionEvent(this, opcion, CREAR_CARPETA_CON_RUTA_COMPLETA));
      }
      default -> {
        opcionInvalida();
      }
    }
    
    procesarNuevaOperacion();
  }
  
  private void opcionInvalida() {
    System.out.println("Opcion Invalida!");
  }
  
  
 //----------------------------------------------------------------------|
  @Override
  public void setControlador(ControlCarpeta c) {
    this.controlador = c;
  }

  @Override
  public void arranca() {
    procesarNuevaOperacion();
  }

  @Override
  public String getRuta() {
    System.out.println("Introduce la ruta: ");
    return leerString();
  }  
}
