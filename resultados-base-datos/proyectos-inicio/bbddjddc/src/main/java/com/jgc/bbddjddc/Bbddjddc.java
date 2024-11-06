/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.bbddjddc;

import com.jgc.bbddjddc.bbdd.OperacionesBBDD;
import com.jgc.bbddjddc.modelo.Departamento;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class Bbddjddc {
  static OperacionesBBDD operBBDD = OperacionesBBDD.getInstance();
  
  public static void main(String[] args) {
    try {
      operBBDD.abrirConexion();
      
      Departamento dept = new Departamento();
      dept.selectById(operBBDD, 1);
      System.out.println(dept);
      
      dept.setLocalidadDep("ciudad real");
      dept.update(operBBDD);
      
      dept.selectById(operBBDD, 1);
      System.out.println(dept);
      
      operBBDD.cerrarConexion();
    } catch (SQLException ex) {
      Logger.getLogger(Bbddjddc.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
