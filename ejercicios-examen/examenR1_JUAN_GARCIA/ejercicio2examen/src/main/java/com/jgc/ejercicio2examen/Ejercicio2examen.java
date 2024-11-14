/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.ejercicio2examen;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class Ejercicio2examen {
  public static void main(String[] args) {
    Reforma reforma = new Reforma(4, "Renovar Fontaneria", "Calle Toledo 47 2A, Ciudad Real", 600);
    Modelo modelo = new Modelo();
    
    modelo.insertaEjercicio2(reforma);
    modelo.muestraEjercicio2(4);
    reforma.setCoste(700);
    modelo.insertaEjercicio2(reforma);
    modelo.muestraEjercicio2(4);
  }
}
