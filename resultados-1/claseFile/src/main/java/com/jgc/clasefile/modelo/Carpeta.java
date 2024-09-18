/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefile.modelo;

import java.io.File;

/**
 * clase encargada de la logica de la aplicacion
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 18 sept 2024
 */
public class Carpeta {
  private String ruta;
  
 //----------------------------------------------------------------------|
  /**
   * constructor de la clase
   * @param ruta ruta de la carpeta
  */
  public Carpeta(String ruta) {
    this.ruta = ruta;
  }

  public Carpeta() {
  }
  
  
 //----------------------------------------------------------------------|
  /**
   * crea un directorio en la ruta indicada
   * utiliza el atributo de la clase
  */
  public void crearCarpeta() {
    File directorioNuevo = new File(this.ruta);
    directorioNuevo.mkdir();
  }
  
  
 //----------------------------------------------------------------------|
  // getters & setters ->
  public String getRuta() {
    return ruta;
  }

  public void setRuta(String ruta) {
    this.ruta = ruta;
  }
}
