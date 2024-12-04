/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectooraclecompleto.modelo;

import com.jgc.proyectooraclecompleto.conection.OperacionesBBDD;
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
 * @author rezzt
 */
public class Departamento {
   // constantes & atributos -->
  private int idDep;
  private String nombreDep;
  private String locDep;
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // constructores -->
  public Departamento () {}
  
  public Departamento(int inputIdDep, String inputNombreDep, String inputLocDep) {
    this.idDep = inputIdDep;
    this.nombreDep = inputNombreDep;
    this.locDep = inputLocDep;
  }
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // metodos privados & publicos -->
    // metodo | insert | insertar departamento ->
  public void insert (OperacionesBBDD bbdd) {
    try {
      bbdd.insert("insert into Departamentos values (?,?,?)", 
              idDep, nombreDep, locDep);
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | update | actualiza un departamento de la bbdd -->
  public void update (OperacionesBBDD inputBBDD) throws SQLException {
    inputBBDD.update("UPDATE departamentos set dnombre = ?, loc = ? WHERE dept_no = ?", this.nombreDep, this.locDep, this.idDep);
  }

   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | delete | elimina un departamento de la bbdd -->
  public void delete (OperacionesBBDD inputBBDD, int n_dept) throws SQLException {
    inputBBDD.delete("DELETE FROM departamentos WHERE dept_no = ?", n_dept);
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | selectById | selecciona un departamento por su id -->
  public void selectById (OperacionesBBDD inputBBDD, int n) {
    try {
      Optional<ResultSet> result = inputBBDD.select("SELECT * FROM Departamentos WHERE dept_no = ?", n);
      
      if (result.isPresent()) {
        while (result.get().next()) {
          this.idDep = result.get().getInt("dept_no");
          this.nombreDep = result.get().getString("dnombre");
          this.locDep = result.get().getString("loc");
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | update | actualizamos un departamento pero con resultset -->
  public void update (ResultSet rs) {
    try {
      rs.beforeFirst();
      
      while (rs.next()) {
        rs.updateString("dnombre", nombreDep);
        rs.updateString("loc", locDep);
        rs.updateRow();
      }
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | selectAll | devolvemos todos los departamentos que haya en la tabla -->
  public static Optional<ResultSet> selectAll (OperacionesBBDD bbdd) {
    Optional<ResultSet> result = null;
    
    try {
      result = bbdd.select("SELECT * FROM departamentos");
    } catch (SQLException ex) {
      System.out.println(" > Ha ocurrido un error: ");
      System.out.println("  - Mensaje: " + ex.getMessage());
      System.out.println("  - SQL Estado: " + ex.getSQLState());
      
      switch (ex.getErrorCode()) {
        case 942:
          System.out.println("  - Esta tabla no existe.");
        default:
          System.out.println("  - Error no identificado.");
      }
    }
    
    return result;
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getNombreDept | obtenemos el nombre de un departamento -->
  public static void getNombreDept (OperacionesBBDD bbdd, int inputNumDep) {
    String salidaReturn = "";
    Connection conexion = bbdd.getConexion();
    
    try {
      String sqlProcedimiento = "{call p_nombre_depart (?,?)}";
      CallableStatement llamada = conexion.prepareCall(sqlProcedimiento);
      
      llamada.setInt(1, inputNumDep);
      llamada.registerOutParameter(2, Types.VARCHAR);
      
      llamada.executeUpdate();
      salidaReturn = " > Departamento " + inputNumDep + ": " + llamada.getString(2);
    } catch (SQLException ex) {
      if (ex.getSQLState().equals("02000") || ex.getErrorCode() == 1403) {
        salidaReturn = " > El Departamento con numero " + inputNumDep + " no existe";
      }
    } finally {
      System.out.println(salidaReturn);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | mostrarResultSet | obtener informacion de un departamento con resultset -->
  public static void mostrarResultSet (Optional<ResultSet> result) throws SQLException {
    if (result.isPresent()) {
      while (result.get().next()) {
        System.out.println(" > Departamento " + result.get().getInt("dept_no") + " | Nombre: " + result.get().getString("dnombre") + ", Localidad: " + result.get().getString("loc"));
      }
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getNumDepartamentos | obtiene el numero de departamentos que existen -->
  public static int getNumDepartamentos (OperacionesBBDD bbdd) {
    int numDeparts = 0;
    
    try {
      Optional<ResultSet> result = bbdd.select("SELECT COUNT(*) AS total FROM departamentos");
      
      if (result.isPresent() && result.get().next()) {
        numDeparts = result.get().getInt("total");
      }
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return numDeparts;
  }
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // getters, setters & override -->
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

  public String getLocDep() {
    return locDep;
  }

  public void setLocDep(String locDep) {
    this.locDep = locDep;
  }

  @Override
  public String toString() {
    return "Departamento{" + "idDep=" + idDep + ", nombreDep=" + nombreDep + ", locDep=" + locDep + '}';
  }
}
