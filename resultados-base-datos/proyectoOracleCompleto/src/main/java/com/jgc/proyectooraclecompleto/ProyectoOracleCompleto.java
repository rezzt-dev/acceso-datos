/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.proyectooraclecompleto;

import com.jgc.proyectooraclecompleto.conection.OperacionesBBDD;
import com.jgc.proyectooraclecompleto.modelo.Empleado;

/**
 *
 * @author rezzt
 */
public class ProyectoOracleCompleto {
  private static OperacionesBBDD bbdd = OperacionesBBDD.getInstance();
  
  public static void main(String[] args) {
    bbdd.abrirConexion();
    
//    Empleado.getSalarioMedioNumeroEmpleadosDept(bbdd, 20);
    
//    bbdd.obtenerClavesFromTabla("EMPLEADOS");

    bbdd.cerrarConexion();
  }
}
