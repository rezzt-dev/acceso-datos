/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.mvc.controlador;

import com.jgc.mvc.modelo.ConversorEurosPesetas;
import com.jgc.mvc.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 sept 2024
 */
public class ControlConversor implements ActionListener {
  private final InterfazVista vista;
  private final ConversorEurosPesetas modelo;
  
  
  public ControlConversor (InterfazVista vista, ConversorEurosPesetas modelo) {
    this.vista = vista;
    this.modelo = modelo;
    
    this.vista.setControlador(this);
    this.vista.arranca();
  }
  
  //----------------------------------------------------------------------|
  /**
   * Método que maneja los eventos de acción generados por los componentes de la interfaz de usuario.
   * Dependiendo de si la vista es gráfica o de consola, se ejecutan diferentes lógicas.
   * Realiza la conversión de euros a pesetas o viceversa según la opción seleccionada.
   *
   * @param evento el evento de acción generado por el usuario.
  */
  @Override
  public void actionPerformed (ActionEvent evento) {
    double cantidad = vista.getCantidad();
    int comision = vista.getComision();
      
    switch (evento.getActionCommand()) {
      case InterfazVista.AEUROS -> {
        vista.escribeCambio(cantidad + " pesetas son: " + modelo.pesetasAeuros(cantidad, comision) + " euros");
      }
      case InterfazVista.APESETAS -> {
        vista.escribeCambio(cantidad + " euros son: " + modelo.eurosApesetas(cantidad, comision) + " pesetas");
      }
        
      default -> vista.escribeCambio("ERROR! Conversión no realizada!");
    }
  }
  
  //----------------------------------------------------------------------|
  /**
   * Método para establecer el tipo de operación que se va a realizar.
   * Puede ser conversión de euros a pesetas o de pesetas a euros.
   *
   * @param operacion la operación a realizar, puede ser "AEUROS" o "APESETAS".
  */
}
