/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mmc.proyectojpav2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.math.BigDecimal;
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

        leerRegistroDept();
        
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
}
