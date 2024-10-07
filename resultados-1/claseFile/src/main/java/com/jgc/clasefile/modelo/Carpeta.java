/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefile.modelo;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Sep 20, 2024
 */
public class Carpeta extends ModeloDirectorios {
  private String ruta;
  
  public Carpeta (String rutaInput) {
    super(rutaInput);
    this.ruta = rutaInput;
  }
  
  public Carpeta () {
    super();
  }
  
  //------------------------------------------------>
   // metodos crear carpeta | todas las posibildades =>
  public void crearCarpeta () {
    File newDirectory = new File (this.ruta);
    newDirectory.mkdir();
  }
  
  public void crearCarpeta (String newFolderName) {
    File newDirectory = new File (this.ruta, newFolderName);
    newDirectory.mkdir();
  }
  
  public void crearCarpeta (File mainFolder, String newFolderName) {
    File newDirectory = new File (mainFolder, newFolderName);
    newDirectory.mkdir();
  }
  
   // metodo "dir" carpeta | extraer informacio contenido =>
  public ArrayList<String> customDir (File folderPath) {
    ArrayList<String> informacion = new ArrayList<>();
    
    if (folderPath.isDirectory()) {
      informacion.add("Contenido Directorio: ");
      String[] infoRaw = folderPath.list();
      
      for (int i=0; i<infoRaw.length; i++) {
        File content = new File(infoRaw[i]);
        
        String fileName = content.getName();
        informacion.add("Contenido " + (i+1) + ": " + fileName);
      }
    } else if (folderPath.isFile()) {
      informacion.add("Informacion Fichero: ");
      
      String fileName = folderPath.getName();
      String fileSize = Long.toString(folderPath.length());
      informacion.add("Nombre: " + fileName + " | Tamaño: " + fileSize);
    }
    
    return informacion;
  }
  
   // metodo "borrar" | borra fichero o todos los fichero de una carpeta =>
  public void customDelete (File folderPath) {
    if (folderPath.isFile()) {
      folderPath.delete();
      System.out.println("Fichero: " + folderPath.getName() + " borrado con exito.");
    } else if (folderPath.isDirectory()) {
      String[] internalInfo = folderPath.list();
      
      for (int i=0; i<internalInfo.length; i++) {
        File tempData = new File(folderPath, internalInfo[i]);
        
        if (!tempData.isDirectory()) {
          if (tempData.delete()) {
            System.out.println("Fichero: " + tempData.getName() + " borrado con exito.");
          } else {
            System.out.println("No se pudo borrar el archivo " + tempData.getName());
          }
        }
      }
      
      if (folderPath.delete()) {
        System.out.println("Directorio: " + folderPath.getName() + " borrado con exito.");
      } else {
        System.out.println("No se pudo borrar el directorio " + folderPath.getName());
      }
      
      System.out.println("Archivos borrados con exito.");
    }
  }
  
   // metodo "borrar recursivo" | borra todo dentro de una carpeta =>
  public void customDeleteRecursive (File folderPath) {
    if (folderPath.isFile()) {
      folderPath.delete();
      System.out.println("Fichero: " + folderPath.getName() + " borrado con exito.");
    } else if (folderPath.isDirectory()) {
      String[] internalInfo = folderPath.list();
      
      for (int i=0; i < internalInfo.length; i++) {
        File tempData = new File(folderPath, internalInfo[i]);
        
        if (!tempData.isDirectory()) {
          if (tempData.delete()) {
            System.out.println("Fichero: " + tempData.getName() + " borrado con exito.");
          } else {
            System.out.println("No se pudo borrar el archivo " + tempData.getName());
          }
        } else {
          customDeleteRecursive(tempData);
        }
      }
      
      if (folderPath.delete()) {
        System.out.println("Directorio: " + folderPath.getName() + " borrado con exito.");
      } else {
        System.out.println("No se pudo borrar el directorio " + folderPath.getName());
      }
      
      System.out.println("Archivos borrados con exito.");
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
}
