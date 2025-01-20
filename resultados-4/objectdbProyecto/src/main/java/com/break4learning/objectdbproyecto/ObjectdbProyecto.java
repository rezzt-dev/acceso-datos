/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.break4learning.objectdbproyecto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author Break4Learning by Javier García-Retamero Redondo
 * @version 1.0
 * Created on 30 dic 2024
 */
public class ObjectdbProyecto {

    private static EntityManagerFactory emfactory;
    private static EntityManager entitymanager;

    public static void main(String[] args) {
        
        inicializaFactory();
        
        //insertarDatos();
        //modificarDatos(4, "Pepito");
 
        // Consultas SIN JPQL
        //consultaDatosUsuario(1);
        //consultaConPedidos(1);
        
        // Consultas con JPQL
        //consultaDatosUsuarioConJPQL(1);
        //consultaDatosUsuarioConJPQL(1000);
        //consultaDatosUsuarioYPedidosConJPQL(1);
        //consultaUsuariosConJPQL();
        //consultaVariosCamposConJPQL();
 
        // Consultas Named
        //consultaNamedDatosUsuario(1);
        //consultaNamedUsuarios();
        
        // Consultas con CriteriaQuery
        //consultaConCriteriaQuery();
        //consultaConCriteriaQueryVariosCampos();
        
        finalizaFactory();
    }
    
    /**
     * Crea las clases necesarias para establecer la comunicación con la BBDD y realizar operaciones
     */   
    public static void inicializaFactory(){
        //Conexion al servidor: objectdb://localhost/proyecto.odb;user=admin;password=admin
        //Conecion local: ./db/proyecto.odb
        
        emfactory = Persistence.createEntityManagerFactory( "objectdb://localhost:6136/proyecto.odb;user=admin;password=admin" );
        entitymanager = emfactory.createEntityManager();
    }    
    
    /**
     * Finaliza la conexión con la BBDD cerrando todo lo necesario 
     */
    public static void finalizaFactory(){
        entitymanager.close();
        emfactory.close();
    }
    
    
    /**
     * A) EJEMPLOS QUE NO UTLIZAN JPQL 
     */
    
    /**
     * Ejemplo de inserción de datos en objetos relacionados
     */
    public static void insertarDatos(){
        entitymanager.getTransaction().begin();  
        
        Usuarios usu1 = new Usuarios(1,"ANA","ana2014");
        entitymanager.persist(usu1);
        Usuarios usu2 = new Usuarios(2,"LUIS","luis2014");
        entitymanager.persist(usu2);
        Usuarios usu3 = new Usuarios(3,"SILVIA","silvia2014");
        entitymanager.persist(usu3);
        Usuarios usu4 = new Usuarios(4,"PEDRO","pedro2014");
        entitymanager.persist(usu4);
        
        Productosesp prod1 = new Productosesp(1,"TABLET",150);
        entitymanager.persist(prod1);
        Productosesp prod2 = new Productosesp(2,"RELOJ",60);
        entitymanager.persist(prod2);
        Productosesp prod3 = new Productosesp(3,"GAMEPAD",40);
        entitymanager.persist(prod3);
        Productosesp prod4 = new Productosesp(4,"CAJA CPU",80);
        entitymanager.persist(prod4);
        Productosesp prod5 = new Productosesp(5,"PORTATIL",1500);
        entitymanager.persist(prod5);
        Productosesp prod6 = new Productosesp(6,"RATON",25);
        entitymanager.persist(prod6);
        
        
        Pedidos ped = new Pedidos(1,30,Metodos.convertirDate("01/01/24"),prod1,usu1);
        entitymanager.persist(ped);
        ped = new Pedidos(2,20,Metodos.convertirDate("12/02/24"),prod2,usu1);
        entitymanager.persist(ped);
        ped = new Pedidos(3,60,Metodos.convertirDate("23/01/24"),prod3,usu2);
        entitymanager.persist(ped);
        ped = new Pedidos(4,110,Metodos.convertirDate("11/03/24"),prod4,usu3);
        entitymanager.persist(ped);      
        
        entitymanager.getTransaction().commit();
        
    }
    
    /**
     * Borrado de un usuario según su identificador
     * 
     * @param idUsuario Identificador de usuario
     */
    public static void borrarDatos(int idUsuario){
       Usuarios usuario = entitymanager.find(Usuarios.class, idUsuario);

       entitymanager.getTransaction().begin();
       entitymanager.remove(usuario);
       entitymanager.getTransaction().commit();
       
       consultaDatosUsuario(idUsuario);       
       
   }

    /**
     * Modifica el nombre de un usuario
     * 
     * @param idUsuario Identificador del usuario a modificar
     * @param nombre Nuevo nombre para el usuario 
     */
    public static void modificarDatos(int idUsuario, String nombre){
       consultaDatosUsuario(idUsuario); 
       
       Usuarios usuario = entitymanager.find(Usuarios.class, idUsuario);
       entitymanager.getTransaction().begin();
       usuario.setNombre(nombre);
       entitymanager.getTransaction().commit();
       
       consultaDatosUsuario(idUsuario); 
   }
    
    /**
     * Muestra los datos de un usuario
     * 
     * @param idUsuario Identificador del usuairo a mostrar 
     */
    private static void consultaDatosUsuario(int idUsuario){
        entitymanager.getTransaction().begin();
        Usuarios usuario=entitymanager.find(Usuarios.class, idUsuario, LockModeType.PESSIMISTIC_READ);
        
        if(usuario != null){
            System.out.print("USUARIO \t");
            System.out.print("CONTRASEÑA");
            System.out.println();

            System.out.print(usuario.getNombre()+"\t");
            System.out.print("\t"+usuario.getContra());
            System.out.println();
            
                   
        } else {
            System.out.println("ERROR: EL USUARIO INDICADO NO EXISTE");
        }
        
    }
        
    /**
     * Muestra los pedidos de un determinado usuario
     * 
     * @param idUsuario Identificador del usuario 
     */
    private static void consultaConPedidos(int idUsuario){
        entitymanager.getTransaction().begin();
        Usuarios usuario=entitymanager.find(Usuarios.class, idUsuario, LockModeType.PESSIMISTIC_READ);
        
        if(usuario != null){
            System.out.print("USUARIO \t");
            System.out.print("CONTRASEÑA");
            System.out.println();

            System.out.print(usuario.getNombre()+"\t");
            System.out.print("\t"+usuario.getContra());
            System.out.println();
            
            Pedidos p;
            List<Pedidos> coleccion = usuario.getPedidosCollection();
            Iterator<Pedidos> it = coleccion.iterator();
            while(it.hasNext()){
                p = it.next();
                System.out.println("PEDIDO:");
                System.out.println(p.getIdpedido());
                System.out.println("FECHA DE ENTREGA:");
                System.out.println(p.getFechaEntrega());
                System.out.println("PRECIO TOTAL:");
                System.out.println(p.getPrecioTotal());
            }
        
        } else {
            System.out.println("ERROR: EL USUARIO INDICADO NO EXISTE");
        }
        
    }
    
    
    /**
     * B) EJEMPLOS QUE SI UTILIZAN JPQL 
     * 
     * https://www.objectdb.com/java/jpa/query
     */
    
    /**
     * Borrado de un usuario utilizando JPQL según su identificador
     * 
     * @param idUsuario Identificador de usuario
     */
    public static void borrarDatosConDelete(int idUsuario){
        Query query = entitymanager.createQuery("DELETE FROM Usuarios u WHERE u.idusuario = :idUsuarioV");
        query.setParameter("idUsuarioV", idUsuario);

        entitymanager.getTransaction().begin();
        int deletedCount = query.executeUpdate();        
        entitymanager.getTransaction().commit();   

        consultaDatosUsuario(idUsuario);         
   }
    
    /**
     * Modifica el nombre de un usuario utilizando JPQL
     * 
     * @param idUsuario Identificador del usuario a modificar
     * @param nombre Nuevo nombre para el usuario 
     */
    public static void modificarDatosConUpdate(int idUsuario, String nombre){
         Query query = entitymanager.createQuery("UPDATE Usuarios u SET u.nombre= :nombreV  WHERE u.idusuario = :idUsuarioV");
         query.setParameter("nombreV", nombre);
         query.setParameter("idUsuarioV", idUsuario);

         entitymanager.getTransaction().begin();
         int updateCount = query.executeUpdate();        
         entitymanager.getTransaction().commit();           
    }   
    
    /**
     * Muestra los datos de un usuario utilizando JPQL
     * 
     * @param idUsuario Identificador del usuairo a mostrar 
     */
    public static void consultaDatosUsuarioConJPQL(int idUsuario){
                
        Usuarios usuario = null;
        
        TypedQuery<Usuarios> query = entitymanager.createQuery("Select u from Usuarios u WHERE u.idusuario=:IDUSUARIOP", Usuarios.class);
        query.setParameter("IDUSUARIOP", idUsuario);
        

        try{
            usuario = query.getSingleResult();
        
            System.out.print("USUARIO \t");
            System.out.print("CONTRASEÑA");
            System.out.println();

            System.out.print(usuario.getNombre()+"\t");
            System.out.print("\t"+usuario.getContra());
            System.out.println();

                    
        } catch (NoResultException e){
            System.out.println("El usuario no existe");
        }
    }
    
    /**
     * Muestra los pedidos de un determinado usuario utilizando JPQL
     * 
     * @param idUsuario Identificador del usuario 
     */
    public static void consultaDatosUsuarioYPedidosConJPQL(int idUsuario){
                
        Usuarios usuario = null;
        
        TypedQuery<Usuarios> query = entitymanager.createQuery("Select u from Usuarios u WHERE u.idusuario=:IDUSUARIOP", Usuarios.class);
        query.setParameter("IDUSUARIOP", idUsuario);
        

        try{
            usuario = query.getSingleResult();
        
            System.out.print("USUARIO \t");
            System.out.print("CONTRASEÑA");
            System.out.println();

            System.out.print(usuario.getNombre()+"\t");
            System.out.print("\t"+usuario.getContra());
            System.out.println();

            Pedidos p;
            List<Pedidos> coleccion = usuario.getPedidosCollection();
            Iterator<Pedidos> it = coleccion.iterator();
            while(it.hasNext()){
                p = it.next();
                System.out.println("PEDIDO:");
                System.out.println(p.getIdpedido());
                System.out.println("FECHA DE ENTREGA:");
                System.out.println(p.getFechaEntrega());
                System.out.println("PRECIO TOTAL:");
                System.out.println(p.getPrecioTotal());
            }
                    
        } catch (NoResultException e){
            System.out.println("El usuario no existe");
        }
    }
    
    /**
     * Muestra todos los usuarios utilizando JPQL
     */
    public static void consultaUsuariosConJPQL(){
                
        Usuarios usuario = null;
        
        TypedQuery<Usuarios> query = entitymanager.createQuery("SELECT u FROM Usuarios u", Usuarios.class);
        
        try{
            List<Usuarios> coleccion = query.getResultList();  
            Iterator<Usuarios> it = coleccion.iterator();
            
            System.out.print("USUARIO \t");
            System.out.print("CONTRASEÑA");
            System.out.println();
                
            while(it.hasNext()){           
                usuario = it.next();
                System.out.print(usuario.getNombre()+"\t\t");
                System.out.println(usuario.getContra());
            }
        
        } catch (NoResultException e){
            System.out.println("El usuario no existe");
        }
    }
    
    /**
     * Muestra determinados campos de los usuarios
     */
    private static void consultaVariosCamposConJPQL(){
        TypedQuery<Object[]> query = entitymanager.createQuery("Select u.idusuario,u.nombre from Usuarios u",Object[].class);

        List<Object[]> list = query.getResultList();

        System.out.print("ID USUARIO \t");
        System.out.print("NOMBRE USUARIO");
        System.out.println();
            
        for(Object[] e:list) {
           System.out.print(e[0]+"\t");
           System.out.println("\t"+e[1]);
           System.out.println();
        }
    }
    
    /**
     * Muestra los datos de un usuario utilizando consultas almacenadas en JPQL
     * 
     * @param idUsuario Identificador del usuairo a mostrar 
     */
    public static void consultaNamedDatosUsuario(int idUsuario){
                
        Usuarios usuario = null;
        
        TypedQuery<Usuarios> query = entitymanager.createNamedQuery("USUARIOS.findById", Usuarios.class);
        query.setParameter("IDUSUARIOP", idUsuario);
        

        try{
            usuario = query.getSingleResult();
        
            System.out.print("USUARIO \t");
            System.out.print("CONTRASEÑA");
            System.out.println();

            System.out.print(usuario.getNombre()+"\t");
            System.out.print("\t"+usuario.getContra());
            System.out.println();

                   
        } catch (NoResultException e){
            System.out.println("El usuario no existe");
        }
    }
        
    /**
     * Muestra todos los usuarios utilizando consultas almacenadas en JPQL
     */
    public static void consultaNamedUsuarios(){
                
        Usuarios usuario = null;
        
        TypedQuery<Usuarios> query = entitymanager.createNamedQuery("USUARIOS.findAll", Usuarios.class);
        
        
        try{
            List<Usuarios> coleccion = query.getResultList();  
            Iterator<Usuarios> it = coleccion.iterator();
            
            System.out.print("USUARIO \t");
            System.out.print("CONTRASEÑA");
            System.out.println();
                
            while(it.hasNext()){           
                usuario = it.next();
                System.out.print(usuario.getNombre()+"\t");
                System.out.print("\t"+usuario.getContra());
                System.out.println();
            }

        } catch (NoResultException e){
            System.out.println("El usuario no existe");
        }
    }
      
    /**
     * Vamos a hacer la siguiente consulta con CriteriaQuery: 
     * 
     * Select u from Usuarios u
     */
    public static void consultaConCriteriaQuery(){
                
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder(); //Fábrica de criterios

        CriteriaQuery<Usuarios> query = cb.createQuery(Usuarios.class); //Representa la consulta 
        
        Root<Usuarios> u = 
                query.from(Usuarios.class); //Especificamos el from  
                query.select(u); //Indicamos los campos a seleccionar
   
        
        List<Usuarios> list = entitymanager.createQuery(query).getResultList();

        for( Usuarios e:list ){
           System.out.println("Usuario NAME :" + e.getNombre());
        }
    }
    
    /**
     * Vamos a hacer la siguiente consulta con CriteriaQuery: 
     * 
     * Select d.dnombre,d.loc from Departamentos d
     */
    public static void consultaConCriteriaQueryVariosCampos(){
                
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder(); //Fábrica de criterios

        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class); //Representa la consulta 
        
        Root<Usuarios> u = 
                query.from(Usuarios.class); //Especificamos el from  
                query.select(cb.array(u.get("nombre"),u.get("contra"))); //Indicamos los campos a seleccionar
             
        List<Object[]> list = entitymanager.createQuery(query).getResultList();

        for( Object[] e:list ){
           System.out.println("Nombre :"+e[0]);
           System.out.println("Contraseña :"+e[1]);
        }
    }
     
}
