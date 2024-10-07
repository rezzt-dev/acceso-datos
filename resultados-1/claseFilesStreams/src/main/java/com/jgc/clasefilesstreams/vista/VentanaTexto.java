/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefilesstreams.vista;

import com.jgc.clasefilesstreams.controlador.ControladorFileStreams;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 30 sept 2024
 */
public class VentanaTexto implements InterfazVista {
 // constantes y atributos -->
  private final BufferedReader consoleReader = new BufferedReader (new InputStreamReader (System.in));
  private ControladorFileStreams controladorFileStreams;
  private String path;
  
 //----------------------------------------------------------------------------->
 // contructores -->
  public VentanaTexto () {
    super();
  }
  
 //----------------------------------------------------------------------------->
 // metodos privados -->
   // metodo "leerOpcion" =>
  private int leerOpcion () {
    try {
      String tempInput = consoleReader.readLine();
      return Integer.parseInt(tempInput);
    } catch (IOException | NumberFormatException e) {
      opcionInvalida();
      return 0;
    }
  }
  
  //----------------------------------------------------------------|
   // metodo "mostrarMenuGeneral" =>
  private void mostrarMenuGeneral () {
    System.out.println("\n\n");
    
    System.out.println("Indica la operacion que quieres realizar: ");
    System.out.println(" 1. leer un fichero.");
    System.out.println(" 2. escribir un fichero.");
    System.out.println(" 3. des/encriptar un fichero.");
    
    System.out.println("\n 0. salir.");
  }
  
    // submetodo "mostrarMenuLeer" =>
  private void mostrarMenuLeer () {
    System.out.println("Indica como quieres leer el fichero: ");
    System.out.println(" 1. leer fichero (caracter a caracter).");
    System.out.println(" 2. leer fichero (cadena de caracteres).");
    System.out.println(" 3. leer fichero (linea a linea).");
    
    System.out.println("\n 0. salir.");
  }
  
    // submetodo "mostrarMenuEscribir" =>
  private void mostrarMenuEscribir () {
    System.out.println("Indica que quieres escribir en el fichero: ");
    System.out.println(" 1. escribir un caracter.");
    System.out.println(" 2. escribir un conjunto de caracteres.");
    System.out.println(" 3. escribir una linea de texto.");
    
    System.out.println("\n 0. salir.");
  }
  
    // submetodo "mostrarMenuEncriptacion" =>
  private void mostrarMenuEncriptacion () {
    System.out.println("Indica que operacion quieres hacer: ");
    System.out.println(" 1. encriptar un fichero.");
    System.out.println(" 2. desencriptar un fichero.");
    System.out.println(" 3. leer fichero encriptado.");
    System.out.println(" 4. leer fichero desencriptado.");
    
    System.out.println("\n 0. salir.");
  }
  
  //----------------------------------------------------------------|
   // metodo "procesarNuevaOperacion" =>
  private void procesarNuevaOperacion () {
    mostrarMenuGeneral();
    int opcionGeneral = leerOpcion();
    
    switch (opcionGeneral) {
      case 0 -> {
        System.out.println("\n");
        System.exit(0);
      }
      
      case 1 -> {
        mostrarMenuLeer();
        int opcionLeer = leerOpcion();
        
        switch (opcionLeer) {
          case 0 -> {
            System.out.println("\n");
            System.exit(0);
          }
          
          case 1 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionLeer, LEER_FICHERO_CARACTER));
          }
          
          case 2 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionLeer, LEER_FICHERO_CADENA));
          }
          
          case 3 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionLeer, LEER_FICHERO_LINEA));
          }
        }
      }
      
      case 2 -> {
        mostrarMenuEscribir();
        int opcionEscribir = leerOpcion();
        
        switch (opcionEscribir) {
          case 0 -> {
            System.out.println("\n");
            System.exit(0);
          }
          
          case 1 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionEscribir, ESCRIBIR_FICHERO_CARACTER));
          }
          
          case 2 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionEscribir, ESCRIBIR_FICHERO_CADENA));
          }
          
          case 3 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionEscribir, ESCRIBIR_FICHERO_LINEA));
          }
        }
      }
      
      case 3 -> {
        mostrarMenuEncriptacion();
        int opcionEncriptar = leerOpcion();
        
        switch (opcionEncriptar) {
          case 0 -> {
            System.out.println("\n");
            System.exit(0);
          }
          
          case 1 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionEncriptar, ENCRIPTAR_FICHERO));
          }
          
          case 2 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionEncriptar, DESENCRIPTAR_FICHERO));
          }
          
          case 3 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionEncriptar, LEER_CONTENIDO_ENCRIPTADO));
          }
          
          case 4 -> {
            this.controladorFileStreams.actionPerformed(new ActionEvent(this, opcionEncriptar, LEER_CONTENIDO_DESENCRIPTADO));
          }
        }
      }
    }
    
    procesarNuevaOperacion();
  }
  
  //----------------------------------------------------------------|
   // metodo "opcionInvalida" =>
  private void opcionInvalida () {
    System.out.println("ERROR! Opcion Invalida.");
  }
  
 //----------------------------------------------------------------------------->
 // metodos publicos -->
   // metodo "setControladorFileStreams" =>
  @Override
  public void setControladorFileStreams (ControladorFileStreams cFS) {
    this.controladorFileStreams = cFS;
  }
  
  //----------------------------------------------------------------|
   // metodo "arranca" =>
  @Override
  public void arranca () {
    procesarNuevaOperacion();
  }
  
  //----------------------------------------------------------------|
   // metodo "operacionTerminada" =>
  @Override
  public void operacionTerminada () {
    System.out.println(" > Operacion realizada con exito.");
  }
  
  //----------------------------------------------------------------|
   // metodo "escribeResultado" =>
  @Override
  public void escribeResultado (String outputResultado) {
    System.out.println("\n" + outputResultado + "\n");
  }
  
  //----------------------------------------------------------------|
   // metodo "sobreescribirFichero" =>
  @Override
  public boolean sobreescribirFichero () {
    System.out.print("  > Quieres sobreescribir el fichero: ");
    String opcion = leerString().toLowerCase();
    boolean noSobreescribir = true;
    
    switch (opcion) {
      case "si" -> {
        noSobreescribir = false;
      }
      
      case "no" -> {
        noSobreescribir = true;
      }
      
      default -> {
        noSobreescribir = true;
      }
    }
    
    return noSobreescribir;
  }
  
  //----------------------------------------------------------------|
   // metodo "leerChar" =>
  @Override
  public char leerChar () {
    try {
      System.out.println("  > Introduce el caracter: ");
      String inputTemp = consoleReader.readLine();
      
      if (inputTemp != null && !inputTemp.isEmpty()) {
        return inputTemp.charAt(0); // Retornar el primer carácter
      } else {
        throw new IllegalArgumentException("No se ingresó ningún carácter."); // Manejo de caso vacío
      }
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    }
  }
  
  //----------------------------------------------------------------|
   // metodo "leerString" =>
  @Override
  public String leerString () {
    try {
      return consoleReader.readLine();
    } catch (IOException e) {
      System.out.println("ERROR! Formato introducido invalido.");
      return null;
    }
  }
  
 //----------------------------------------------------------------------------->
 // getters & setters -->
  @Override
  public String getPath () {
    System.out.print(" > Introduce la ruta: ");
    return leerString();
  }
}
