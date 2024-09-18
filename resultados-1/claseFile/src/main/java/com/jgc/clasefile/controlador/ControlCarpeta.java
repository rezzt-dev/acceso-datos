/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefile.controlador;

import com.jgc.clasefile.modelo.Carpeta;
import com.jgc.clasefile.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * clase encargada de comunicar la vista con el modelo
 * como esta clase esta interesada en procesor un evento de accion entonces debe implementar la interfaz ActionListener
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 18 sept 2024
 */
public class ControlCarpeta implements ActionListener {
  private final InterfazVista vista;
  private final Carpeta modelo;
  
 //----------------------------------------------------------------------|
  /**
   * constrcutor de la clase
   * 
   * @param vista interfaz de la aplicacion
   * @param modelo logica de la aplicacion
  */
  public ControlCarpeta(InterfazVista vista, Carpeta modelo) {
    this.vista = vista;
    this.modelo = modelo;
    
    this.vista.setControlador(this);
    this.vista.arranca();
  }
  
  
 //----------------------------------------------------------------------|
  @Override
  public void actionPerformed(ActionEvent evento) {
    String ruta = vista.getRuta();
    modelo.setRuta(ruta);
    
    switch (evento.getActionCommand()) {
      case InterfazVista.CREAR_CARPETA_CON_RUTA_COMPLETA -> {
        modelo.crearCarpeta();
      }
    }
  }
  
  
 //----------------------------------------------------------------------|
  // getters & setters ->
  
}
