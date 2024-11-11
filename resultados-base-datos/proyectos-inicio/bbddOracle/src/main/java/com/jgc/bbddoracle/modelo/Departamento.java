/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.bbddoracle.modelo;


import com.jgc.bbddoracle.bbdd.OperacionesBBDD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 nov 2024
 */
public class Departamento {
  private int idDep;
  private String nombreDep;
  private String localidadDep;

  public Departamento () {}
  
  public Departamento(int idDep, String nombreDep, String localidadDep) {
    this.idDep = idDep;
    this.nombreDep = nombreDep;
    this.localidadDep = localidadDep;
  }
  
  public void insert (OperacionesBBDD inputBBDD) {
    try {
      inputBBDD.insert("insert into Departamentos values (?,?,?)", idDep, nombreDep, localidadDep);
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void selectById (OperacionesBBDD inputBBDD, int n) {
    try {
      Optional<ResultSet> result = inputBBDD.select("SELECT * FROM Departamentos WHERE dept_no = ?", n);
      
      if (result.isPresent()) {
        while (result.get().next()) {
          this.idDep = result.get().getInt("dept_no");
          this.nombreDep = result.get().getString("dnombre");
          this.localidadDep = result.get().getString("loc");
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void update (OperacionesBBDD inputBBDD) throws SQLException {
    inputBBDD.update("UPDATE departamentos set dnombre = ?, loc = ? WHERE dept_no = ?", this.nombreDep, this.localidadDep, this.idDep);
  }
  
  public static void getNomDept (OperacionesBBDD inputBBDD, int inputNumDep) {
    String salidaReturn = "";
    Connection conexion = inputBBDD.getConexion();
    
    try {
      String sqlProced = "{call p_nombre_depart (?,?)}";
      CallableStatement llamada = conexion.prepareCall(sqlProced);
      
      llamada.setInt(1, inputNumDep);
      llamada.registerOutParameter(2, Types.VARCHAR);
      
      llamada.executeUpdate();
      salidaReturn = " > Departamento " + inputNumDep + ": " + llamada.getString(2);
    } catch (SQLException ex) {
      if (ex.getSQLState().equals("02000") || ex.getErrorCode() == 1403) { // data not found
        salidaReturn = " > El departamento con numero " + inputNumDep + " no existe.";
      }
    } finally {
      System.out.println(salidaReturn);
    }
  }
  
  public static void delete (OperacionesBBDD inputBBDD, int n_dept) throws SQLException {
    inputBBDD.delete("DELETE FROM departamentos WHERE dept_no = ?", n_dept);
  }
  
  public static Optional<ResultSet> selectAll (OperacionesBBDD inputBBDD) {
    Optional<ResultSet> result = null;
    
    try {
      result = inputBBDD.select("SELECT * FROM departamentos");
    } catch (SQLException ex) {
      System.err.println(" > Ha ocurrido un error.");
      System.err.println("  - Mensaje: " + ex.getMessage());
      System.err.println("  - SQL Estado: " + ex.getSQLState());
      
      switch (ex.getErrorCode()) {
        case 942:
          System.err.println(" > Esta tabla no existe.");
        default:
          System.err.println(" > Error no identificado.");
      }
    }
    
    return result;
  }
  
  public static void mostrarResultSet (Optional<ResultSet> result) throws SQLException {
    if (result.isPresent()) {
      while(result.get().next()) {
        System.out.println("Numero Departamento: " + result.get().getInt("dept_no") + " | Nombre: " + result.get().getString("dnombre") + " | Localidad: " + result.get().getString("loc"));
      }
    }
  }
  
  public void update(ResultSet rs){
    try {
      rs.beforeFirst();
      //Updating the salary of each employee by 5000
      while(rs.next()) {
        //Retrieve by column name
        rs.updateString("dnombre", this.nombreDep);
        rs.updateString("loc", this.localidadDep);
        rs.updateRow();
      } 
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static String pNombreDepart(OperacionesBBDD bbdd, int dept_no) {
    CallableStatement llamada;
    String dnombre = null;
    try {     
      //Vamos a llamar a un procedimiento con la siguiente cabecera
      //PROCEDURE P_NOMBRE_DEPART(NDEPART NUMBER, NOMBRE_DEPART OUT VARCHAR2)
      //Preparamos el string para la llamada:
      String sql = "{call p_nombre_depart (?,?)}"; 

      //Creamos un objeto llamando al método prepareCall:
      llamada=bbdd.getConexion().prepareCall(sql);

      //Indicamos cuáles son los parámetros de entrada y cuales los de salida
      //Le damos valor al parámetro de entrada:
      llamada.setInt(1, dept_no);
      //Registramos el parámetro de salida de la función:
      llamada.registerOutParameter(2, Types.VARCHAR);

      //Realizamos la llamada al procedimiento:
      llamada.executeUpdate();

      //Obtenemos el valor del primer parámetro de salida
      dnombre = llamada.getString(2);
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }

    return  dnombre;
  }
  
  public static String fNombreDepart(OperacionesBBDD bbdd, int dept_no) {
    CallableStatement llamada;
    String dnombre = null;
    try {     
      String sql = "{?=call f_nombre_depart (?)}";
      llamada=bbdd.getConexion().prepareCall(sql);
      llamada.setInt(2, dept_no);
      llamada.registerOutParameter(1, Types.VARCHAR);
      llamada.executeUpdate();
      dnombre = llamada.getString(1);
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return  dnombre;
  }

  @Override
  public String toString() {
    return "Departamento{" + "idDep=" + idDep + ", nombreDep=" + nombreDep + ", localidadDep=" + localidadDep + '}';
  }

  public int getIdDep() {
    return idDep;
  }

  public void setIdDep(int idDep) {
    this.idDep = idDep;
  }

  public String getNombreDep() {
    return nombreDep;
  }

  public void setNombreDep(String nombreDep) {
    this.nombreDep = nombreDep;
  }

  public String getLocalidadDep() {
    return localidadDep;
  }

  public void setLocalidadDep(String localidadDep) {
    this.localidadDep = localidadDep;
  }
}
