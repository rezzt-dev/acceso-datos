/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.ejercicio3examen;

import com.jgc.ejercicio3examen.controlador.ControladorProducto;
import com.jgc.ejercicio3examen.modelo.ModeloProducto;
import com.jgc.ejercicio3examen.vista.InterfazVista;
import com.jgc.ejercicio3examen.vista.VentanaTexto;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class Ejercicio3examen {
  public static void main(String[] args) {
    InterfazVista vista = new VentanaTexto();
    ModeloProducto modelo = new ModeloProducto();
    
    ControladorProducto controlProduct = new ControladorProducto(vista, modelo);
    vista.arranca();
  }
}
