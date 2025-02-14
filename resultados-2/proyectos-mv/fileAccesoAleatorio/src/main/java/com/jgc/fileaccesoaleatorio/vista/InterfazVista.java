/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.jgc.fileaccesoaleatorio.vista;

import com.jgc.fileaccesoaleatorio.controlador.ControladorRegistros;
import com.jgc.fileaccesoaleatorio.modelo.Escritura;
import com.jgc.fileaccesoaleatorio.modelo.Lectura;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on Oct 7, 2024
 */
public interface InterfazVista {
 // atributos & constantes =>
  static final String LEER_EMPLEADO = "lee un empleado";
  static final String MOSTRAR_REGISTROS = "muestra los registros guardados";
  
  static final String ESCRIBIR_EMPLEADO_FINAL_ARCHIVO = "escribe un empleado al final del archivo";
  static final String MODIFICAR_APELLIDO_EMPLEADO = "modifica el apellido del empleado";
  static final String ALMACENAR_REGISTRO = "guarda el registro en el archivo";
  static final String BORRADO_LOGICO = "realiza un borrado logico de los registros";
  
 //——————————————————————————————————————————————————————————————————————
 // metodos abstractos =>
  void setControladorRegistros (ControladorRegistros controlRegistros);
  void arranca();
  void operacionExitosa();
  void escribeResultado(String cadenaTexto);

 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
  String getRuta ();
  int getIdentificador();
  String getApellido();
  int getDepartamento();
  double getSalario();
}
