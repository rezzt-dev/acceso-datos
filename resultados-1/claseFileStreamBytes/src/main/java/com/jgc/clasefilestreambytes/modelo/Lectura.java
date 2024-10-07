/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefilestreambytes.modelo;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 oct 2024
 */
public class Lectura extends Fichero {
 // constantes y atributos -->
  
 //----------------------------------------------------------------------------->
 // contructores -->
  public Lectura (String inputPath) {
    super(inputPath);
  }
  
 //----------------------------------------------------------------------------->
 // metodos privados -->
 //----------------------------------------------------------------------------->
 // metodos publicos -->
   // metodo "simpleDataRead" =>
  public String simpleDataRead () {
    DataInputStream inputData = null;
    StringBuffer dataText = new StringBuffer();
    
    try {
      inputData = new DataInputStream(new FileInputStream(getPath()));
      
      dataText.append(inputData.readByte());
      dataText.append(" ");
      
      dataText.append(inputData.readShort());
      dataText.append(" ");
      
      dataText.append(inputData.readInt());
      dataText.append(" ");
      
      dataText.append(inputData.readLong());
      dataText.append(" ");
      
      dataText.append(inputData.readFloat());
      dataText.append(" ");
      
      dataText.append(inputData.readDouble());
      dataText.append(" ");
      
      dataText.append(inputData.readBoolean());
      dataText.append(" ");
      
      dataText.append(inputData.readChar());
      dataText.append(" ");
      
      dataText.append(inputData.readUTF());
      dataText.append(" ");
      
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    } finally {
      try {
        inputData.close();
      } catch (IOException e) {
      }
    }
    
    return dataText.toString();
  }
  
   // metodo "simpleDataRead" =>
  public ArrayList<Object> readObjects() {
    ArrayList<Object> returnList = new ArrayList<>();
    
    try (FileInputStream fis = new FileInputStream(getPath());
      ObjectInputStream inputData = new ObjectInputStream(fis)) {
      Object tempObject = inputData.readObject();
      returnList.add(tempObject);
      inputData.close();
    } catch (EOFException e) {
      // Fin del archivo, no es un error en este caso
    } catch (ClassNotFoundException e) {
      System.err.println("Error: Clase no encontrada - " + e.getMessage());
    } catch (IOException e) {
      System.err.println("Error de E/S: " + e.getMessage());
    }
    
    return returnList;
}
  
 //----------------------------------------------------------------------------->
 // getters & setters -->
}
