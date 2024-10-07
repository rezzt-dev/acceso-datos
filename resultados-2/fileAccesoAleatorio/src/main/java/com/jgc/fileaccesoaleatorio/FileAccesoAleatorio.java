/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.fileaccesoaleatorio;

import com.jgc.fileaccesoaleatorio.modelo.FicheroEmpleados;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class FileAccesoAleatorio {

  public static void main(String[] args) {
    FicheroEmpleados fichero = new FicheroEmpleados("empleados.dat");
    Scanner scanner = new Scanner(System.in);

    System.out.println("Modificar apellido de empleado");
    System.out.print("Introduce el ID del empleado: ");
    
    long id = scanner.nextLong();
    scanner.nextLine(); // Consumir el salto de línea

    System.out.print("Introduce el nuevo apellido: ");
    String nuevoApellido = scanner.nextLine();

    try {
      boolean modificado = fichero.modificarApellido(id, nuevoApellido);
      
      if (modificado) {
        System.out.println("Apellido modificado con éxito.");
      } else {
        System.out.println("No se encontró un empleado con el ID especificado.");
      }
    } catch (IOException e) {
      System.out.println("Error al modificar el apellido: " + e.getMessage());
    }

    scanner.close();
  }
}
