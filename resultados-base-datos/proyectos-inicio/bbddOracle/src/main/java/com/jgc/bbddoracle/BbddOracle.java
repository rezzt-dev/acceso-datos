/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.bbddoracle;

import com.jgc.bbddoracle.bbdd.OperacionesBBDD;
import com.jgc.bbddoracle.modelo.Departamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rezzt
 */
public class BbddOracle {
  private static OperacionesBBDD bbdd = OperacionesBBDD.getInstance();
  
  public static void main(String[] args) {
    bbdd.abrirConexion();
    
    try {
      int numDept = 20;
      double subida = 100.00;
      
      bbdd.llamarProcedimientoSubidaSal(numDept, subida);
      int numEmpleados = bbdd.llamarFuncionNEmpleado(numDept);
      
      System.out.println(" El numero total de empleados es: " + numEmpleados);
      
      bbdd.mostrarNominaEmpleados();
    } finally {
      bbdd.cerrarConexion();
    }
  }
}
