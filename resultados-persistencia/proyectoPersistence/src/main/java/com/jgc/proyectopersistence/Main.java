package com.jgc.proyectopersistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
import java.util.Date;

public class Main {

  static EntityManagerFactory emFactory;
  static EntityManager entityManager;

  public static void main(String[] args) {
    inicializaFactory();
    entityManager.close();
    emFactory.close();
  }

  public static void inicializaFactory() {
    emFactory = Persistence.createEntityManagerFactory("com.jgc_proyectoPersistence_jar_1.0-SNAPSHOTPU");
    entityManager = emFactory.createEntityManager();
  }

  // Método para insertar un nuevo empleado
  public static void insertarEmpleado(Short empNo, String apellido, String oficio, Short dir, Date fechaAlt, BigDecimal salario, BigDecimal comision, Departamentos departamento) {
    try {
      entityManager.getTransaction().begin();

      Empleados empleado = new Empleados();
      empleado.setEmpNo(empNo);
      empleado.setApellido(apellido);
      empleado.setOficio(oficio);
      empleado.setDir(dir);
      empleado.setFechaAlt(fechaAlt);
      empleado.setSalario(salario);
      empleado.setComision(comision);
      empleado.setDeptNo(departamento); // Relacionamos al empleado con un departamento

      entityManager.persist(empleado); // Guardar el nuevo empleado

      entityManager.getTransaction().commit(); // Confirmar la transacción
      System.out.println("Empleado insertado con éxito.");
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      e.printStackTrace();
    }
  }

  // Método para modificar un empleado existente
  public static void modificarEmpleado(Short empNo, String apellido, String oficio, Short dir, Date fechaAlt, BigDecimal salario, BigDecimal comision) {
    try {
      entityManager.getTransaction().begin();

      Empleados empleado = entityManager.find(Empleados.class, empNo); // Buscar al empleado por ID
      if (empleado != null) {
        empleado.setApellido(apellido);
        empleado.setOficio(oficio);
        empleado.setDir(dir);
        empleado.setFechaAlt(fechaAlt);
        empleado.setSalario(salario);
        empleado.setComision(comision);

        entityManager.merge(empleado); // Actualizar la entidad

        entityManager.getTransaction().commit(); // Confirmar la transacción
        System.out.println("Empleado modificado con éxito.");
      } else {
        System.out.println("Empleado no encontrado.");
      }
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      e.printStackTrace();
    }
  }

  // Método para eliminar un empleado
  public static void eliminarEmpleado(Short empNo) {
    try {
      entityManager.getTransaction().begin();

      Empleados empleado = entityManager.find(Empleados.class, empNo); // Buscar al empleado por ID
      if (empleado != null) {
        entityManager.remove(empleado); // Eliminar la entidad

        entityManager.getTransaction().commit(); // Confirmar la transacción
        System.out.println("Empleado eliminado con éxito.");
      } else {
        System.out.println("Empleado no encontrado.");
      }
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive()) {
        entityManager.getTransaction().rollback();
      }
      e.printStackTrace();
    }
  }
}
