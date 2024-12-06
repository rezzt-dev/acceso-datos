package com.jgc.proyectojpa.controlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgc.proyectojpa.Departamentos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;

public class GeneralController {
  static EntityManagerFactory emfactory;
  static EntityManager entitymanager;
  static Departamentos departamentos;

  public static void leerRegistroDepts (int inputId) {
    entitymanager.getTransaction().begin();
    departamentos = entitymanager.find(Departamentos.class, (short) inputId, LockModeType.PESSIMISTIC_READ);
    entitymanager.getTransaction().commit();

    if (departamentos != null) {
      System.out.println(" > Dept NAME: " + departamentos.getDnombre());
    } else {
      System.out.println(" > No existe el registro");
    }
  }

  public static void esperar () {
    try {
      BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

      System.out.println(" > Pulsa ENTER para continuar...");
      String sTexto = bReader.readLine();
    } catch (IOException ex) {
      Logger.getLogger(GeneralController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
