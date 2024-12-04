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

    // Ejemplo de uso de los métodos
    // Primero asegurémonos de tener un departamento
    Departamentos departamento = entityManager.find(Departamentos.class, (short) 1); // Convertir 1 a Short

    // Insertar un nuevo empleado
    insertarEmpleado((short) 1001, "Pérez", "Desarrollador", (short) 7839, new Date(), new BigDecimal("3500"), new BigDecimal("500"), departamento);

    // Modificar un empleado existente (usando ID de 1001 como ejemplo)
    modificarEmpleado((short) 1001, "Pérez", "Senior Developer", (short) 7839, new Date(), new BigDecimal("4000"), new BigDecimal("600"));

    // Eliminar un empleado (usando ID de 1001 como ejemplo)
    eliminarEmpleado((short) 1001);

    // Cerrar EntityManager y Factory
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
