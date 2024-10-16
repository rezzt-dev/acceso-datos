/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.filedomxml.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 16 oct 2024
 */
public class GestionContenidoDOM {
 // atributos & constantes =>
  Document documento;
  DocumentBuilderFactory docFactory;
  DocumentBuilder docBuilder;

 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public GestionContenidoDOM (String inputNombre) {
    try {
      this.docFactory = DocumentBuilderFactory.newInstance();
      this.docBuilder = this.docFactory.newDocumentBuilder();
      
      DOMImplementation domImplementation = this.docBuilder.getDOMImplementation();
      this.documento = (Document) domImplementation.createDocument(null, inputNombre, null);
      this.documento.setXmlVersion("1.0");
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(GestionContenidoDOM.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    System.out.println(" > documento creado en memoria.");
  }
  
 //——————————————————————————————————————————————————————————————————————
 // metodos privados =>
 //——————————————————————————————————————————————————————————————————————
  // metodos publicos =>

  
 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
   // getters ->

}
