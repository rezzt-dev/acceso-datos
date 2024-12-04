/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.filedomxmlbinary;

import com.jgc.filedomxmlbinary.modelo.Conversor;
import com.jgc.filedomxmlbinary.modelo.Empleado;
import java.util.List;


/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class FileDOMXmlBinary {
  public static void main(String[] args) {
    Conversor modeloConversor = new Conversor("./Resources/empleados.dat", "DatosEmpleados");
    List<Empleado> listaEmpleados = modeloConversor.lecturaEmpleados();
    modeloConversor.fromEmpleadoToXML(listaEmpleados);
  }
}
