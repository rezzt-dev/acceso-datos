/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.filedomxml;

import com.jgc.filedomxml.modelo.Empleado;
import com.jgc.filedomxml.modelo.GestionContenidoDOM;
import java.util.List;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class FileDOMXml {
  public static void main(String[] args) {
    String generalName = "Empleados";
    GestionContenidoDOM modeloDOM = new GestionContenidoDOM(generalName);
    
    modeloDOM.createNewEmpleado(1, "Garcia", 11, 900.0, "Maestro");
    modeloDOM.createNewEmpleado(2, "Aguilar", 22, 800.0, "Atleta");
    modeloDOM.createNewEmpleado(3, "Molina", 33, 700.0);

    
    modeloDOM.addCargoToAllEmpleados();
    modeloDOM.modifySalarioFromEmpleado(1, 1000.0);
    
    modeloDOM.generateFileFromDOM("./Resources/"+ generalName +".xml");
    
    System.out.println("\n");
    
    List<Empleado> empleList = modeloDOM.getEmpleados();
    for (Empleado tempEmple : empleList) {
      System.out.println(tempEmple.toString());
    }
  }
}
