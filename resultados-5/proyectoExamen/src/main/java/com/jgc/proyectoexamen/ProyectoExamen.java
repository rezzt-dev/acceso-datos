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
    OperacionesBBDD operador = new OperacionesBBDD();
    
    operador.abrirConexion();
    operador.MostrarClientes();
    operador.cerrarConexion();
  }
}
