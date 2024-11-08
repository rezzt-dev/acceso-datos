/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.bbddoracle.bbdd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 nov 2024
 */
public class OperacionesBBDD {
  private String driver;
  private String urlconnection;

  private Connection conexion = null;
  private Properties propiedades = null;
  
  private static OperacionesBBDD operacionesBBDD;
  private PreparedStatement preparedStatement;
  
  private OperacionesBBDD () {
    driver = "oracle.jdbc.driver.OracleDriver";
    urlconnection = "jdbc:oracle:thin:@localhost:1521/FREE";
  }
  
  public static OperacionesBBDD getInstance () {
    if (operacionesBBDD == null) {
      operacionesBBDD = new OperacionesBBDD();
    }
    
    return operacionesBBDD;
  }
  
  public void abrirConexion () {
    try {
      this.propiedades = new Properties();
      this.propiedades.setProperty("user", "dam2");
      this.propiedades.setProperty("password", "dam2");
      
      Class.forName(driver);
      this.conexion = DriverManager.getConnection(urlconnection, propiedades);
    } catch (ClassNotFoundException | SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void cerrarConexion () {
    try {
      this.conexion.close();
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public Optional<ResultSet> insert(String inputInsertSQL, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputInsertSQL,PreparedStatement.RETURN_GENERATED_KEYS);
    
     // inputInsertSQL => "insert into Departamentos values (?,?,?)"
     // params =>  [1, "informatica", "ciudad real"]
    
    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
     
    preparedStatement.executeUpdate();
    return  Optional.of(preparedStatement.getGeneratedKeys());
  }
  
  private ResultSet executeQuery (String inputQuerySQL, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputQuerySQL);
    
    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
    
    return preparedStatement.executeQuery();
  }
  
  public Optional<ResultSet> select (String inputQuerySQL, Object... params) throws SQLException {
    return Optional.of(executeQuery(inputQuerySQL, params));
  }
  
  private int updateQuery (String inputQuerySQL, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputQuerySQL);

    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
     
    return preparedStatement.executeUpdate();
  }
  
  public int update (String inputQuerySQL, Object... params) throws SQLException {
    return updateQuery(inputQuerySQL, params);
  }
  
  public int delete (String inputQuerySQL, Object... params) throws SQLException {
    return updateQuery(inputQuerySQL, params);
  }

  public Connection getConexion() {
    return conexion;
  }
}
