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
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
  public void crearArchivo(String rutaInput, String nombreInput) {
    Path directorio = Paths.get(rutaInput);
    Path archivo = directorio.resolve(nombreInput);
    
    try {
        // Crear los directorios si no existen
        if (Files.notExists(directorio)) {
            Files.createDirectories(directorio);
        }
        // Crear el archivo
        if (Files.notExists(archivo)) {
            Files.createFile(archivo);
            System.out.println("Archivo creado exitosamente en: " + archivo.toString());
        } else {
            System.out.println("El archivo ya existe.");
        }
    } catch (IOException e) {
        System.out.println("Error al crear el archivo: " + e.getMessage());
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
  public void moverArchivo(String rutaOrigenInput, String rutaDestinoInput) throws IOException {
    Path origen = Paths.get(rutaOrigenInput);
    Path destino = Paths.get(rutaDestinoInput);
    
    Files.move(origen, destino, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("Archivo movido exitosamente a: " + destino.toString());
  }
  
   // metodo copiar archivo | indicar ruta-origen & ruta-destino =>
  public void copiarArchivo(String rutaOrigenInput, String rutaDestinoInput) throws IOException {
    Path origen = Paths.get(rutaOrigenInput);
    Path destino = Paths.get(rutaDestinoInput);
    
    Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("Archivo copiado exitosamente a: " + destino.toString());
  }
  
   // Nuevo método para eliminar archivo o directorio =>
  public void eliminarArchivoODirectorio(String rutaInput, String nombreInput) {
    Path ruta = Paths.get(rutaInput, nombreInput);
        
    try {
      if (Files.isDirectory(ruta)) {
         // Si es un directorio, eliminar los archivos dentro
        Files.list(ruta).forEach(archivo -> {
          try {
            Files.delete(archivo);
          } catch (IOException e) {
            System.out.println("Error al eliminar el archivo: " + archivo.toString() + ". " + e.getMessage());
          }
        });
       // Eliminar el directorio después de vaciarlo
      Files.delete(ruta);
      System.out.println("Directorio y sus archivos eliminados exitosamente.");
      } else if (Files.isRegularFile(ruta)) {
         // Si es un archivo, eliminarlo directamente
        Files.delete(ruta);
        System.out.println("Archivo eliminado exitosamente.");
      } else {
        System.out.println("La ruta especificada no existe.");
      }
    } catch (IOException e) {
      System.out.println("Error al eliminar: " + e.getMessage());
    }
  }
  
   // Nuevo método para renombrar archivo usando NIO =>
  public void renombrarArchivoNIO(String rutaInput, String nombreActual, String nombreNuevo) {
    Path archivoActual = Paths.get(rutaInput, nombreActual);
    Path archivoNuevo = archivoActual.resolveSibling(nombreNuevo); // Ruta del nuevo archivo

    try {
      Files.move(archivoActual, archivoNuevo);
      System.out.println("Archivo renombrado exitosamente de " + nombreActual + " a " + nombreNuevo);
    } catch (NoSuchFileException e) {
      System.out.println("El archivo no existe: " + archivoActual.toString());
    }  catch (FileAlreadyExistsException e) {
      System.out.println("Ya existe un archivo con el nombre: " + nombreNuevo);
    } catch (IOException e) {
      System.out.println("Error al renombrar el archivo: " + e.getMessage());
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
