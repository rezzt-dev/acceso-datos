/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.bbddjddc;

import com.jgc.bbddjddc.bbdd.OperacionesBBDD;
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
    operBBDD.abrirConexion();
    insertarDatos();
    operBBDD.cerrarConexion();
  }
  
  public static void insertarDatos () {
    try {
      operBBDD.insert("insert into Departamentos values (?,?,?)", 1, "informatica", "ciudad real");
    } catch (SQLException ex) {
      Logger.getLogger(Bbddjddc.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
