/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.filedomxmlbinary.modelo;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 16 oct 2024
 */
public class Empleado {
 // atributos & constantes =>
  private long identificador;
  private String apellido;
  private int departamento;
  private double salario;
  private String cargo;

 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public Empleado () {
  }
  
  public Empleado (long inputId) {
    this.identificador = inputId;
  }
  
  public Empleado (long inputId, String inputApellido, int inputDepartamento, double inputSalario, String inputCargo) {
    this.identificador = inputId;
    this.apellido = inputApellido;
    this.departamento = inputDepartamento;
    this.salario = inputSalario;
    this.cargo = inputCargo;
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos privados =>
 //——————————————————————————————————————————————————————————————————————
  // metodos publicos =>
  @Override
  public String toString () {
    return "Empleado{" + "Identificador: " + identificador + " | Apellido: " + apellido + " | Departamento: " + departamento + " | Salario: " + salario + " | Cargo: " + cargo + '}';
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
  
  public String getCargo () {
    return cargo;
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
  
  public void setCargo (String inputCargo) {
    this.cargo = inputCargo;
  }
}
