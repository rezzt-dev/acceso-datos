/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.fileaccesoaleatorio.modelo;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Oct 7, 2024
 */
public class Empleado {
 // atributos & constantes =>
  private long identificador;
  private String apellido;
  private int departamento;
  private double salario;

 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public Empleado () {
  }
  
  public Empleado (long inputId) {
    this.identificador = inputId;
  }
  
  public Empleado (long inputId, String inputApellido, int inputDepartamento, double inputSalario) {
    this.identificador = inputId;
    this.apellido = inputApellido;
    this.departamento = inputDepartamento;
    this.salario = inputSalario;
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos privados =>
 //——————————————————————————————————————————————————————————————————————
  // metodos publicos =>
  @Override
  public String toString () {
    return "Empleado{" + "Identificador: " + identificador + " | Apellido: " + apellido + " | Departamento: " + departamento + " | Salario: " + salario + '}';
  }
  
 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
   // getters ->
  public long getIdentificador () {
    return this.identificador;
  }
  
  public String getApellido () {
    return this.apellido;
  }
  
  public int getDepartamento () {
    return departamento;
  }
  
  public double getSalario () {
    return salario;
  }
   // setters ->
  public void setIdentificador (long inputId) {
    this.identificador = inputId;
  }
  
  public void setApellido (String inputApellido) {
    this.apellido = inputApellido;
  }
  
  public void setDepartamento (int inputDepartamento) {
    this.departamento = inputDepartamento;
  }
  
  public void setSalario (double inputSalario) {
    this.salario = inputSalario;
  }
}
