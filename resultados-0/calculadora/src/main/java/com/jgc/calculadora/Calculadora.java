/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.calculadora;

import java.util.Scanner;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class Calculadora {
  public static void main(String[] args) {
    /**
     * Calculadora Divisas
     * @param opcion opcion del menu, valores validos del 0-2
     * @param euros valor decimal de cantidad para pasar a pesetas
     * @param pesetas valor decimal de cantidad para pasar a euros
     * 
     * @return devuelve el resultado del cambio
    */
    
    Scanner scanner = new Scanner(System.in);
    System.out.println("Indica la operacion que quiere realizar:");
    System.out.println(" 1: convertir de euros a pesetas.");
    System.out.println(" 2: convertir de pesetas a euros.");
    System.out.println(" 0: salir.");
    
    int opcion = scanner.nextInt();
    
    switch (opcion) {
      case 0 -> {
        break;
      }
      case 1 -> {
        System.out.print(" > Introduce la cantidad euros: ");
        float euros = scanner.nextFloat();
        
        System.out.println("  - Pesetas: " + (euros*166.386));
        break;
      }
      case 2 -> {
        System.out.print(" > Introduce la cantidad pesetas: ");
        float pesetas = scanner.nextFloat();
        
        System.out.println("  - Euros: " + (pesetas/166.386));
        break;
      }
      default -> {
        System.out.println("!Opcion Incorrecta¡");
        break;
      }
    }
  }
}
