/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.fileaccesoaleatorio.controlador;

import com.jgc.fileaccesoaleatorio.modelo.Empleado;
import com.jgc.fileaccesoaleatorio.modelo.Escritura;
import com.jgc.fileaccesoaleatorio.modelo.Lectura;
import com.jgc.fileaccesoaleatorio.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Oct 7, 2024
 */
public class ControladorRegistros implements ActionListener {
 // atributos & constantes =>
  private final InterfazVista vista;
  private final Escritura modeloEs;
  private final Lectura modeloLec;
  
  private String filePath;
  private int idEmpleado;
  private Empleado tempEmpleado;

 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public ControladorRegistros (InterfazVista inputVista, Escritura inputModeloEs, Lectura inputModeloLec) {
    this.vista = inputVista;
    this.modeloEs = inputModeloEs;
    this.modeloLec = inputModeloLec;
    
    this.vista.setControladorRegistros(this);
  }

 //——————————————————————————————————————————————————————————————————————
 // metodos privados =>
 //——————————————————————————————————————————————————————————————————————
 // metodos publicos =>
  @Override
  public void actionPerformed (ActionEvent evento) {
    switch (evento.getActionCommand()) {
      case InterfazVista.LEER_EMPLEADO -> {
        this.filePath = this.vista.getRuta();
        this.idEmpleado = this.vista.getIdentificador();
        
        this.tempEmpleado = this.modeloLec.lecturaEmpleado(this.idEmpleado);
        this.tempEmpleado.toString();
      }
      
      case InterfazVista.MOSTRAR_REGISTROS -> {
        this.filePath = this.vista.getRuta();
        this.modeloLec.setPath(filePath);
        
        try {
          this.modeloLec.mostrarRegistros();
          this.vista.operacionExitosa();
        } catch (IOException ex) {
          Logger.getLogger(ControladorRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      case InterfazVista.ESCRIBIR_EMPLEADO_FINAL_ARCHIVO -> {
        this.filePath = this.vista.getRuta();
        this.modeloEs.setPath(filePath);
        
        long tempId = this.vista.getIdentificador();
        String tempApellido = this.vista.getApellido();
        int tempDepartamento = this.vista.getDepartamento();
        double tempSalario = this.vista.getSalario();
        
        this.tempEmpleado = new Empleado (tempId, tempApellido, tempDepartamento, tempSalario);
        this.modeloEs.escribirEmpleadoFinalArchivo(tempEmpleado);
        this.vista.operacionExitosa();
      }
      
      case InterfazVista.MODIFICAR_APELLIDO_EMPLEADO -> {
        this.filePath = this.vista.getRuta();
        this.modeloLec.setPath(filePath);
        
        this.idEmpleado = this.vista.getIdentificador();
        String nuevoApellido = this.vista.getApellido();
        
        try {
          this.modeloEs.modificarApellidoEmpleado(idEmpleado, nuevoApellido);
          this.vista.operacionExitosa();
        } catch (IOException ex) {
          Logger.getLogger(ControladorRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      case InterfazVista.ALMACENAR_REGISTRO -> {
        this.filePath = this.vista.getRuta();
        this.modeloEs.setPath(filePath);
        
        long tempId = this.vista.getIdentificador();
        String tempApellido = this.vista.getApellido();
        int tempDepartamento = this.vista.getDepartamento();
        double tempSalario = this.vista.getSalario();
        
        this.tempEmpleado = new Empleado (tempId, tempApellido, tempDepartamento, tempSalario);
        try {
          this.modeloEs.almacenarRegistro(tempEmpleado);
          this.vista.operacionExitosa();
        } catch (IOException ex) {
          Logger.getLogger(ControladorRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      case InterfazVista.BORRADO_LOGICO -> {
        this.filePath = this.vista.getRuta();
        this.modeloEs.setPath(filePath);
        
        this.idEmpleado = this.vista.getIdentificador();
        try {
          this.modeloEs.borradoLogico(idEmpleado);
          this.vista.operacionExitosa();
        } catch (IOException ex) {
          Logger.getLogger(ControladorRegistros.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }

 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
}
