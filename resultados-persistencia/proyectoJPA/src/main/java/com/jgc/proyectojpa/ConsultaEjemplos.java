package com.jgc.proyectojpa;

import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

import com.jgc.proyectojpa.model.Departamentos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@SuppressWarnings("unchecked")
public class ConsultaEjemplos {
  static EntityManagerFactory emFactory;
  static EntityManager entityManager;

  public static void main(String[] args) {
    System.out.println(" > Controlador de Consultas.");
    inicializarFactory();

    consultaAlmacenada();

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
  private static void consultaSimple () {
    Query consulta = entityManager.createQuery("Select UPPER(d.dnombre) from Departamentos d");
    
    List<String> lista = consulta.getResultList();

    for (String aux : lista) {
      System.out.println("  - Dept Name: " + aux);
      System.out.println("——————————————————————————————————————————");
    }
  }

  
  private static void consultaAvanzada () {
    TypedQuery<Object[]> consulta = entityManager.createQuery("Select d.dnombre, d.loc from Departamentos d", Object[].class);
    List<Object[]> lista = consulta.getResultList();

    for (Object[] aux : lista) {
      System.out.println("  - Dept Name: " + aux[0]);
      System.out.println("  - Dept Loc: " + aux[1]);
      System.out.println("——————————————————————————————————————————");
    }
  }

  private static void consultaAlmacenada () {
    Query consulta = entityManager.createNamedQuery("Departamentos.findAll");
    List<Departamentos> lista = consulta.getResultList();

    for (Departamentos auxDept : lista) {
      System.out.println("  - Dept Name: " + auxDept.getDnombre());
      System.out.println("——————————————————————————————————————————");
    }
  }

  private static void consultaAlmacenadaParams () {
    Query consulta = entityManager.createNamedQuery("Departamentos.findByDeptNo");

    consulta.setParameter("deptNo", 1);
    List<Departamentos> lista = consulta.getResultList();

    for (Departamentos auxDept : lista) {
      System.out.println("  - Dept Name: " + auxDept.getDnombre());
      System.out.println("——————————————————————————————————————————");
    }
  }

  private static void consultaAlmacenadaParams (int inputDeptNo) {
    Query consulta = entityManager.createNamedQuery("Departamentos.findByDeptNo");
    consulta.setParameter("deptNo", inputDeptNo);

    List<Departamentos> listaDepts = consulta.getResultList();

    for (Departamentos auxDept : listaDepts) {
      System.out.println("——————————————————————————————————————————");
      System.out.println("  - Nombre: " + auxDept.getDnombre());
    }
    System.out.println("———————————————————————————————————————————————————————————————————————————");
  }
}
