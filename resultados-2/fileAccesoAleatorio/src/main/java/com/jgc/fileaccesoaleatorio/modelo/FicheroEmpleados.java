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
  final int CARACTERES_APELLIDO = 10;
  
   // constantes - tamaño componentes registro ->
  final int LONGITUD_IDENTIFICADOR = LONGITUD_LONG;
  final int LONGITUD_APELLIDO = (CARACTERES_APELLIDO * LONGITUD_CHAR);
  final int LONGITUD_DEPARTAMENTO = LONGITUD_INT;
  final int LONGITUD_SALARIO = LONGITUD_DOUBLE;
  
   // constantes - tamaño registro ->
  final int LONGITUD_TOTAL = (LONGITUD_IDENTIFICADOR + LONGITUD_APELLIDO + LONGITUD_DEPARTAMENTO + LONGITUD_SALARIO);
  
   // atributos ->
  private File path;
  
 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public FicheroEmpleados (String inputPath) {
    this.path = new File(inputPath);
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos privados =>
  
 //——————————————————————————————————————————————————————————————————————
 // metodos publicos =>
  
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
