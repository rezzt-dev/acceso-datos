/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.mvc.modelo;

/**
 * clase para utilizar el conversor en euros a pesetas
 * 
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 sept 2024
 */
public class ConversorEurosPesetas extends Conversor {
  
  public ConversorEurosPesetas () {
    super(166.386D);
  }
  //-----------------------------------------------------------------------------
  
  /**
   * convierte una cantidad de euros a pesetas
   * 
   * @param cantidad cantidad de euros para convertir a pesetas
   * @return cantidad de pesetas
   */
  public double eurosApesetas (double cantidad, int comision) {
    return eurosAmoneda(cantidad, comision);
  }
  //-----------------------------------------------------------------------------
  
  /**
   * convierte una cantidad de euros a pesetas
   * 
   * @param cantidad cantidad de pesetas para convertir a euros
   * @return cantidad de euros
   */
  public double pesetasAeuros (double cantidad, int comision) {
    return monedaAeuros(cantidad, comision);
  }
}
