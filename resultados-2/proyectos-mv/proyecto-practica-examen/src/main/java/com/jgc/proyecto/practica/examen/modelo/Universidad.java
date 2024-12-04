/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyecto.practica.examen.modelo;

/**
 *
 * @author rezzt
 */
public class Universidad {
 // constantes & variables ->
   // atributos universidad ->
  private long idUni;
  private String nombreCarrera;
  private String ciudadCarrera;
  private double notaCorte;
  
  public Universidad () {}

  public Universidad(long idUni, String nombreCarrera, String ciudadCarrera, double notaCorte) {
    this.idUni = idUni;
    this.nombreCarrera = nombreCarrera;
    this.ciudadCarrera = ciudadCarrera;
    this.notaCorte = notaCorte;
  }

  public long getIdUni() {
    return idUni;
  }

  public void setIdUni(long idUni) {
    this.idUni = idUni;
  }

  public String getNombreCarrera() {
    return nombreCarrera;
  }

  public void setNombreCarrera(String nombreCarrera) {
    this.nombreCarrera = nombreCarrera;
  }

  public String getCiudadCarrera() {
    return ciudadCarrera;
  }

  public void setCiudadCarrera(String ciudadCarrera) {
    this.ciudadCarrera = ciudadCarrera;
  }

  public double getNotaCorte() {
    return notaCorte;
  }

  public void setNotaCorte(double notaCorte) {
    this.notaCorte = notaCorte;
  }
}
