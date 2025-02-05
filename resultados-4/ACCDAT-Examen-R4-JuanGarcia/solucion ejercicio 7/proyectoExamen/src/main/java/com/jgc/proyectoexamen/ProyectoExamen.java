/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.proyectoexamen;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class ProyectoExamen {
  public static void main(String[] args) {
    System.out.println("--------------------------------------------------------------------------");
    OperacionesBBDD.abrirConexion();
    
    OperacionesBBDD.insertarVuelosEjemplo();
//    Piloto p1 = new Piloto(5, 80, 90);
//    OperacionesBBDD.modificarPiloto(1, p1);
//    OperacionesBBDD.visualizarVuelo(1);
    
    OperacionesBBDD.cerrarConexion();
    System.out.println("--------------------------------------------------------------------------");
  }
}
