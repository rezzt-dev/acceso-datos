/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.fileaccesoaleatorio.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 9 oct 2024
 */
public class Escritura extends FicheroEmpleados {
  public Escritura (String inputPath) {
    super(inputPath);
  }
  
  private void writeString (RandomAccessFile inputFile, String inputString, int inputLength) throws IOException {
    StringBuffer buffer = new StringBuffer(inputString);
    buffer.setLength(inputLength);
    inputFile.writeChars(buffer.toString());
  }
  
  public void escribirEmpleadoFinalArchivo (Empleado inputEmpleado) {
    RandomAccessFile randomFile = null;
    long pos = 0;
    StringBuffer buffer = null;
    
    try {
      randomFile = new RandomAccessFile(getPath(), "rw");
      
      if (randomFile.length() != 0) {
        pos = randomFile.length();
      }
      
      randomFile.seek(pos);
      randomFile.writeLong((pos/super.getLongitudTotal()) + 1);
      
      buffer = new StringBuffer(inputEmpleado.getApellido());
      buffer.setLength(super.getCaracteresApellido());
      randomFile.writeChars(buffer.toString());
      
      randomFile.writeInt(inputEmpleado.getDepartamento());
      randomFile.writeDouble(inputEmpleado.getSalario());
      
      
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        randomFile.close();
      } catch (IOException ex) {
        Logger.getLogger(Escritura.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
  public void modificarApellidoEmpleado(int inputId, String nuevoApellido) throws IOException {
    RandomAccessFile randomFile = null;
    StringBuffer buffer = null;
    
    try {
      randomFile = new RandomAccessFile(getPath(), "rw");
      while (randomFile.getFilePointer() < randomFile.length()) {
        long pos = randomFile.getFilePointer();
        long idActual = randomFile.readLong();
            
        if (idActual == inputId) {
          randomFile.seek(pos + LONGITUD_IDENTIFICADOR);
                
          buffer = new StringBuffer(nuevoApellido);
          buffer.setLength(super.getCaracteresApellido());
          randomFile.writeChars(buffer.toString());
        }
            
        randomFile.skipBytes(LONGITUD_TOTAL - LONGITUD_IDENTIFICADOR);
      }
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, "Archivo no encontrado", ex);
    } catch (IOException ex) {
      Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, "Error de E/S", ex);
    } finally {
      if (randomFile != null) {
        try {
          randomFile.close();
        } catch (IOException ex) {
          Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, "Error al cerrar el archivo", ex);
        }
      }
    }
  }
  
  public void almacenarRegistro (Empleado inputEmpleado) throws IOException {
    long inputId = inputEmpleado.getIdentificador();
    String inputApellido = inputEmpleado.getApellido();
    int inputDepartamento = inputEmpleado.getDepartamento();
    double inputSalario = inputEmpleado.getSalario();

    
    try (RandomAccessFile fileAccess = new RandomAccessFile(getPath(), "rw")) {
      fileAccess.seek(fileAccess.length());
      fileAccess.writeLong(inputId);
      writeString(fileAccess, inputApellido, CARACTERES_APELLIDO);
      fileAccess.writeInt(inputDepartamento);
      fileAccess.writeDouble(inputSalario);
    }
  }
  
  
  
  public void borradoLogico (int inputId) throws IOException {
    RandomAccessFile randomFile = null;
    StringBuffer buffer = null;
    
    try {
      randomFile = new RandomAccessFile(getPath(), "rw");
      
      while (randomFile.getFilePointer() < randomFile.length()) {
        long pos = randomFile.getFilePointer();
        long idActual = randomFile.readLong();
        
        if (idActual == inputId) {
          randomFile.seek(pos);
          randomFile.writeLong(0);
          return;
        }
      }
      
      randomFile.skipBytes(LONGITUD_TOTAL - LONGITUD_IDENTIFICADOR);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, "Archivo no encontrado", ex);
    } catch (IOException ex) {
      Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, "Error de E/S", ex);
    } finally {
      if (randomFile != null) {
        try {
          randomFile.close();
        } catch (IOException ex) {
          Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, "Error al cerrar el archivo", ex);
        }
      }
    } 
  }
}
