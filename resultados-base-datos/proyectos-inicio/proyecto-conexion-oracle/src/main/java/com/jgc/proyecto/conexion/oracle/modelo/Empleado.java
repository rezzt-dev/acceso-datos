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
public class Empleado {
  private int numEmple;
  private String apellido;
  private String oficio;
  private int dir;
  private String fechaAlt;
  private double salario;
  private double comision;
  private int numDept;
  
  public Empleado () {}

  public Empleado(int numEmple, String apellido, String oficio, int dir, String fechaAlt, double salario, double comision, int numDept) {
    this.numEmple = numEmple;
    this.apellido = apellido;
    this.oficio = oficio;
    this.dir = dir;
    this.fechaAlt = fechaAlt;
    this.salario = salario;
    this.comision = comision;
    this.numDept = numDept;
  }
  
  public void insert (OperacionesBBDD inputBBDD) {
    try {
      inputBBDD.insert("insert into Empleados values (?,?,?,?,?,?,?,?)", numEmple, apellido, oficio, dir, fechaAlt, salario, comision, numDept);
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void selectById (OperacionesBBDD inputBBDD, int n_emp) {
    try {
      Optional<ResultSet> result = inputBBDD.select("SELECT * FROM empleados WHERE emp_no = ?", n_emp);
      
      if (result.isPresent()) {
        while(result.get().next()) {
          this.numEmple = result.get().getInt("emp_no");
          this.apellido = result.get().getString("apellido");
          this.oficio = result.get().getString("oficio");
          this.dir = result.get().getInt("dir");
          this.fechaAlt = result.get().getString("fecha_alt");
          this.salario = result.get().getDouble("salario");
          this.comision = result.get().getDouble("comision");
          this.numDept = result.get().getInt("dept_no");
        }
      }
      
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void update (OperacionesBBDD inputBBDD) throws SQLException {
    inputBBDD.update("UPDATE departamentos set apellido = ?, oficio = ?, dir = ?, fecha_alt = ?, salario = ?, comision = ?, dept_no = ? WHERE emp_no = ?", 
            this.apellido, this.oficio, this.dir, this.fechaAlt, this.salario, this.comision, this.numDept, this.numEmple);
  }
  
  public static void delete (OperacionesBBDD inputBBDD, int emp_no) throws SQLException {
    inputBBDD.delete("DELETE FROM empleados WHERE emp_no = ?", emp_no);
  }
  
  public static Optional<ResultSet> selectAll (OperacionesBBDD inputBBDD) {
    Optional<ResultSet> result = null;
    
    try {
      result = inputBBDD.select("SELECT * FROM empleados");
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return result;
  }
  
  public static void mostrarResultSet (Optional<ResultSet> result) throws SQLException {
    if (result.isPresent()) {
      while(result.get().next()) {
        System.out.println("Num Empleado: " + result.get().getInt("emp_no") + " | Apellido: " + result.get().getString("apellido") + 
                " | Oficio: " + result.get().getString("oficio") + " | Direccion: " + result.get().getInt("dir") +
                " | Fecha de Alta: " + result.get().getString("fecha_alt") + " | Salario: " + result.get().getDouble("salario") + 
                " | Comision: " + result.get().getDouble("comision") + " | Numero Departamento: " + result.get().getInt("dept_no"));
      }
    }
  }

  @Override
  public String toString() {
    return "Empleado{" + "numEmple=" + numEmple + ", apellido=" + apellido + ", oficio=" + oficio + ", dir=" + dir + ", fechaAlt=" + fechaAlt + ", salario=" + salario + ", comision=" + comision + ", numDept=" + numDept + '}';
  }

  public int getNumEmple() {
    return numEmple;
  }

  public void setNumEmple(int numEmple) {
    this.numEmple = numEmple;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getOficio() {
    return oficio;
  }

  public void setOficio(String oficio) {
    this.oficio = oficio;
  }

  public int getDir() {
    return dir;
  }

  public void setDir(int dir) {
    this.dir = dir;
  }

  public String getFechaAlt() {
    return fechaAlt;
  }

  public void setFechaAlt(String fechaAlt) {
    this.fechaAlt = fechaAlt;
  }

  public double getSalario() {
    return salario;
  }

  public void setSalario(double salario) {
    this.salario = salario;
  }

  public double getComision() {
    return comision;
  }

  public void setComision(double comision) {
    this.comision = comision;
  }

  public int getNumDept() {
    return numDept;
  }

  public void setNumDept(int numDept) {
    this.numDept = numDept;
  }
}
