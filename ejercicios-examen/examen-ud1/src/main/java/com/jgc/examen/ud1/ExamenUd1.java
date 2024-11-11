/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.examen.ud1;

import com.jgc.examen.ud1.controlador.ControlUniversidades;
import com.jgc.examen.ud1.modelo.FicheroUniversidad;
import com.jgc.examen.ud1.vista.InterfazVista;
import com.jgc.examen.ud1.vista.VentanaTexto;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class ExamenUd1 {
  public static void main(String[] args) {
    InterfazVista vista = new VentanaTexto();
    FicheroUniversidad modelo = new FicheroUniversidad();
    
    ControlUniversidades controlador = new ControlUniversidades(vista, modelo);
    vista.arranca();
  }
}
