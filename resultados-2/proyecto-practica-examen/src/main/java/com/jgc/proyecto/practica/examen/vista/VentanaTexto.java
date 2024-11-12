/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyecto.practica.examen.vista;

import com.jgc.proyecto.practica.examen.controlador.ControlUniversidades;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author rezzt
 */
public class VentanaTexto implements InterfazVista {
 // constantes & atributos ->
  private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  
  private ControlUniversidades controladorUnis;
  private String path;
  private long idUni;
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // constructor | ventana texto -> 
  public VentanaTexto () {
    super();
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos privados ->
  private String leerString () {
    try {
      return in.readLine();
    } catch (IOException ex) {
      System.out.println(" > ERROR! Introduce correctamente la cadena.");
      return null;
    }
  }
  
  private int leerOpcion () {
    try {
      String opcion = in.readLine();
      return Integer.parseInt(opcion);
    } catch (IOException | NumberFormatException ex) {
      System.out.println(" > ERROR! Introduce una opcion valida.");
      return 0;
    }
  }
  
  private void mostrarMenu () {
    System.out.println("======================================");
    System.out.println("0. Salir");
    System.out.println("1. Crear Estructura de Carpetas");
    System.out.println("2. Alta datos Carreras Universitarias");
    System.out.println("3. Generar Archivo XML con Carreras Universitarias");
    System.out.println("4. Generacion de una Plantilla XSL");
    System.out.println("5. Modificar Carrera Universitaria");
    System.out.println("6. Generacion de Pagina Web con Carreras Universitarias");
    System.out.println("======================================");
    System.out.println("\n Selecciona una opcion: ");
  }
  
  private void procesarNuevaOperacion () {
    mostrarMenu();
    int opcion = leerOpcion();
    
    switch (opcion) {
      case 0 -> {
        System.out.println("\n");
        System.exit(0);
      }
      
      case 1 -> {
        this.controladorUnis.actionPerformed(new ActionEvent(this, opcion, CREAR_ARBOL_DIRECTORIOS));
      }
      
      case 2 -> {
        this.controladorUnis.actionPerformed(new ActionEvent(this, opcion, DAR_ALTA_CARRERA_UNIVERSITARIA));
      }
      
      case 3 -> {
        this.controladorUnis.actionPerformed(new ActionEvent(this, opcion, GENERAR_ARCHIVO_XML_CARRERAS));
      }
      
      case 4 -> {
        this.controladorUnis.actionPerformed(new ActionEvent(this, opcion, GENERAR_PLANTILLA_XSL));
      }
      
      case 5 -> {
        this.controladorUnis.actionPerformed(new ActionEvent(this, opcion, MODIFICAR_CARRERA_UNIVERSITARIA));
      }
      
      case 6 -> {
        this.controladorUnis.actionPerformed(new ActionEvent(this, opcion, GENERAR_PAGINA_WEB_CARRERAS));
      }
    }
    
    procesarNuevaOperacion();
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // override | metodos publicos ->
  @Override
  public void setControladorUniversidades (ControlUniversidades inputControlador) {
    this.controladorUnis = inputControlador;
  }
  
  @Override
  public void escribirCadena (String inputCadena) {
    System.out.println(inputCadena);
  }
  
  @Override
  public void arranca () {
    procesarNuevaOperacion();
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // override | getters ->
  @Override
  public String getRuta () {
    return leerString();
  }
  
  @Override
  public long getIdentificador () {
    System.out.println("  - Introduce el Identificador: ");
    
    try {
      String opcion = in.readLine();
      return Long.parseLong(opcion);
    } catch (IOException | NumberFormatException ex) {
      System.out.println(" > ERROR! Introduce una opcion valida.");
      return 0L;
    }
  }
  
  @Override
  public String getNombreCarrera () {
    System.out.println("  - Introduce el Nombre de la Carrera: ");
    return leerString();
  }
  
  @Override
  public String getCiudadCarrera () {
    System.out.println("  - Introduce la Ciudad de la Carrera: ");
    return leerString();
  }
  
  @Override
  public double getNotaCorte () {
    System.out.println("  - Introduce la Nota de Corte: ");
    
    try {
      String opcion = in.readLine();
      return Double.parseDouble(opcion);
    } catch (IOException | NumberFormatException ex) {
      System.out.println(" > ERROR! Introduce una opcion valida.");
      return 0.0;
    }
  }
}
