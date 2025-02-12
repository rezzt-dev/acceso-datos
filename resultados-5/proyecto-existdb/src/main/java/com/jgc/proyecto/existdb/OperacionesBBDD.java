/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.proyecto.existdb;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultItem;
import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;


/***
  import javax.xml.xquery.XQConnection;
  import javax.xml.xquery.XQDataSource;
  import javax.xml.xquery.XQException;
  import javax.xml.xquery.XQPreparedExpression;
  import javax.xml.xquery.XQResultItem;
  import javax.xml.xquery.XQResultSequence;
  import net.xqj.exist.ExistXQDataSource;
 */

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 12 feb 2025
 */
public class OperacionesBBDD {
   // variables para la conexion de la bbdd ->
  private XQDataSource server;
  private XQConnection connection;
  
 //-----------------------------------------------------------------------------------------------------------------------------------------
   // metodos de manejo de conexiones ->
  public void abrirConexion () {
    try {
      server = new ExistXQDataSource();
      
      server.setProperty("serverName", "localhost");
      server.setProperty("port", "8080");
      server.setProperty("user", "dam2");
      server.setProperty("password", "dam2");
      
      connection = server.getConnection();    
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void cerrarConexion () {
    try {
      connection.close();
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
 //-----------------------------------------------------------------------------------------------------------------------------------------
   // metodos simples de consultas a la bbdd ->
  public void consulta (String inputTextoConsulta) {
    try {
      XQPreparedExpression xqConsulta;
      XQResultSequence xqResultado;
      
      xqConsulta = connection.prepareExpression(inputTextoConsulta);
      xqResultado = xqConsulta.executeQuery();
      
      XQResultItem resultItem;
      while (xqResultado.next()) {
        resultItem = (XQResultItem) xqResultado.getItem();
        System.out.println(resultItem.getItemAsString(null));
      }
      
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
 //-----------------------------------------------------------------------------------------------------------------------------------------
   // metodos simples de modificacion a la bbdd ->
  public void modificacion (String inputTextoConsulta) {
     // "update rename " + "/EMPLEADOS/fila_emple2 " + "as " + "'fila_emple'"
     
    try {
      XQExpression xqConsulta;
      xqConsulta = connection.createExpression();
      
      xqConsulta.executeCommand(inputTextoConsulta);
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
 //-----------------------------------------------------------------------------------------------------------------------------------------
   // metodos | ejercicios de java a la bbdd xml ->
    // metodo de creacion de un empleado en el fichero universidad.xml =>
  public void creacionEmpleado(int inputSalario, String inputPuesto, String inputNombre) {
    try {
      XQExpression xqConsulta = connection.createExpression();
        
      String consulta = "update insert <empleado salario='" + inputSalario +"'><puesto>" + inputPuesto + "</puesto><nombre>" + inputNombre + 
              "</nombre></empleado> into /universidad/departamento[2]";

      xqConsulta.executeCommand(consulta);
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   // Actualiza el salario de los empleados del departamento con código MAT1 sumándoles 100€.
  public void actualizarSalarioEmpleados () {
    try {
      XQExpression xqConsulta = connection.createExpression();
        
      String consulta = "update replace /universidad/departamento[codigo='MAT1']/empleado/@salario with /universidad/departamento[codigo='MAT1']/empleado/@salario + 100";

      xqConsulta.executeCommand(consulta);
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   // Crea un método que lea de teclado un departamento y visualice sus empleados.
  public void lecturaDepartamento () {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print(" -> Introduce el codigo del departamento: ");
    String codDept = scanner.nextLine();
    
    try {
      XQExpression xqConsulta = connection.createExpression();
      String consulta = "/universidad/departamento[codigo='" + codDept + "']";

      XQResultSequence xqResultado = xqConsulta.executeQuery(consulta);
      XQResultItem resultItem;
      while (xqResultado.next()) {
        resultItem = (XQResultItem) xqResultado.getItem();
        System.out.println(resultItem.getItemAsString(null));
      }
      
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}
