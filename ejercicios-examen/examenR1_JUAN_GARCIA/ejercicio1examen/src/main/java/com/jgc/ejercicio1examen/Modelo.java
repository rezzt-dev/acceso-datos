/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.ejercicio1examen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 nov 2024
 */
public class Modelo {
  public static void busquedaEjercicio1 (String nombreArchivo, String rutaPartida, String rutaCopia) {
    File folderRutaPartida = new File(rutaPartida);
    
    if (!folderRutaPartida.exists()) {
      folderRutaPartida.mkdir();
    }
    
    if (utilidadBuscarArchivo(folderRutaPartida, nombreArchivo)) {
      File ficheroBase = new File(rutaPartida, nombreArchivo);
      File ficheroCopia = new File(rutaCopia, nombreArchivo);
      
      try {
        FileInputStream fileInput = new FileInputStream(ficheroBase);
        FileOutputStream fileOutput = new FileOutputStream(ficheroCopia);
        
        byte[] tempData = new byte[1024];
        int length;
        
        while ((length = fileInput.read(tempData)) > 0) {
          fileOutput.write(tempData, 0, length);
        }
        
        System.out.println("archivo copiado");
      } catch (IOException ex) {
        System.out.println("No se ha copiado el archivo");
      }
    }
  }
  
  private static boolean utilidadBuscarArchivo (File rutaBuscar, String archivoBuscar) {
    boolean archivoEncontrado = false;
    
    
    if (rutaBuscar.isDirectory() && rutaBuscar.exists()) {
      String[] contenidoRuta = rutaBuscar.list();
      
      for (String contenidoRuta1 : contenidoRuta) {
        String tmpData = contenidoRuta1;
        if (tmpData.equals(archivoBuscar)) {
          archivoEncontrado = true;
        } else {
          if (new File(tmpData).isDirectory()) {
            utilidadBuscarArchivo(new File(tmpData), archivoBuscar);
          }
        }
      }
    }
    
    return archivoEncontrado;
  }
}
