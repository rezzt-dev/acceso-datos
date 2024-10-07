/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefilestreambytes.modelo;

import java.io.File;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 oct 2024
 */
public class Fichero {
 // constantes y atributos -->
  private File path;
  
 //----------------------------------------------------------------------------->
 // contructores -->
  public Fichero (String inputPath) {
    this.path = new File(inputPath);
  }
  
 //----------------------------------------------------------------------------->
 // metodos privados -->
 //----------------------------------------------------------------------------->
 // metodos publicos -->
  public boolean fileExist () {
    return this.path.exists();
  }

 //----------------------------------------------------------------------------->
 // getters & setters -->
  public String getPath () {
    return this.path.getAbsolutePath();
  }
  
  public void setPath (String inputPath) {
    this.path = new File(inputPath);
  }
}
