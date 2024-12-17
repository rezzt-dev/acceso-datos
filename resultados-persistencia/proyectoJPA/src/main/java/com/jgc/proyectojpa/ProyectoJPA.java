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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgc.proyectojpa.controllers.DepartamentosJpaController;
import com.jgc.proyectojpa.exceptions.NonexistentEntityException;
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
    listarEmplesFromDept(10);
    cerrarFactory();
  }
  
  public static void inicializarFactory () {
    emFactory = Persistence.createEntityManagerFactory("com.jgc_proyectoJPA_jar_1.0-SNAPSHOTPU");
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
    
    Collection<Empleados> listEmple = dept.getEmpleadosCollection();
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
    
    entityManager.getTransaction().begin();
    tempEmple.setSalario(newSalario);
    tempEmple.setDeptNo(departamentos);
    entityManager.getTransaction().commit();
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
      SimpleDateFormat s = new SimpleDateFormat("DD/MM/YYYY");
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
      Collection<Empleados> listEmple = departamentos.getEmpleadosCollection();
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

 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos usando los controladores de las clases
  public static void crearEmpleDept () {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);

    try {
      Departamentos dept = new Departamentos((short) 77);
      dept.setDnombre("BIG DATA");
      dept.setLoc("TALAVERA");

      dept.setEmpleadosCollection(new ArrayList<>());

      deptController.create(dept);

      System.out.println("  - Departamentos y empleados creados existosamente.");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void insertarDeptWithEmple () {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);

    try {
      Departamentos dept = new Departamentos((short) 99);
      dept.setDnombre("BIG DATA");
      dept.setLoc("TOLEDO");

      Empleados newEmpleado = new Empleados((short) 7521);

      Collection<Empleados> listEmpleados = new ArrayList<Empleados>();
      listEmpleados.add(newEmpleado);

      dept.setEmpleadosCollection(listEmpleados);
      deptController.create(dept);

      System.out.println("  - Departamentos y empleados creados existosamente.");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void borrarDept () {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);

    try {
      deptController.destroy((short) 99);
    } catch (NonexistentEntityException ex) {
      ex.printStackTrace();
    }
  }

  public static void listDept () {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);
    List<Departamentos> deptList = deptController.findDepartamentosEntities();
    
    System.out.println("——————————————————————————————————————————————————————————————————————————");
    System.out.println(" > Lista de Departamentos");
    for (Departamentos auxDept : deptList) {
      System.out.println("  - Nombre Dept: " + auxDept.getDnombre() + " | Numero Empleados: " + auxDept.getEmpleadosCollection().size());
    }
    System.out.println("——————————————————————————————————————————————————————————————————————————");
  }

  public static void listDeptTramos () {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);
    List<Departamentos> deptList;
    
    listDept();
    deptList = deptController.findDepartamentosEntities(3, 0);
    
    System.out.println(" > Trae 3 resgistros empezando en la posicion 0.");
    System.out.println("——————————————————————————————————————————————————————————————————————————");
    
    System.out.println(" > Lista de Departamentos");
    for (Departamentos auxDept : deptList) {
      System.out.println("  - Nombre Dept: " + auxDept.getDnombre() + " | Numero Empleados: " + auxDept.getEmpleadosCollection().size());
    }
    System.out.println("——————————————————————————————————————————————————————————————————————————");

    deptList = deptController.findDepartamentosEntities(3, 1);
    System.out.println(" > Trae 3 resgistros empezando en la posicion 1.");
    System.out.println("——————————————————————————————————————————————————————————————————————————");
    
    System.out.println(" > Lista de Departamentos");
    for (Departamentos auxDept : deptList) {
      System.out.println("  - Nombre Dept: " + auxDept.getDnombre() + " | Numero Empleados: " + auxDept.getEmpleadosCollection().size());
    }
    System.out.println("——————————————————————————————————————————————————————————————————————————");

  }

  public static void countNumDepts () {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);
    int numDepts = deptController.getDepartamentosCount();

    System.out.println(" > Numero de Departamentos: " + numDepts);
  }

  public static void getDeptData () {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);
    int numDeptFind = 10;
    Departamentos auxDept = deptController.findDepartamentos((short) numDeptFind);

    System.out.println(" > Num Dept: " + numDeptFind + " | Nombre: " + auxDept.getDnombre());
  }

  public static void modificarDept (int inputId) {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);
    Departamentos auxDept = deptController.findDepartamentos((short) inputId);

    System.out.println("——————————————————————————————————————————————————————————————————————————");

    try {
      auxDept.setDeptNo((short) inputId);
      auxDept.setDnombre("CONTABILIDAD");
      auxDept.setLoc("MADRID");

      deptController.edit(auxDept);
      System.out.println(" > Dept " + inputId + " modificado correctamente.");
    } catch (NonexistentEntityException ex) {
      ex.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("——————————————————————————————————————————————————————————————————————————");
  }

  public static void listarEmplesFromDept (int inputId) {
    DepartamentosJpaController deptController = new DepartamentosJpaController(emFactory);
    Departamentos auxDept = deptController.findDepartamentos((short) inputId);

    System.out.println(" > Dept " + inputId + " | Nombre: " + auxDept.getDnombre() + " | Num Empleados: " + auxDept.getEmpleadosCollection().size());
    System.out.println("——————————————————————————————————————————————————————————————————————————");

    Collection<Empleados> listaEmples = auxDept.getEmpleadosCollection();
    for (Empleados auxEmple : listaEmples) {
      System.out.println("  - Empleado " + auxEmple.getEmpNo() + " | Apellido: " + auxEmple.getApellido());
    }

    System.out.println("——————————————————————————————————————————————————————————————————————————");
  }
}
