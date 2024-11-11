/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.examen.ud1.modelo;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 11 nov 2024
 */
public class Universidad {
  private int id;
  private String carrera;
  private String ciudad;
  private double notaCorte;
  
  public Universidad () {}

  public Universidad(int id, String carrera, String ciudad, double notaCorte) {
    this.id = id;
    this.carrera = carrera;
    this.ciudad = ciudad;
    this.notaCorte = notaCorte;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCarrera() {
    return carrera;
  }

  public void setCarrera(String carrera) {
    this.carrera = carrera;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public double getNotaCorte() {
    return notaCorte;
  }

  public void setNotaCorte(double notaCorte) {
    this.notaCorte = notaCorte;
  }

  @Override
  public String toString() {
    return "Universidad{" + "id=" + id + ", carrera=" + carrera + ", ciudad=" + ciudad + ", notaCorte=" + notaCorte + '}';
  }
}
