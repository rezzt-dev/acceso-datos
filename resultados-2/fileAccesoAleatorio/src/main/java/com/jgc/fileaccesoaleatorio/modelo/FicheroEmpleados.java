/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.fileaccesoaleatorio.modelo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Oct 7, 2024
 */
public class FicheroEmpleados {
 // atributos & constantes =>
   // constantes - tamaño datos ->
  private final int LONGITUD_LONG = 8;
  private final int LONGITUD_DOUBLE = 8;
  private final int LONGITUD_INT = 4;
  private final int LONGITUD_CHAR = 2;
  
   // constantes - tamaño apellido ->
  private final int CARACTERES_APELLIDO = 10;
  
   // constantes - tamaño componentes registro ->
  private final int LONGITUD_IDENTIFICADOR = LONGITUD_LONG;
  private final int LONGITUD_APELLIDO = (CARACTERES_APELLIDO * LONGITUD_CHAR);
  private final int LONGITUD_DEPARTAMENTO = LONGITUD_INT;
  private final int LONGITUD_SALARIO = LONGITUD_DOUBLE;
  
   // constantes - tamaño registro ->
  private final int LONGITUD_TOTAL = (LONGITUD_IDENTIFICADOR + LONGITUD_APELLIDO + LONGITUD_DEPARTAMENTO + LONGITUD_SALARIO);
  
   // atributos ->
  private File path;
  
 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public FicheroEmpleados (String inputPath) {
    this.path = new File(inputPath);
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos privados =>
   // metodo "writeString" | metodo auxliar para escribir strings de longitud fija ->
  private void writeString (RandomAccessFile inputFile, String inputString, int inputLength) throws IOException {
    StringBuffer buffer = new StringBuffer(inputString);
    buffer.setLength(inputLength);
    inputFile.writeChars(buffer.toString());
  }
  
   // metodo "readString" | metodo auxliar para leer strings de longitud fija ->
  private String readString (RandomAccessFile inputFile, int inputLength) throws IOException {
    char[] charArray = new char[inputLength];
    
    for (int i = 0; i < inputLength; i++) {
      charArray[i] = inputFile.readChar();
    }
    
    return new String(charArray).trim();
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos publicos =>
   // metodo "almacenarRegistro" | almacenar la informacion de un registro ->
  public void almacenarRegistro (long inputId, String inputApellido, int inputDepartamento, double inputSalario) throws IOException {
    try (RandomAccessFile fileAccess = new RandomAccessFile(path, "rw")) {
      fileAccess.seek(fileAccess.length());
      fileAccess.writeLong(inputId);
      writeString(fileAccess, inputApellido, CARACTERES_APELLIDO);
      fileAccess.writeInt(inputDepartamento);
      fileAccess.writeDouble(inputSalario);
    }
  }
  
   // metodo "borradorLogico" | realizar un borrado logico ->
  public void borradorLogico (long inputId) throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(path, "rw")) {
      while (file.getFilePointer() < file.length()) {
        long posicion = file.getFilePointer();
        long idActual = file.readLong();
        
        if (idActual == inputId) {
          file.seek(posicion);
          file.writeLong(0);
          return;
        }
        file.skipBytes(LONGITUD_TOTAL - LONGITUD_IDENTIFICADOR);
      }
    }
  }
  
   // metodo "mostrarRegistros" | mostrar todos los registros ->
  public void mostrarRegistros () throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(path, "r")) {
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
  
   // metodo "modificarApellido" |  ->
  public boolean modificarApellido (long inputId, String nuevoApellido) throws IOException {
    try (RandomAccessFile file = new RandomAccessFile(path, "rw")) {
      while (file.getFilePointer() < file.length()) {
        long posicion = file.getFilePointer();
        long idActual = file.readLong();
        
        if (idActual == inputId) {
          file.seek(posicion + LONGITUD_IDENTIFICADOR);
          writeString(file, nuevoApellido, CARACTERES_APELLIDO);
          return true;
        }
        file.skipBytes(LONGITUD_TOTAL - LONGITUD_IDENTIFICADOR);
      }
    }
    return false;
  }
  
 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
   // getters ->
  public int getCaracteresApellido () {
    return CARACTERES_APELLIDO;
  }
  
  public int getLongitudApellido () {
    return LONGITUD_APELLIDO;
  }
  
  public int getLongitudTotal () {
    return LONGITUD_TOTAL;
  }
  
  public File getPath () {
    return this.path;
  }
  
   // setters ->
  public void setPath (String inputPath) {
    this.path = new File(inputPath);
  }
}
