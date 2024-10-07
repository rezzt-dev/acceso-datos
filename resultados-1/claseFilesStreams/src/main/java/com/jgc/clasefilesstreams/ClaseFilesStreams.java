/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.clasefilesstreams;

import com.jgc.clasefilesstreams.controlador.ControladorFileStreams;
import com.jgc.clasefilesstreams.modelo.FileStreamsModelo;
import com.jgc.clasefilesstreams.vista.InterfazVista;
import com.jgc.clasefilesstreams.vista.VentanaTexto;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class ClaseFilesStreams {
  public static void main(String[] args) {
    FileStreamsModelo modelo = new FileStreamsModelo();
    InterfazVista vista = new VentanaTexto();
    ControladorFileStreams controlador = new ControladorFileStreams(vista, modelo);
    
    vista.arranca();
  }
}
