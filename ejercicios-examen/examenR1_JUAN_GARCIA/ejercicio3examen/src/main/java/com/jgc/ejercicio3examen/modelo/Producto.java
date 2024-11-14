/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.ejercicio3examen.modelo;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 nov 2024
 */
public class Producto {
  private String nombre;
  private int cantidad;
  private String nombreSuper;

  public Producto() {
  }

  public Producto(String nombre, int cantidad, String nombreSuper) {
    this.nombre = nombre;
    this.cantidad = cantidad;
    this.nombreSuper = nombreSuper;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public String getNombreSuper() {
    return nombreSuper;
  }

  public void setNombreSuper(String nombreSuper) {
    this.nombreSuper = nombreSuper;
  }

  @Override
  public String toString() {
    return "Producto{" + "nombre=" + nombre + ", cantidad=" + cantidad + ", nombreSuper=" + nombreSuper + '}';
  }
}
