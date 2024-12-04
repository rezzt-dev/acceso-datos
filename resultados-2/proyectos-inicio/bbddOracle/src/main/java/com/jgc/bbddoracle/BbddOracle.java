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
      
      
    } finally {
      bbdd.cerrarConexion();
    }
  }
}
