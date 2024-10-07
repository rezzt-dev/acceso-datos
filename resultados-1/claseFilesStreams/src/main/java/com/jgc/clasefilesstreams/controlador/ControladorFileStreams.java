/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.clasefilesstreams.controlador;

import com.jgc.clasefilesstreams.modelo.FileStreamsModelo;
import com.jgc.clasefilesstreams.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 30 sept 2024
 */
public class ControladorFileStreams implements ActionListener {
 // constantes y atributos -->
  private InterfazVista vista;
  private FileStreamsModelo modelo;
  
  private String path;
  private StringBuffer response;
  private boolean noSobreescribir;
  
 //----------------------------------------------------------------------------->
 // contructores -->
  public ControladorFileStreams (InterfazVista inputVista, FileStreamsModelo inputModelo) {
    this.vista = inputVista;
    this.modelo = inputModelo;
    
    this.vista.setControladorFileStreams(this);
  }
  
 //----------------------------------------------------------------------------->
 // metodos privados -->
  
 //----------------------------------------------------------------------------->
 // metodos publicos -->
   // metodo "actionPerformed" =>
  @Override
  public void actionPerformed (ActionEvent evento) {
    switch (evento.getActionCommand()) {
      case InterfazVista.LEER_FICHERO_CARACTER -> {
        this.modelo.setPath(this.vista.getPath());
        
        this.response = this.modelo.leerFicheroCaracter();
        this.vista.escribeResultado(response.toString());
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.LEER_FICHERO_CADENA -> {
        this.modelo.setPath(this.vista.getPath());
        
        this.response = this.modelo.leerFicheroCadena();
        this.vista.escribeResultado(response.toString());
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.LEER_FICHERO_LINEA -> {
        this.modelo.setPath(this.vista.getPath());
        
        this.response = this.modelo.leerFicheroLinea();
        this.vista.escribeResultado(response.toString());
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.ESCRIBIR_FICHERO_CARACTER -> {
        this.path = this.vista.getPath();
        this.modelo.setPath(this.path);
        
         // this.noSobreescribir = this.vista.sobreescribirFichero();
        char inputChar = this.vista.leerChar();
        
        this.modelo.escribirFicheroCaracter(inputChar);
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.ESCRIBIR_FICHERO_CADENA -> {
        this.path = this.vista.getPath();
        this.modelo.setPath(this.path);
        
         // this.noSobreescribir = this.vista.sobreescribirFichero();
        String inputChars = this.vista.leerString();
        
        this.modelo.escribirFicheroCadena(inputChars);
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.ESCRIBIR_FICHERO_LINEA -> {
        this.path = this.vista.getPath();
        this.modelo.setPath(this.path);
        
         // this.noSobreescribir = this.vista.sobreescribirFichero();
        String stringLine = this.vista.leerString();
        
        this.modelo.escribirFicheroLinea(stringLine);
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.ENCRIPTAR_FICHERO -> {
        this.path = this.vista.getPath();
        this.modelo.setPath(this.path);
        
        this.modelo.encriptarFichero(this.path);
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.DESENCRIPTAR_FICHERO -> {
        this.path = this.vista.getPath();
        this.modelo.setPath(this.path);
        
        this.modelo.desencriptarFichero(this.path);
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.LEER_CONTENIDO_ENCRIPTADO -> {
        this.path = this.vista.getPath();
        this.modelo.setPath(this.path);
        
        response = this.modelo.encriptarContenido(this.modelo.getFileFromRuta(this.path));
        this.vista.escribeResultado(response.toString());
        this.vista.operacionTerminada();
      }
      
      case InterfazVista.LEER_CONTENIDO_DESENCRIPTADO -> {
        this.path = this.vista.getPath();
        this.modelo.setPath(this.path);
        
        response = this.modelo.desencriptarContenido(this.modelo.getFileFromRuta(this.path));
        this.vista.escribeResultado(response.toString());
        this.vista.operacionTerminada();
      }
    }
  }
}
