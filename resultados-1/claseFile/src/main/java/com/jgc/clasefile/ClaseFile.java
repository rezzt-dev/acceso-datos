/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.clasefile;

import com.jgc.clasefile.controlador.ControlCarpeta;
import com.jgc.clasefile.modelo.Carpeta;
import com.jgc.clasefile.vista.InterfazVista;
import com.jgc.clasefile.vista.VentanaCarpetaTexto;

/**
 * clase ejemplo para mostrar el funcionamiento de la clase File
 * 
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 18 sept 2024
 */
public class ClaseFile {
  public static void main(String[] args) {
    InterfazVista vista = new VentanaCarpetaTexto();
    Carpeta modelo = new Carpeta();
    ControlCarpeta control = new ControlCarpeta(vista, modelo);
  }
}
