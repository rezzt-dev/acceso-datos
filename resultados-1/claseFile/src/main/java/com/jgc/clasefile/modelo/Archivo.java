/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefile.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jgc.clasefile.modelo.ModeloDirectorios;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Sep 20, 2024
 */
public class Archivo extends ModeloDirectorios {
  private FileInputStream inputStream = null;
  private FileOutputStream outputStream = null;
  
  private String ruta;
  private String nombre;
  
  public Archivo (String rutaInput, String nombreInput) {
    super(rutaInput);
    
    this.ruta = rutaInput;
    this.nombre = nombreInput;
  }
  
  public Archivo () {
    super();
  }
  
  //------------------------------------------------>
   // metodo crear archivo | indicar ruta y nombre =>
  public void crearArchivo (String rutaInput, String nombreInput) {
    File newFile = null;
    try {
      newFile = new File (rutaInput, nombreInput);
      newFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      newFile = null;
    }
  }
  
   // metodo renombrar archivo | indicar ruta, nombre y nuevo nombre =>
  public boolean renombrarArchivo(String nombreBaseInput, String nombreNuevoInput) {
    File archivoOriginal = new File(nombreBaseInput);
    if (archivoOriginal.exists()) {
      File archivoRenombrado = new File(archivoOriginal.getParent(), nombreNuevoInput);
      boolean exito = archivoOriginal.renameTo(archivoRenombrado);
      if (exito) {
        System.out.println("Archivo renombrado exitosamente.");
        return true;
      } else {
        System.out.println("No se pudo renombrar el archivo.");
        return false;
      }
    } else {
      System.out.println("El archivo original no existe.");
      return false;
    }
  }
  
   // metodo mover archivo | indicar ruta-origen & ruta-destino =>
  public void moverArchivo (String rutaOrigenInput, String rutaDestinoInput) {
    File archivoBase = new File (rutaOrigenInput);
    File archivoCopiado = new File (rutaDestinoInput);
    
    copiarArchivo (rutaOrigenInput, rutaDestinoInput);
    
    if (archivoCopiado.exists()) {
      if (archivoBase.delete()) {
        System.out.println("Archivo movido exitosamente.");
      } else {
        System.out.println("No se pudo eliminar el archivo de origen.");
      }
    } else {
      System.out.println("Error al mover el archivo: la copia ha fallado.");
    }
  }
  
   // metodo copiar archivo | indicar ruta-origen & ruta-destino =>
  public void copiarArchivo (String rutaOrigenInput, String rutaDestinoInput) {
    File archivoBase = new File (rutaOrigenInput);
    File archivoCopiado = new File (rutaDestinoInput);
    
    try (FileInputStream inputStream = new FileInputStream(archivoBase); FileOutputStream outputStream = new FileOutputStream(archivoCopiado)) {
      
      byte[] tempData = new byte[1024];
      int length;
      
      while ((length = inputStream.read(tempData)) > 0) {
        outputStream.write(tempData, 0, length);
      }
      
      System.out.println("Archivo copiado exitosamente.");
    } catch (IOException e) {
      System.out.println("Error al copiar el archivo: " + archivoBase.getName());
    }
  }
  
  //------------------------------------------------>
  @Override
  public String getRuta () {
    return this.ruta;
  }
  
  @Override
  public void setRuta (String rutaInput) {
    this.ruta = rutaInput;
  }
  
  public String getNombre () {
    return this.nombre;
  }
  
  public void setNombre (String nombreInput) {
    this.nombre = nombreInput;
  }
}
