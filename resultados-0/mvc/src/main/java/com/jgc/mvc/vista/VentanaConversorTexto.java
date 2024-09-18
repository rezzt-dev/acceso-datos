/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.mvc.vista;

import com.jgc.mvc.controlador.ControlConversor;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.in;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 sept 2024
 */
public class VentanaConversorTexto implements InterfazVista {  
  private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  private double cantidad;
  private ControlConversor controlador;
  
  public VentanaConversorTexto () {
    super();
  }
  
  //----------------------------------------------------------------------|
  /**
   * Lee la opción seleccionada por el usuario. Valida si es un número válido.
   * @return la opción seleccionada
  */
  private int leerOpcion () {
    try {
      String opcion = in.readLine();
      return Integer.parseInt(opcion);
    } catch (IOException | NumberFormatException e) {
      opcionInvalida();
      return 0;
    }
  }
  
  /**
   * Lee la cantidad a convertir desde la entrada del usuario. Valida el formato numérico.
   * @return la cantidad introducida por el usuario
  */
  private double leerCantidad () {
    System.out.println("Introduce la cantidad: ");
    
    try {
      String cantInput;
      cantInput = in.readLine();
      return Double.parseDouble(cantInput);
      
    } catch (IOException | NumberFormatException e) {
      System.out.println("Error! Formato de numero no valido, debe ser 99.99");
      return leerCantidad();
    }
  }
  
  /**
   * Muestra el menú de opciones disponibles para la conversión.
  */
  public void mostrarMenu () {
    System.out.println("Indica la operacion que quieres realizar: ");
    System.out.println("1: cambiar de euros a pesetas");
    System.out.println("2: cambiar de pesetas a euros");
    System.out.println("0: salir");
  }
  
  /**
   * Procesa la operación seleccionada por el usuario y gestiona la conversión.
  */
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
        
        controlador.actionPerformed(new ActionEvent(this, opcion, APESETAS));
      }
      case 2 -> {
        controlador.actionPerformed(new ActionEvent(this, opcion, AEUROS));
      }
      default -> {
        opcionInvalida();
      }
    }
  }
  
  /**
   * Muestra un mensaje de opción inválida.
   */
  private void opcionInvalida () {
    System.out.println("Opcion Invalida!");
  }
  
  private int leerComision () {
    System.out.println("Introduce la comision: ");
    
    try {
      String comisionInput = in.readLine();
      return Integer.parseInt(comisionInput);
    } catch (IOException | NumberFormatException e) {
      System.out.println("Error! Formato de numero no valido, debe ser 9");
      return leerComision();
    }
  }
  
  //----------------------------------------------------------------------|
  /**
   * Configura el controlador que gestionará la lógica de la conversión.
   * @param c controlador que maneja la conversión
  */
  @Override
  public void setControlador(ControlConversor c) {
    this.controlador = c;
  }

  /**
   * Inicia la aplicación y ejecuta la operación de conversión seleccionada.
  */
  @Override
  public void arranca() {
    procesarNuevaOperacion();
  }

  /**
   * Devuelve la cantidad a convertir.
   * @return la cantidad a convertir
  */
  @Override
  public double getCantidad() {
    return leerCantidad();
  }

  /**
   * Muestra el resultado de la conversión.
   * @param s resultado de la conversión
  */
  @Override
  public void escribeCambio(String s) {
    System.out.println(s);
    System.out.println();
    procesarNuevaOperacion();
  }
  
  @Override
  public int getComision() {
    System.out.println("");
    return leerComision();
  }
}