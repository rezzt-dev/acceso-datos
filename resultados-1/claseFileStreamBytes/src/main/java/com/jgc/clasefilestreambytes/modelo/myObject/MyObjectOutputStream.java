/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefilestreambytes.modelo.myObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 oct 2024
 */
public class MyObjectOutputStream extends ObjectOutputStream {
 // constantes y atributos -->
  
 //----------------------------------------------------------------------------->
 // contructores -->
  public MyObjectOutputStream (OutputStream out) throws IOException {
    super(out);
  }
  
  protected MyObjectOutputStream () throws IOException {
    reset();
  }
  
 //----------------------------------------------------------------------------->
 // metodos privados -->
  protected void writeStreamHeader () {
    
  }
  
 //----------------------------------------------------------------------------->
 // metodos publicos -->
 //----------------------------------------------------------------------------->
 // getters & setters -->
}
