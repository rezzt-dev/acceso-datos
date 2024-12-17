package com.jgc.proyectojpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

import com.jgc.proyectojpa.model.Empleados;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class UpdateDeleteTester {
  static EntityManagerFactory emFactory;
  static EntityManager entityManager;

  public static void main(String[] args) {
    System.out.println(" > Controlador de Update y Delete.");
    inicializarFactory();

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
  private static void modificarDatosConUpdate () {
    Query consulta = entityManager.createQuery("UDPATE Departamentos d SET d.deptNo =: valorNuevo WHERE d.deptNo =: deptNoV");
    consulta.setParameter("valorNuevo", (short) 88);
    consulta.setParameter("deptNoV", (short) 10);

    entityManager.getTransaction().begin();
    int updateCount = consulta.executeUpdate();
    entityManager.getTransaction().commit();
  }

  private static void eliminarDatosConUpdate () {
    Query consulta = entityManager.createQuery("DELETE FROM Departamentos d WHERE d.deptNo =: d.deptNoV");
    consulta.setParameter("deptNoV", (short) 20);

    entityManager.getTransaction().begin();
    int deleteCount = consulta.executeUpdate();
    entityManager.getTransaction().commit();
  }

 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
  public static void updateSalarios (int inputDeptNo, double inputSalario) {
    short deptNo = (short) inputDeptNo;
    BigDecimal subida = BigDecimal.valueOf(inputSalario);

    EntityTransaction transaction = entityManager.getTransaction();

    System.out.println("—————————————————————————————————————————————————————————————————————————————————————————————");
    System.out.println(" > Lista de Salarios Sin Actualizar: ");
    mostrarSalarios(inputDeptNo);

    try {
      transaction.begin();

      Query consulta = entityManager.createQuery("SELECT e FROM Empleados e WHERE e.deptNo =: inputDeptNo");
      consulta.setParameter("inputDeptNo", deptNo);

      List<Empleados> listaEmple = consulta.getResultList();
      for (Empleados auxEmple : listaEmple) {
        auxEmple.setSalario(auxEmple.getSalario().add(subida));
        entityManager.merge(auxEmple);
      }

      transaction.commit();

    } catch (Exception ex) {
      if (transaction.isActive()) {
        transaction.rollback();
      }

      ex.printStackTrace();
    }

    System.out.println("—————————————————————————————————————————————————————————————————————————————————————————————");
    System.out.println(" > Lista de Salarios Actualizados: ");
    mostrarSalarios(inputDeptNo);
  }

  private static void mostrarSalarios (int inputDeptNo) {
    short deptNo = (short) inputDeptNo;

    Query consulta = entityManager.createQuery("SELECT e FROM Empleados e WHERE e.deptNo =: inputDeptNo");
    consulta.setParameter("inputDeptNo", deptNo);

    List<Empleados> listaEmple = consulta.getResultList();
    System.out.println("  - Salarios: ");

    for (Empleados auxEmple : listaEmple) {
      System.out.println("   + Apellido: " + auxEmple.getApellido() + " | Salario: " + auxEmple.getSalario());
    }
  }

 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
  public static void deleteDept () {
    BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
    int inputDeptNo = 0;

    try {
      System.out.print(" > Introduce el Numero del Departamento que quieres eliminar: ");
      inputDeptNo = Integer.valueOf(bReader.readLine());
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        bReader.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    Query consulta = entityManager.createQuery("DELETE FROM Departamentos d WHERE d.deptNo =: inputDeptNo");
    consulta.setParameter("inputDeptNo", (short) inputDeptNo);

    entityManager.getTransaction().begin();
    int deleteCount = consulta.executeUpdate();
    entityManager.getTransaction().commit();
  }

  public static void modificarSalarioDeptFromEmple (int inputEmpleNo, int inputDeptNo, double inputSalario) {
    short empleNo = (short) inputEmpleNo;
    short deptNo = (short) inputDeptNo;
    BigDecimal salario = BigDecimal.valueOf(inputSalario);
    
    Query consulta = entityManager.createQuery("SELECT Empleados d SET e.deptNo =: inputDeptNo, e.salario =: inputSalario WHERE e.empleNo =: inputEmpleNo");
    consulta.setParameter("inputDeptNo", deptNo);
    consulta.setParameter("inputSalario", salario);
    consulta.setParameter("inputEmpleNo", empleNo);

    entityManager.getTransaction().begin();
    int updateCount = consulta.executeUpdate();
    entityManager.getTransaction().commit();
  }

  public static void deleteEmple () {
    BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
    int inputEmpleNo = 0;

    try {
      System.out.print(" > Introduce el Numero del Empleado que quieres eliminar: ");
      inputEmpleNo = Integer.valueOf(bReader.readLine());
    } catch (IOException ex) {
      ex.printStackTrace();
    } finally {
      try {
        bReader.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    Query consulta = entityManager.createQuery("DELETE FROM Empleados e WHERE e.empleNo =: inputEmpleNo");
    consulta.setParameter("inputEmpleNo", (short) inputEmpleNo);

    entityManager.getTransaction().begin();
    int deleteCount = consulta.executeUpdate();
    entityManager.getTransaction().commit();
  }
}
