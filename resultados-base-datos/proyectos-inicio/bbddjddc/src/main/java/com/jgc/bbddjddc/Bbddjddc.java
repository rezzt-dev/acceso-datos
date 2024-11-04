/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.bbddjddc;

import com.jgc.bbddjddc.bbdd.OperacionesBBDD;
import com.jgc.bbddjddc.modelo.Departamento;
import com.jgc.bbddjddc.modelo.Empleado;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class Bbddjddc {
  static OperacionesBBDD operBBDD = OperacionesBBDD.getInstance();
  
  public static void main(String[] args) {
    operBBDD.abrirConexion();
    insertarDatos();
    operBBDD.cerrarConexion();
  }
  
  public static void insertarDatos () {
//    Departamento tempDep = new Departamento(1, "recursos humanos", "ciudad real");
//    tempDep.insert();
    Empleado tempEmple = new Empleado(1,"garcia", "programador", 1234, "09/3/2022", 1400, 3, 2);
    tempEmple.insert();
  }
}
