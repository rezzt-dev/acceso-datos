/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.fileaccesoaleatorio.vista;

import com.jgc.fileaccesoaleatorio.controlador.ControladorRegistros;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 14 oct 2024
 */
public class VentanaTexto implements InterfazVista {
 // atributos & constantes =>
  private final BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
  private ControladorRegistros controlRegistros;
  private String ruta;
  private long identificador;
  
 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public VentanaTexto () {
    super();
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos privados =>
  private String leerString () {
    try {
      return in.readLine();
    } catch (IOException e) {
      System.out.println("ERROR! Introduce correctamente la cadena.");
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
  
  private int leerIdentificador () {
    try {
      String opcion = in.readLine();
      return Integer.parseInt(opcion);
    } catch (IOException e) {
      System.err.println("ERROR! Introduce un identificador correcto.");
      return 0;
    }
  }
  
  private double leerDouble () {
    try {
      String opcion = in.readLine();
      return Double.parseDouble(opcion);
    } catch (IOException e) {
      System.err.println("ERROR! Introduce un salario correcto.");
      return 0.0;
    }
  }
  
  private void mostrarMenu () {
    System.out.println("\n\n");
    System.out.println("Indica la operacion que quieres realizar: ");
    System.out.println(" 1: leer un empleado.");
    System.out.println(" 2: mostrar registros guardados.");
    System.out.println(" 3: escribir empleado.");
    System.out.println(" 4: modificar apellido empleado.");
    System.out.println(" 5: almacenar registro.");
    System.out.println(" 6: realizar borrado logico.");

    System.out.println("\n 0: salir.");
  }
  
  private void procesarNuevaOperacion () {
    mostrarMenu();
    int opcion;
    
    opcion = leerOpcion();
    
    switch (opcion) {
      case 0 -> {
        System.out.println("\n");
        System.exit(0);
      }
      
      case 1 -> {
        this.controlRegistros.actionPerformed(new ActionEvent(this, opcion, LEER_EMPLEADO));
      }
      
      case 2 -> {
        this.controlRegistros.actionPerformed(new ActionEvent(this, opcion, MOSTRAR_REGISTROS));
      }
      
      case 3 -> {
        this.controlRegistros.actionPerformed(new ActionEvent(this, opcion, ESCRIBIR_EMPLEADO_FINAL_ARCHIVO));
      }
      
      case 4 -> {
        this.controlRegistros.actionPerformed(new ActionEvent(this, opcion, MODIFICAR_APELLIDO_EMPLEADO));
      }
      
      case 5 -> {
        this.controlRegistros.actionPerformed(new ActionEvent(this, opcion, ALMACENAR_REGISTRO));
      }
      
      case 6 -> {
        this.controlRegistros.actionPerformed(new ActionEvent(this, opcion, BORRADO_LOGICO));
      }
    }
    
    procesarNuevaOperacion();
  }
  
  private void opcionInvalida () {
    System.out.println("ERROR! Opcion Invalida.");
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos publicos =>
  @Override
  public void arranca() {
    procesarNuevaOperacion();
  }
  
  @Override
  public void operacionExitosa () {
    System.out.println("Operacion realizada con Exito!");
  }
  
  @Override
  public void escribeResultado (String inputCadena) {
    System.out.println(inputCadena);
  }
  
  
 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
  @Override
  public void setControladorRegistros (ControladorRegistros cR) {
    this.controlRegistros = cR;
  }
  
  @Override
  public String getRuta () {
    System.out.println(" > Introduce la ruta: ");
    return leerString();
  }
  
  @Override
  public int getIdentificador () {
    System.out.println(" > Introduce el identificador: ");
    return leerIdentificador();
  }
  
  @Override
  public String getApellido () {
    System.out.println(" > Introduce el apellido: ");
    return leerString();
  }
  
  @Override
  public int getDepartamento () {
    System.out.println(" > Introduce el departamento: ");
    return leerOpcion();
  }
  
  @Override
  public double getSalario () {
    System.out.println(" > Introduce el salario: ");
    return leerDouble();
  }
}