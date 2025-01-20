/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.proyectojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgc.proyectojpa.model.Departamentos;
import com.jgc.proyectojpa.model.Empleados;

/**
 *
 * @author rezzt
 */
public class ProyectoJPA {
  static EntityManagerFactory emFactory;
  static EntityManager entityManager;
  static Departamentos departamentos;
  
  public static void main(String[] args) {
    inicializarFactory();
    mostrarDatosEmpleFromDept(10);
    cerrarFactory();
  }
  
  public static void inicializarFactory () {
    emFactory = Persistence.createEntityManagerFactory("objectdb://localhost/proyecto.odb;user=admin;password=admin");
    entityManager = emFactory.createEntityManager();
  }
  
  public static void cerrarFactory () {
    entityManager.close();
    emFactory.close();
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
  public static void mostrarDatosEmpleFromDept (int inputDeptNo) {
    short deptNo = (short) inputDeptNo;
    
    Empleados tempEmple;
    Departamentos dept = new Departamentos();
    
    entityManager.getTransaction().begin();
    dept = entityManager.find(Departamentos.class, deptNo, LockModeType.PESSIMISTIC_READ);
    entityManager.getTransaction().commit();
    
    System.out.println(" > Departamento: " + dept.getDeptNo() + " | Nombre: " + dept.getDnombre() + " | Loc: " + dept.getLoc());
    System.out.println(" > Lista de Empleados: ");
    
    List<Empleados> listEmple = dept.getEmpleadosCollection();
    Iterator<Empleados> it = listEmple.iterator();
    
    while (it.hasNext()) {
      tempEmple = it.next();
      System.out.println("  - Empleado: " + tempEmple.getApellido());
    }
    
    System.out.println("——————————————————————————————————————————————————————————————————————————");
  }
  
  public static void eliminarDept (int inputDeptNo) {
    short deptNo = (short) inputDeptNo;
    
    entityManager.getTransaction().begin();
    departamentos = entityManager.find(Departamentos.class, deptNo, LockModeType.PESSIMISTIC_READ);
    entityManager.getTransaction().commit();
    
    entityManager.getTransaction().begin();
    entityManager.remove(departamentos);
    entityManager.getTransaction().commit();
  }
  
  public static void modificarSalarioDeptFromEmple (int inputId, double inputSalario, int inputDeptNo) {
    Empleados tempEmple;
    
    short newDeptNo = (short) inputDeptNo;
    BigDecimal newSalario = BigDecimal.valueOf(inputSalario);
    
    entityManager.getTransaction().begin();
    tempEmple = entityManager.find(Empleados.class, (short) inputId, LockModeType.PESSIMISTIC_READ);
    departamentos = entityManager.find(Departamentos.class, newDeptNo, LockModeType.PESSIMISTIC_READ);
    entityManager.getTransaction().commit();
    
    if (tempEmple != null && departamentos != null) {
      entityManager.getTransaction().begin();
      tempEmple.setSalario(newSalario);
      tempEmple.setDeptNo(departamentos);
      entityManager.getTransaction().commit();
    } else {
      if (tempEmple == null) {
        System.out.println("  - El empleado seleccionado no existe.");
      }
      if (departamentos == null) {
        System.out.println("  - El departamento selecciona no existe.");
      }
    }
  }
  
  public static void eliminarEmple () {
    Empleados tempEmple;
    
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader (System.in));
      short empleNo = (short) br.read();
      
      entityManager.getTransaction().begin();
      tempEmple = entityManager.find(Empleados.class, (short) empleNo, LockModeType.PESSIMISTIC_READ);
      
      if (tempEmple != null) {
        entityManager.getTransaction().begin();
        entityManager.remove(tempEmple);
        entityManager.getTransaction().commit();
      } else {
        System.out.println(" > El empleado con Id: " + empleNo + " no existe.");
      }
    } catch (IOException ex) {
      Logger.getLogger(ProyectoJPA.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public static void insertarDatosEmple (int inputEmpleNo, String inputApellido, String inputOficio, int inputDir, String inputFechaAlt, double inputSalario, double inputComision, int inputDeptNo) {
    Empleados tempEmple = new Empleados();
    
    // casteo de los parametros
    short empleNo = (short) inputEmpleNo;
    short dir = (short) inputDir;
    BigDecimal salario = BigDecimal.valueOf(inputSalario);
    BigDecimal comision = BigDecimal.valueOf(inputComision);
    short deptNo = (short) inputDeptNo;
    Date fechaAlta = stringToDate(inputFechaAlt);
    
    entityManager.getTransaction().begin();
    departamentos = entityManager.find(Departamentos.class, deptNo, LockModeType.PESSIMISTIC_READ);
    entityManager.getTransaction().commit();
    
    tempEmple.setEmpNo(empleNo);
    tempEmple.setApellido(inputApellido);
    tempEmple.setDir(dir);
    tempEmple.setOficio(inputOficio);
    tempEmple.setFechaAlt(fechaAlta);
    tempEmple.setSalario(salario);
    tempEmple.setComision(comision);
    tempEmple.setDeptNo(departamentos);
    
    entityManager.getTransaction().begin();
    entityManager.persist(tempEmple);
    entityManager.getTransaction().commit();
  }
  
  private static Date stringToDate (String inputFecha) {
    java.util.Date fechaUtil = null;
    try {
      SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy");
      fechaUtil = s.parse(inputFecha);
    } catch (ParseException ex) {
      Logger.getLogger(ProyectoJPA.class.getName()).log(Level.SEVERE, null, ex);
    }
    return new java.sql.Date(fechaUtil.getTime());
  }
  
  public static void subidaSalarioEmpleFromDept (int inputDeptNo, double inputPlusSalario) {
    short deptNo = (short) inputDeptNo;
    BigDecimal plusSalario = BigDecimal.valueOf(inputPlusSalario);
    
    entityManager.getTransaction().begin();
    departamentos = entityManager.find(Departamentos.class, deptNo, LockModeType.PESSIMISTIC_READ);
    entityManager.getTransaction().commit();
    
    if (departamentos != null) {
      List<Empleados> listEmple = departamentos.getEmpleadosCollection();
      System.out.println(" > Lista de Empleados sin Actualizar: ");
      
      for (Empleados tempEmple : listEmple) {
        System.out.println("  - Empleado: " + tempEmple.getApellido() + " | Salario: " + tempEmple.getSalario());
      }
      
      System.out.println("——————————————————————————————————————————————————————————————————————————");
      System.out.println(" > Lista de Empleados Actualizados: ");
      
      for (Empleados tempEmple : listEmple) {
        entityManager.getTransaction().begin();
        tempEmple.setSalario(tempEmple.getSalario().add(plusSalario));
        entityManager.getTransaction().commit();
        
        System.out.println("  - Empleado: " + tempEmple.getApellido() + " | Salario: " + tempEmple.getSalario());
      }
    }
  }
}
