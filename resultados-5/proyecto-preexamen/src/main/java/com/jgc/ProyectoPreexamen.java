/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc;

/**
 *
 * @author rezzt
 */
public class ProyectoPreexamen {
  public static void main(String[] args) {
    OperacionesBBDD operador = new OperacionesBBDD();
    
    operador.abrirConexion();
    operador.mostrarDepartamento();
    operador.cerrarConexion();
  }
}
