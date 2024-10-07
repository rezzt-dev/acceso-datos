/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefilesstreams.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 30 sept 2024
 */
public class FileStreamsModelo {
 // constantes y atributos -->
  private static final String ALFABETO = "abcdefghijklmnopqrstuvwxyz";
  private static final String CIFRADO = "4bcd3fgh1jklmzopqp5@7vwxy6";
  
  private String path;
  
 //----------------------------------------------------------------------------->
 // contructores -->
  public FileStreamsModelo () {
  }
  
  public FileStreamsModelo (String inputPath) {
    this.path = inputPath;
  }
  
 //----------------------------------------------------------------------------->
 // metodos privados -->
  
  
 //----------------------------------------------------------------------------->
 // metodos publicos -->
   // metodo "leerFicheroCaracter" =>
  public StringBuffer leerFicheroCaracter () {
    StringBuffer outputContent = new StringBuffer();
  
    try {
      File inputFile = new File(this.path);
      FileReader readerIn = new FileReader(inputFile);
      int i = 0;
      
      while ((i = readerIn.read()) != -1) {
        outputContent.append((char) i);
      }
      
      readerIn.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return outputContent;
  }
  
   // metodo "leerFicheroCadena" =>
  public StringBuffer leerFicheroCadena () {
    StringBuffer outputContent = new StringBuffer();
    
    try {
      File inputFile = new File(this.path);
      FileReader readerIn = new FileReader(inputFile);
      
      char[] buffer = new char[1024];
      int i = 0;
      
      while ((i = readerIn.read(buffer)) != 1) {
        outputContent.append(buffer, 0, i);
      }
      
      readerIn.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return outputContent;
  }
   
   // metodo "leerFicheroLinea" =>
  public StringBuffer leerFicheroLinea () {
    StringBuffer outputContent = new StringBuffer();
    
    try {
      File inputFile = new File(this.path);
      BufferedReader bufferReader = new BufferedReader(new FileReader(inputFile));
      String line;
      
      while ((line = bufferReader.readLine()) != null) {
        outputContent.append(line);
        outputContent.append(System.lineSeparator());
      }
      
      bufferReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return outputContent;
  }
  
   // metodo "escribirFicheroCaracter" =>
  public void escribirFicheroCaracter (char inputChar) {
    try {
      File outputFile = new File(this.path);
      
      if (!outputFile.exists()) {
        outputFile.createNewFile();
      }
      
      FileWriter writerOut = new FileWriter(outputFile, true);
      writerOut.write(inputChar);
      writerOut.close();
        
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
   // metodo "escribirFicheroCadena" =>
  public void escribirFicheroCadena (String inputString) {
    try {
      File outputFile = new File(this.path);
      
      if (!outputFile.exists()) {
        outputFile.createNewFile();
      }
      
      FileWriter writerOut = new FileWriter(outputFile, true);
      
      for (int i=0; i < inputString.length(); i++) {
        writerOut.write(inputString.charAt(i));
      }
      
      writerOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
   // metodo "escribirFicheroLinea" =>
  public void escribirFicheroLinea (String inputLine) {
    try {
      File outputFile = new File(this.path);
      
      if (!outputFile.exists()) {
        outputFile.createNewFile();
      }
      
      FileWriter writerOut = new FileWriter(outputFile, true);
      
      writerOut.write(inputLine);
      writerOut.write(System.lineSeparator());
      
      writerOut.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
   // metodo "encriptarFichero" =>
  public void encriptarFichero (String inputPath) {
    this.path = inputPath;
    File inputFile = new File(inputPath);
    String parentPath = inputFile.getParent();
    
    String encriptContent = encriptarContenido(inputFile).toString();
    
    File encriptFile = new File(parentPath, "encripted.txt");
    this.path = encriptFile.getAbsolutePath();
    escribirFicheroLinea(encriptContent);
  }
  
   // metodo "desencriptarFichero" =>
  public void desencriptarFichero (String inputPath) {
    this.path = inputPath;
    File inputFile = new File(inputPath);
    
    String parentPath = inputFile.getParent();
    String disencriptContent = desencriptarContenido(inputFile).toString();
    
    File disencriptFile = new File(parentPath, "disencript.txt");
    this.path = disencriptFile.getAbsolutePath();
    escribirFicheroLinea(disencriptContent);
  }
  
   // metodo "encriptarContenido" =>
  public StringBuffer encriptarContenido (File inputFile) {
    this.path = inputFile.getAbsolutePath();
    
    StringBuffer rawContentBuffer = leerFicheroCaracter();
    StringBuffer encriptContent = new StringBuffer();
      
    String rawContent = rawContentBuffer.toString();
    rawContent = rawContent.toLowerCase();
      
    for (char c : rawContent.toCharArray()) {
      int indice = ALFABETO.indexOf(c);
        
      if (indice != -1) {
        encriptContent.append(CIFRADO.charAt(indice));
      } else {
        encriptContent.append(c);
      }
    }
    
    return encriptContent;
  }
  
   // metodo "desencriptarContenido" =>
  public StringBuffer desencriptarContenido (File inputFile) {
    this.path = inputFile.getAbsolutePath();
    
    StringBuffer encriptContentBuffer = leerFicheroCaracter();
    String encriptContent = encriptContentBuffer.toString();
    encriptContent = encriptContent.toLowerCase();
    
    StringBuffer disencriptContent = new StringBuffer();
    
    for (char c : encriptContent.toCharArray()) {
      int indice = CIFRADO.indexOf(c);
      
      if (indice != -1) {
        disencriptContent.append(ALFABETO.charAt(indice));
      } else {
        disencriptContent.append(c);
      }
    }
    
    return disencriptContent;
  }
  
   // metodo "getFileFromRuta" =>
  public File getFileFromRuta (String inputPath) {
    return new File(inputPath);
  }
  
 //----------------------------------------------------------------------------->
 // getters & setters -->
  public String getPath () {
    return this.path;
  }
  
  public void setPath (String inputPath) {
    this.path = inputPath;
  }
  
}
