/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.fileaccesoaleatorio;

import com.jgc.fileaccesoaleatorio.controlador.ControladorRegistros;
import com.jgc.fileaccesoaleatorio.modelo.Escritura;
import com.jgc.fileaccesoaleatorio.modelo.Lectura;
import com.jgc.fileaccesoaleatorio.vista.InterfazVista;
import com.jgc.fileaccesoaleatorio.vista.VentanaTexto;
import java.io.IOException;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class FileAccesoAleatorio {

  public static void main(String[] args) throws IOException {
    String rutaBase = "";
    
    InterfazVista vista = new VentanaTexto();
    Escritura modeloEs = new Escritura(rutaBase);
    Lectura modeloLec = new Lectura(rutaBase);
    ControladorRegistros controlador = new ControladorRegistros(vista, modeloEs, modeloLec);
    
    vista.arranca();
  }
}
