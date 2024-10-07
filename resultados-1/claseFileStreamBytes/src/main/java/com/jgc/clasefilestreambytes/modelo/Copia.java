/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefilestreambytes.modelo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 oct 2024
 */
public class Copia extends Fichero {
 // constantes y atributos -->
  private File outputPath;
  
 //----------------------------------------------------------------------------->
 // contructores -->
  public Copia (String inputPath, String outputPath) {
    super(inputPath);
    this.outputPath = new File(outputPath);
  }
  
 //----------------------------------------------------------------------------->
 // metodos privados -->
 //----------------------------------------------------------------------------->
 // metodos publicos -->
   // metodo "copyFile" =>
  public void copyFile () {
    DataInputStream inputData = null;
    DataOutputStream outputData = null;
    
    try {
      int length = 0;
      byte[] tempData = new byte[1024];
      
      inputData = new DataInputStream(new FileInputStream(getPath()));
      outputData = new DataOutputStream(new FileOutputStream(this.outputPath));
      
      while ((length = inputData.read(tempData)) > 0) {
        outputData.write(tempData, 0, length);
      }
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        inputData.close();
        outputData.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

 //----------------------------------------------------------------------------->
 // getters & setters -->
}
