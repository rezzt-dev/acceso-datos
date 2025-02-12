/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.proyecto.existdb;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class ProyectoExistdb {
  static OperacionesBBDD oper = new OperacionesBBDD();
  
  public static void main(String[] args) {
    oper.abrirConexion();
    oper.lecturaDepartamento();
    oper.cerrarConexion();
  }
}
