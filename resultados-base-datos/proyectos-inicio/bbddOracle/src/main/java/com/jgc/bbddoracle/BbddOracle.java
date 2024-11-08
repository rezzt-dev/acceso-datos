/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.bbddoracle;

import com.jgc.bbddoracle.bbdd.OperacionesBBDD;
import com.jgc.bbddoracle.modelo.Departamento;
import java.sql.SQLException;
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
//    
//    SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
//    java.util.Date fecha = null;
//    
//    try {
//      fecha = s.parse("26/04/2024");
//    } catch (ParseException ex) {
//      Logger.getLogger(BbddOracle.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    
//    java.sql.Date fechaSql = new java.sql.Date(fecha.getTime());
//    Empleado emple = new Empleado (1, "Garcia", "DIRECTOR", 555, fechaSql, 150.33, 1500, 10);
//    emple.insertar(bbdd);
//    
//    System.out.println(emple);
//    try {
//      Departamento.mostrarResultSet(Departamento.selectAll(bbdd));
//    } catch (SQLException ex) {
//      Logger.getLogger(BbddOracle.class.getName()).log(Level.SEVERE, null, ex);
//    }

    Departamento.getNomDept(bbdd, 10);
    
    bbdd.cerrarConexion();
  }
}
