/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.clasefile;

import com.jgc.clasefile.controlador.ControladorArchivo;
import com.jgc.clasefile.controlador.ControladorCarpeta;
import com.jgc.clasefile.modelo.Archivo;
import com.jgc.clasefile.modelo.Carpeta;
import com.jgc.clasefile.vista.InterfazVista;
import com.jgc.clasefile.vista.VentanaGrafica;
import com.jgc.clasefile.vista.VentanaTexto;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class ClaseFile {
  public static void main(String[] args) {
    InterfazVista vista = new VentanaGrafica ();
    Archivo modeloArchivo = new Archivo ();
    Carpeta modeloCarpeta = new Carpeta ();
    ControladorArchivo controladorArchivo = new ControladorArchivo(vista, modeloArchivo);
    ControladorCarpeta controladorCarpeta = new ControladorCarpeta(vista, modeloCarpeta);
    
    vista.arranca();
  }
}