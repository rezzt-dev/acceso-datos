/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.proyecto.practica.examen;

import com.jgc.proyecto.practica.examen.controlador.ControlUniversidades;
import com.jgc.proyecto.practica.examen.modelo.FicheroUniversidades;
import com.jgc.proyecto.practica.examen.modelo.Universidad;
import com.jgc.proyecto.practica.examen.vista.InterfazVista;
import com.jgc.proyecto.practica.examen.vista.VentanaTexto;

/**
 *
 * @author rezzt
 */
public class ProyectoPracticaExamen {
  public static void main(String[] args) {
    InterfazVista vista = new VentanaTexto();
    Universidad modeloUni = new Universidad();
    FicheroUniversidades modeloFichero = new FicheroUniversidades("");
    
    ControlUniversidades controlUnis = new ControlUniversidades(vista, modeloUni, modeloFichero);
    vista.arranca();
  }
}
