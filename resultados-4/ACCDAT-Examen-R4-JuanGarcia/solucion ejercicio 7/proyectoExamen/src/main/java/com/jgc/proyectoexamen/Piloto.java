/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.proyectoexamen;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 5 feb 2025
 */
public class Piloto {
  private int idPiloto;
  private int horasVueloPiloto;
  private int horasVueloAnual;

  public Piloto(int idPiloto, int horasVueloPiloto, int horasVueloAnual) {
    this.idPiloto = idPiloto;
    this.horasVueloPiloto = horasVueloPiloto;
    this.horasVueloAnual = horasVueloAnual;
  }

  public int getIdPiloto() {
    return idPiloto;
  }

  public void setIdPiloto(int idPiloto) {
    this.idPiloto = idPiloto;
  }

  public int getHorasVueloPiloto() {
    return horasVueloPiloto;
  }

  public void setHorasVueloPiloto(int horasVueloPiloto) {
    this.horasVueloPiloto = horasVueloPiloto;
  }

  public int getHorasVueloAnual() {
    return horasVueloAnual;
  }

  public void setHorasVueloAnual(int horasVueloAnual) {
    this.horasVueloAnual = horasVueloAnual;
  }
  
  
}
