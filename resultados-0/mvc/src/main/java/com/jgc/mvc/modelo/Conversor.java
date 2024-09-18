/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.mvc.modelo;

/**
 * clase encargada de la logica de negocio de la aplicacion
 * 
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 sept 2024
 */
public class Conversor {
  
  /**
   * cantidad de la moneda destino  a la cual equivale un euro
   */
  private final double cambio;

  public Conversor(double cambio) {
    this.cambio = cambio;
  }
  

  //-----------------------------------------------------------------------------
  
  /**
   * convierte los euros a una moneda cualquiera utilizando el cambio
   * 
   * @param cantidad cantidad de euros a convertir
   * @return cantidad equivalente a la moneda destino
   */
  public double eurosAmoneda (double cantidad, int comisionRaw) {
    double comision = (comisionRaw / 100.0);
    return (cantidad * cambio) - ((cantidad * cambio) * comision);
  }
  //-----------------------------------------------------------------------------
  
  /**
   * convierte los euros a una moneda cualquiera utilizando el cambio
   * 
   * @param cantidad cantidad de una moneda destino
   * @return cantidad equivalente en euros de la moneda destino
   */
  public double monedaAeuros(double cantidad, int comisionRaw) {
    double comision = (comisionRaw / 100.0);
    return (cantidad / cambio) - ((cantidad / cambio) * comision);
  }
}
