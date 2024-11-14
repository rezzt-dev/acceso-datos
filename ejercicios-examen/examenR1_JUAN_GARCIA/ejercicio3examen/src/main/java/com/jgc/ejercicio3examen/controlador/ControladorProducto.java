/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.ejercicio3examen.controlador;

import com.jgc.ejercicio3examen.modelo.ModeloProducto;
import com.jgc.ejercicio3examen.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 nov 2024
 */
public class ControladorProducto implements ActionListener {

  private final InterfazVista vista;
  private final ModeloProducto modelo;
  
  public ControladorProducto (InterfazVista inputVista, ModeloProducto inputModelo) {
    this.vista = inputVista;
    this.modelo = inputModelo;
      
    this.vista.setControlador(this);
  }

  

  public void actionPerformed(ActionEvent evento) {
    switch (evento.getActionCommand()) {
      case InterfazVista.AGREGAR_PRODUCTO -> {
        String nombreProduct = this.vista.getNombre();
        int cantidad = this.vista.getCantidad();
        String nombreSuper = this.vista.getNombreSuper();
        
        this.modelo.agregarDatos(nombreProduct, cantidad, nombreSuper);
      }
    }
  }
}
