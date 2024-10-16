/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.filedomxml;

import com.jgc.filedomxml.modelo.GestionContenidoDOM;
//import org.w3c.dom.Element;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class FileDOMXml {
  public static void main(String[] args) {
    String generalName = "Empleados";
    GestionContenidoDOM modeloDOM = new GestionContenidoDOM(generalName);
    
//    Element elem = modeloDOM.addNodo("Empleado");
//    modeloDOM.addNodoTexto("identificador", "1", elem);
//    
//    Element elem2 = modeloDOM.addNodo("Empleado");
//    modeloDOM.addNodoTexto("identificador", "2", elem2);
//    
//    Element elem3 = modeloDOM.addNodo("Empleado");
//    modeloDOM.addNodoTexto("identificador", "3", elem3);

    
    
    
//    modeloDOM.generateFileFromDOM("./Resources/"+ generalName +".xml");
    modeloDOM.chargeFileInMemory("./Resources/"+ generalName +".xml");
    modeloDOM.screenPrinter();
  }
}
