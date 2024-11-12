/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.examen.ud1.vista;

import com.jgc.examen.ud1.controlador.ControlUniversidades;
import com.jgc.examen.ud1.modelo.FicheroUniversidad;
import com.jgc.examen.ud1.modelo.Universidad;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 11 nov 2024
 */
public class VentanaTexto implements InterfazVista {
 // constantes & variables ->
  private ControlUniversidades controlador;
  private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos privados ->
   // metodo | mostrarMenu | muestra un menu de las operaciones disponibles ->
  private void mostrarMenu () {
    System.out.println("=============================================================");
    System.out.println("0. Salir");
    System.out.println("1. Crear estructura de carpetas");
    System.out.println("2. Alta datos Carreras Universitarias");
    System.out.println("3. Generar archivo XML con Carreras Universitarias");
    System.out.println("4. Generacion de plantilla XSL");
    System.out.println("5. Modificar Carrera Universitaria");
    System.out.println("6. Generacion de pagina web con Carreras Universitarias");
    System.out.println("=============================================================");
    System.out.print(" > Introduzca la opcion: ");
  }
  
   // metodo | leerOpcion | lee una opcion y devuelve un int ->
  private int leerOpcion() {
    try {
      String cadena = in.readLine();
      return Integer.parseInt(cadena);
    } catch (IOException | NumberFormatException ex) {
      System.out.println("  - Operacion Incorrecta.");
      return 0;
    }
  }
  
   // metodo | procesarNuevaOperacion | procesa las operaciones en bucle ->
  private void procesarNuevaOperacion () {
    mostrarMenu();
    int opcion = leerOpcion();
    switch (opcion) {
      case 0 -> {
        System.out.println("  - Ejecucion Finalizada.");
        System.exit(0);
      }
      
      case 1 -> controlador.actionPerformed(new ActionEvent(this, opcion, CREAR_CARPETAS));
      case 2 -> controlador.actionPerformed(new ActionEvent(this, opcion, DAR_ALTA_UNIVERSIDADES));
      case 3 -> controlador.actionPerformed(new ActionEvent(this, opcion, GENERAR_ARCHIVO_XML));
      case 4 -> controlador.actionPerformed(new ActionEvent(this, opcion, GENERAR_PLANTILLA_XSL));
      case 5 -> controlador.actionPerformed(new ActionEvent(this, opcion, MODIFICAR_CARRERA_XML));
      case 6 -> controlador.actionPerformed(new ActionEvent(this, opcion, GENERAR_PAGINA_WEB_HTML));
    }
    
    procesarNuevaOperacion();
  }
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos publicos ->
  @Override
  public void arranca() {
    procesarNuevaOperacion();
  }
  
  @Override
  public void setControlador (ControlUniversidades inputControlador) {
    this.controlador = inputControlador;
  }
  
  @Override
  public void escribirCadena (String inputCadena) {
    System.out.println(inputCadena);
  }
  
  @Override
  public String leerString () {
    try {
      return in.readLine();
    } catch (IOException ex) {
      Logger.getLogger(VentanaTexto.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }
  
  @Override
  public Universidad getUniversidad () {
    System.out.println(" > Introduce el ID de la Universidad: ");
    int tempId = leerOpcion();
    
    System.out.println(" > Introduce el Nombre de la Carrera: ");
    String tempCarrera = leerString();
    
    System.out.println(" > Introduce la Ciudad de la Carrera: ");
    String tempCiudad = leerString();
    
    System.out.println(" > Introduce la Nota de Corte: ");
    double tempNotaCorte = 0;
    
    try {
      tempNotaCorte = Double.parseDouble(in.readLine());
    } catch (IOException ex) {
      Logger.getLogger(VentanaTexto.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return new Universidad(tempId, tempCarrera, tempCiudad, tempNotaCorte);
  }
  
  @Override
  public int getIdUni () {
    System.out.println(" > Introduce el ID: ");
    try {
      String cadena = in.readLine();
      return Integer.parseInt(cadena);
    } catch (IOException | NumberFormatException ex) {
      System.out.println("  - Operacion Incorrecta.");
      return 0;
    }
  }
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // getters & setters ->
}
