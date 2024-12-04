/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.filexmlconversor;

import com.jgc.filexmlconversor.modelo.Conversor;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class FileXMLConversor {
  public static void main(String[] args) {
    String ficheroXML = "./Resources/Empleados.xml";
    String plantillaXSL = "./Resources/plantillaEmpleados.xsl";
    String ficheroHTML = "./Resources/outputHTML.html";
    
    Conversor modeloConversor = new Conversor(ficheroXML, plantillaXSL, ficheroHTML);
    modeloConversor.ConvertToHTML();
  }
}
