/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mmc.proyectojpav2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MMC by Mateo Molina Campos
 */
public class ProyectoJPAv2 {
    
    static EntityManagerFactory emfactory;
    static EntityManager entitymanager;
    static Departamentos departamentos;
    static DepartamentosJpaController departamentosJpaController;
    

    public static void main(String[] args) {
       
        
        //el emfactory es como una especie de fabrcia de creacion de entidades y demas
        //ente las comillas le tenemos que pasar el nombre de nuestra unidad de persistencia (abrimos el archivo persistence.xml y copiamos el persistence unit name)
        inicializarFactory();
//        
//        //parecido a cuando le pasábamos la conexion
//        departamentosJpaController = new DepartamentosJpaController(emfactory);
//        
//        //creamos un departamento y, depsues, llamando al jpacontroller.create hacemos el insert pasandole el objeto
//        departamentos = new Departamentos();
//        
//        //
//        departamentos.setDeptNo((short)99);
//        departamentos.setDnombre("Pruebas");
//        departamentos.setLoc("Madrid");
//        
//        try {
//            departamentosJpaController.create(departamentos);
//        } catch (Exception ex) {
//            Logger.getLogger(ProyectoJPAv2.class.getName()).log(Level.SEVERE, null, ex);
//        }

        leerRegistroRelacionadoEmpleDept();
        modificarDatosDept( 50, "CONSTRUCCION", "VALENCIA");
        modificarDatosDeptSeguro(10, "MODA", "LUGO");
        
        cerrarFactory();
        
    }
    
    public static void inicializarFactory () {
      emfactory = Persistence.createEntityManagerFactory("com.mmc_proyectoJPAv2_jar_1.0-SNAPSHOTPU");
      entitymanager = emfactory.createEntityManager();
    }
    
    public static void cerrarFactory () {
      entitymanager.close();
      emfactory.close();
    }
    
    public static void leerRegistroDept () {
      departamentos = entitymanager.find(Departamentos.class, (short) 10);
      
      if (departamentos != null) {
        System.out.println(" > Dept NAME: " + departamentos.getDnombre());
      } else {
        System.out.println(" > No existe el registro");
      }
    }
    
    public static void leerRegistroBloqueDept () {
      entitymanager.getTransaction().begin();
      
      departamentos = entitymanager.find(Departamentos.class, (short) 10, LockModeType.PESSIMISTIC_READ);
      
      if (departamentos != null) {
        System.out.println(" > Dept NAME: " + departamentos.getDnombre());
      } else {
        System.out.println(" > No existe el registro");
      }
      
      entitymanager.getTransaction().commit();
    }
    
    public static void leerRegistroRelacionadoEmpleDept () {
      Empleados tempEmple;
      
      departamentos = entitymanager.find(Departamentos.class, (short) 10);
      
      if (departamentos != null) {
        System.out.println(" > Dept NAME: " + departamentos.getDnombre());
        
        Collection<Empleados> listEmple = departamentos.getEmpleadosCollection();
        
        Iterator<Empleados> it = listEmple.iterator();
        
        while (it.hasNext()) {
          tempEmple = it.next();
          System.out.println("  - Emple NAME: " + tempEmple.getApellido());
        }
      } else {
        System.out.println(" > No existe el registro");
      }
    }
    
    public static void esperar () {
      try {
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        
        System.out.println(" > Pulsa ENTER para continuar...");
        String sTexto = br.readLine();
      } catch (IOException ex) {
        Logger.getLogger(ProyectoJPAv2.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    public static void recargarDeptBBDD () {
      entitymanager.getTransaction().begin();
      entitymanager.refresh(departamentos);
      entitymanager.getTransaction().commit();
    }
    
    public static void insertarDatosDept (int inputDeptNo, String inputNombre, String inputLoc) {
      Departamentos dept;
      
      dept = new Departamentos();
      dept.setDeptNo((short) inputDeptNo);
      dept.setDnombre(inputNombre);
      dept.setLoc(inputLoc);
      
      entitymanager.getTransaction().begin();
      entitymanager.persist(dept);
      entitymanager.getTransaction().commit();
    }
    
    public static void modificarDatosDept (int inputDeptNo, String newNombre, String newLoc) {
      departamentos = entitymanager.find(Departamentos.class, (short) inputDeptNo);
      
      entitymanager.getTransaction().begin();
      departamentos.setDnombre(newNombre);
      departamentos.setLoc(newLoc);
      entitymanager.getTransaction().commit();
    }
    
    public static void modificarDatosDeptSeguro (int inputDeptNo, String newNombre, String newLoc) {
      entitymanager.getTransaction().begin();
      departamentos = entitymanager.find(Departamentos.class, (short) inputDeptNo, LockModeType.PESSIMISTIC_READ);
      entitymanager.getTransaction().commit();
      
      entitymanager.getTransaction().begin();
      departamentos.setDnombre(newNombre);
      departamentos.setLoc(newLoc);
      entitymanager.getTransaction().commit();
    }
    
    public static void borrarDatosDept (int inputDeptNo) {
      entitymanager.getTransaction().begin();
      departamentos = entitymanager.find(Departamentos.class, (short) inputDeptNo, LockModeType.PESSIMISTIC_READ);
      entitymanager.getTransaction().commit();
      
      entitymanager.getTransaction().begin();
      entitymanager.remove(departamentos);
      entitymanager.getTransaction().commit();
    }
}
