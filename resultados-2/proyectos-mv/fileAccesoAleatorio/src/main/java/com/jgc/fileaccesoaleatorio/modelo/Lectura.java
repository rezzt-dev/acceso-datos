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
public class Lectura extends FicheroEmpleados {
  public Lectura (String inputPath) {
    super(inputPath);
  }
  
   // metodo "readString" | metodo auxliar para leer strings de longitud fija ->
  private String readString (RandomAccessFile inputFile, int inputLength) throws IOException {
    char[] charArray = new char[inputLength];
    
    for (int i = 0; i < inputLength; i++) {
      charArray[i] = inputFile.readChar();
    }
    
    return new String(charArray).trim();
  }
  
  public Empleado lecturaEmpleado (int inputId) {
    RandomAccessFile randomFile = null;
    int pos = 0;
    Empleado tempEmpleado = new Empleado();
    byte[] cadena = new byte[super.getLongitudApellido()];
    
    try {
      randomFile = new RandomAccessFile(getPath(), "r");
      pos = (inputId - 1) * super.getLongitudTotal();
      
      if (pos < randomFile.length()) {
        randomFile.seek(pos);
        tempEmpleado.setIdentificador(randomFile.readLong());
        
        randomFile.read(cadena);
        tempEmpleado.setApellido(new String(cadena));
        
        tempEmpleado.setDepartamento(randomFile.readInt());
        tempEmpleado.setSalario(randomFile.readDouble());
      }
      
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        randomFile.close();
        
      } catch (IOException ex) {
        Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    return tempEmpleado;
  }
  
  public void mostrarRegistros () throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(getPath(), "r")) {
      while (file.getFilePointer() < file.length()) {
        long identificador = file.readLong();
        String apellido = readString(file, CARACTERES_APELLIDO);
        int departamento = file.readInt();
        double salario = file.readDouble();
                
        if (identificador != 0) {
          System.out.printf("ID: %d, Apellido: %s, Departamento: %d, Salario: %.2f%n",
          identificador, apellido, departamento, salario);
        }
      }
    }
  }
}
