/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.bbddoracle.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rezzt
 */
public class ConexionOracle {
  private final String driver = "oracle.jdbc.driver.OracleDriver";
  private final String urlconnection = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";   // Cadena de conexión

  private Connection conexion = null;
  private Properties propiedades = null;
  
  public void abrirConexion() {
    try {
      this.propiedades = new Properties();
      this.propiedades.setProperty("user", "dam2");
      this.propiedades.setProperty("password", "dam2");
      
      Class.forName(driver);
      this.conexion = DriverManager.getConnection(urlconnection, propiedades);
    } catch (ClassNotFoundException | SQLException ex) {
      Logger.getLogger(ConexionOracle.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void cierraConexion () {
    try {
      this.conexion.close();
    } catch (SQLException ex) {
      Logger.getLogger(ConexionOracle.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
