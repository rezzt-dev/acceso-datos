/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefile.controlador;

import com.jgc.clasefile.modelo.Archivo;
import com.jgc.clasefile.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Sep 20, 2024
 */
public class ControladorArchivo implements ActionListener {
  private final InterfazVista vista;
  private final Archivo modelo;
  
  private String ruta;
  private String nombre;
  
  public ControladorArchivo (InterfazVista vistaInput, Archivo modeloInput) {
    this.vista = vistaInput;
    this.modelo = modeloInput;
    this.vista.setControladorArchivo (this);
  }
  
  //------------------------------------------------>
  @Override
  public void actionPerformed (ActionEvent evento) {
    switch (evento.getActionCommand()) {
      case InterfazVista.CREAR_ARCHIVO_CON_RUTA_Y_NOMBRE -> {
        this.ruta = this.vista.getRuta();
        this.nombre = this.vista.getNombre();
        
        this.modelo.crearArchivo(ruta, nombre);
        this.vista.operacionExitosa();
        this.vista.limpiarCampos();
      }
      
      case InterfazVista.RENOMBRAR_ARCHIVO_EXISTENTE -> {
        this.nombre = this.vista.getNombreBase();
        String nuevoNombre = this.vista.getNombre();
        
        this.modelo.renombrarArchivo(nombre, nuevoNombre);
        this.vista.operacionExitosa();
        this.vista.limpiarCampos();
      }
      
      case InterfazVista.COPIAR_ARCHIVO_NUEVA_RUTA -> {
        this.ruta = this.vista.getRuta();
        String nuevaRuta = this.vista.getNuevaRuta();
        
        this.modelo.copiarArchivo(ruta, nuevaRuta);
        this.vista.operacionExitosa();
        this.vista.limpiarCampos();
      }
      
      case InterfazVista.MOVER_ARCHIVO_NUEVA_RUTA -> {
        this.ruta = this.vista.getRuta();
        String nuevaRuta = this.vista.getNuevaRuta();
        
        this.modelo.moverArchivo(ruta, nuevaRuta);
        this.vista.operacionExitosa();
        this.vista.limpiarCampos();
      }
    }
  }
  
  //------------------------------------------------>
  //------------------------------------------------>
  //------------------------------------------------>

}
