/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyecto.practica.examen.controlador;

import com.jgc.proyecto.practica.examen.modelo.FicheroUniversidades;
import com.jgc.proyecto.practica.examen.modelo.Universidad;
import com.jgc.proyecto.practica.examen.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author rezzt
 */
public class ControlUniversidades implements ActionListener {
  private final InterfazVista vista;
  private final FicheroUniversidades modeloFichero;
  private final Universidad modeloUniversidad;
  
  private String filePath;
  private int idUni;
  private Universidad tempUniversidad;
  
  public ControlUniversidades (InterfazVista inputVista, Universidad inputModeloUni, FicheroUniversidades inputModeloFichero) {
    this.vista = inputVista;
    this.modeloUniversidad = inputModeloUni;
    this.modeloFichero = inputModeloFichero;
    
    this.vista.setControladorUniversidades(this);
  }
  
  @Override
  public void actionPerformed (ActionEvent evento) {
    switch (evento.getActionCommand()) {
      case InterfazVista.CREAR_ARBOL_DIRECTORIOS -> {
        this.modeloFichero.crearCarpeta("ORIGEN");
        this.modeloFichero.crearCarpeta("DESTINO");
      }
      
      case InterfazVista.DAR_ALTA_CARRERA_UNIVERSITARIA -> {
        long tempId = this.vista.getIdentificador();
        String tempNombre = this.vista.getNombreCarrera();
        String tempCiudad = this.vista.getCiudadCarrera();
        double tempNotaCorte = this.vista.getNotaCorte();
        
        Universidad tempUni = new Universidad(tempId, tempNombre, tempCiudad, tempNotaCorte);
        this.modeloFichero.setPath("./ORIGEN/Universidades.dat");
        
        if (this.modeloFichero.darAltaCarreraUniversitaria(tempUni) == true) {
          this.vista.escribirCadena(" > Carrera Universitaria dada de Alta.");
        } else {
          this.vista.escribirCadena(" > Ya existe una Carrera Univeristaria con ese Id.");
        }
      }
      
      case InterfazVista.GENERAR_ARCHIVO_XML_CARRERAS -> {
        this.modeloFichero.setPath("./ORIGEN/Universidades.dat");
        this.modeloFichero.generarArchivoXML();
      }
    }
  }
}
