/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.proyecto.conexion.oracle.modelo;


import com.jgc.proyecto.conexion.oracle.bbdd.OperacionesBBDD;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  
  public static void delete (OperacionesBBDD inputBBDD, int n_dept) throws SQLException {
    inputBBDD.delete("DELETE FROM departamentos WHERE dept_no = ?", n_dept);
  }
  
  public static Optional<ResultSet> selectAll (OperacionesBBDD inputBBDD) {
    Optional<ResultSet> result = null;
    
    try {
      result = inputBBDD.select("SELECT * FROM departamentos");
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
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
