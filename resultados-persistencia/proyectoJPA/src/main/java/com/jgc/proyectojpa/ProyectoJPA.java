/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.proyectojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
}
