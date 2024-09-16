package com.jgc.mvc;

import com.jgc.mvc.controlador.ControlConversor;
import com.jgc.mvc.modelo.ConversorEurosPesetas;
import com.jgc.mvc.vista.InterfazVista;
import com.jgc.mvc.vista.VentanaConversor;
import com.jgc.mvc.vista.VentanaConversorTexto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 sept 2024
 */
public class Mvc {
  
  public static void main (String[] args) {
    InterfazVista vista = new VentanaConversor();
    ConversorEurosPesetas modelo = new ConversorEurosPesetas();
    ControlConversor control = new ControlConversor(vista, modelo);
  }
}
