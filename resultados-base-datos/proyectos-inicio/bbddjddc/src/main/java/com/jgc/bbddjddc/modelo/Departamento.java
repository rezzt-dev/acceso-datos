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
public class Departamento {
  private OperacionesBBDD operacionesBBDD;
  
  private int idDep;
  private String nombreDep;
  private String localidadDep;

  public Departamento(int idDep, String nombreDep, String localidadDep) {
    this.idDep = idDep;
    this.nombreDep = nombreDep;
    this.localidadDep = localidadDep;
  }
  
  public void insert () {
    try {
      operacionesBBDD.insert("insert into Departamentos values (?,?,?)", idDep, nombreDep, localidadDep);
    } catch (SQLException ex) {
      Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
    }
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
