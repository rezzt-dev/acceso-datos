/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.proyectoexamen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 5 feb 2025
 */
public class OperacionesBBDD {
  private static Connection conexion;
  
  public static void abrirConexion() {
    try {
      String driver = "oracle.jdbc.OracleDriver";
      String urlConnection = "jdbc:oracle:thin:@localhost:1521:free";
      
      Properties propiedades = new Properties();
      propiedades.setProperty("user", "examen");
      propiedades.setProperty("password", "examen");
      
      Class.forName(driver);
      conexion = DriverManager.getConnection(urlConnection, propiedades);
      System.out.println("Conexión establecida correctamente.");
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, "¡Driver de Oracle no encontrado!", ex);
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, "Error al conectar con la base de datos", ex);
    }
  }
  
  public static void cerrarConexion() {
    try {
      if (conexion != null && !conexion.isClosed()) {
        conexion.close();
        System.out.println("Conexión cerrada correctamente.");
      }
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, "Error al cerrar la conexión", ex);
    }
  }

  private static void insertarVuelos(int idVuelo, String companiaVuelo, int dniPiloto, int horasVueloPiloto, int horasVueloAnual, int kilosCarga, int precioKilo) {
    String sql = "INSERT INTO t_vuelos_mercancias values (?,?,PILOTO(?,?,?),?,?)";
    
    try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
      sentencia.setInt(1, idVuelo);
      sentencia.setString(2, companiaVuelo);
      sentencia.setInt(3, dniPiloto);
      sentencia.setInt(4, horasVueloPiloto);
      sentencia.setInt(5, horasVueloAnual);
      sentencia.setInt(6, kilosCarga);
      sentencia.setInt(7, precioKilo);
      
      
      int filasAfectadas = sentencia.executeUpdate();
      System.out.println("Filas insertadas: " + filasAfectadas);
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, "Error al insertar datos", ex);
    }
  }
  
 //------------------------------------------------------------------------------------------------------------------------------------->
  public static void insertarVuelosEjemplo() {
    insertarVuelos(3, "aisa", 2, 34, 45, 366, 5);
    insertarVuelos(4, "aisa", 0,0,0, 600, 12);
  }
  
  public static void modificarPiloto (int idVuelo, Piloto inputPiloto) {
    String sql = "UPDATE TABLE (SELECT V.PILOTO FROM T_VUELOS_MERCANCIAS WHERE IDVUELO = ?) ALIAS_PILOTO SET ALIAS_PILOTO.DNI = ?, ALIAS_PILOTO.HORAS_DE_VUELO = ?, ALIAS_PILOTO.HORAS_DE_VUELO_ANUAL = ?";
    
    try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
      sentencia.setInt(1, idVuelo);
      sentencia.setInt(2, inputPiloto.getIdPiloto());
      sentencia.setInt(3, inputPiloto.getHorasVueloPiloto());
      sentencia.setInt(4, inputPiloto.getHorasVueloAnual());
      
      int filasAfectadas = sentencia.executeUpdate();
      System.out.println("Filas insertadas: " + filasAfectadas);
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, "Error al insertar datos", ex);
    }
  }
  
  public static void visualizarVuelo (int idVuelo) {
    String sql = "SELECT V.IDVUELO, V.PILOTO.DNI, V.COSTE_TRANSPORTE() FROM T_VUELOS_MERCANCIAS WHERE IDVUELO = ?";
    
    try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
      sentencia.setInt(1, idVuelo);
      
      System.out.println(sentencia.executeUpdate());
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, "Error al insertar datos", ex);
    }
  }
}
