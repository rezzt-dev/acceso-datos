/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.proyectojpa;


import com.jgc.proyectojpa.controllers.DepartamentosJpaController;
import com.jgc.proyectojpa.controllers.EmpleadosJpaController;
import com.jgc.proyectojpa.model.Departamentos;
import com.jgc.proyectojpa.model.Empleados;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 dic 2024
 */
public class TestControllers {
  static EntityManagerFactory emFactory;
  
  public static void main(String[] args) {
    try {
      inicializarFactoryController();
      
      DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);
      EmpleadosJpaController empleController = new EmpleadosJpaController(emFactory);
      
      Departamentos departamento = new Departamentos();
      
      departamento.setDeptNo((short) 77);
      departamento.setDnombre("BIG DATA");
      departamento.setLoc("TALAVERA");
      departamento.setEmpleadosCollection(null);
      
      Collection<Empleados> empleCollection = new ArrayList<>();
//      Empleados auxEmple = new Empleados();
//      
//      auxEmple.setEmpNo((short) 7777);
//      auxEmple.setApellido("ROBLES");
//      auxEmple.setSalario(BigDecimal.valueOf(2000));
//      auxEmple.setOficio("ANALISTA");
//      auxEmple.setDir((short) 7839);

      Empleados auxEmple = emFactory.createEntityManager().getReference(Empleados.class, (short) 7839);
      auxEmple.setDeptNo(departamento);
      empleController.edit(auxEmple);
      
      departamento.setEmpleadosCollection(empleCollection);
      deptController.edit(departamento);
      
      
      
      cerrarFactoryController();
    } catch (Exception ex) {
      Logger.getLogger(TestControllers.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void inicializarFactoryController () {
    emFactory = Persistence.createEntityManagerFactory("com.jgc_proyectoJPA_jar_1.0-SNAPSHOTPU");
  }
  
  public static void cerrarFactoryController () {
    emFactory.close();
  }
}
