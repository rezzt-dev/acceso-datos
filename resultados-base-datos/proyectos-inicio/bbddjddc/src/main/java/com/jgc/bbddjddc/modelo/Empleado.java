/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.bbddjddc.modelo;

import com.jgc.bbddjddc.bbdd.OperacionesBBDD;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 nov 2024
 */
public class Empleado {
  private OperacionesBBDD operacionesBBDD;
  
  private int numEmple;
  private String apellido;
  private String oficio;
  private int dir;
  private String fechaAlt;
  private double salario;
  private double comision;
  private int numDept;

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
  
  public void insert () {
    try {
      operacionesBBDD.insert("insert into Empleados values (?,?,?,?,?,?,?,?)", numEmple, apellido, oficio, dir, fechaAlt, salario, comision, numDept);
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public OperacionesBBDD getOperacionesBBDD() {
    return operacionesBBDD;
  }

  public void setOperacionesBBDD(OperacionesBBDD operacionesBBDD) {
    this.operacionesBBDD = operacionesBBDD;
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
