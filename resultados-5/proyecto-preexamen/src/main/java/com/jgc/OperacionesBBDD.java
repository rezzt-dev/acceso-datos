/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc;

import java.io.StringWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultItem;
import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;
import org.w3c.dom.Node;

/**
 *
 * @author rezzt
 */
public class OperacionesBBDD {
   // constantes & atributos ->
  private XQDataSource server;
  private XQConnection connection;
  
  private TransformerFactory tFactory;
  private Transformer transformer;
  
 //-----------------------------------------------------------------------------------------------------------------------------
   // constructores ->
  public OperacionesBBDD () {
    try {
      this.tFactory = TransformerFactory.newInstance();
      this.transformer = tFactory.newTransformer();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
 //-----------------------------------------------------------------------------------------------------------------------------
   // metodos publicos ->
    // metodo de abrir conexion con la bbdd =>
  public void abrirConexion () {
    try {
      server = new ExistXQDataSource();
    
      server.setProperty("serverName", "localhost");
      server.setProperty("port", "8080");
      server.setProperty("user", "dam2");
      server.setProperty("password", "0024");

      connection = server.getConnection();
    } catch (XQException ex) {
      ex.printStackTrace();
    }
  } 
  
    // metodo de abrir conexion con la bbdd =>
  public void cerrarConexion () {
    try {
      connection.close();
    } catch (XQException ex) {
      ex.printStackTrace();
    }
  }
  
 //-----------------------------------------------------------------------------------------------------------------------------
   // metodos privados -> ayuda
    // metodo eliminar cabeceras del xml =>
  private String eliminarNamespace (XQResultItem inputItem) {
    StringWriter writer = new StringWriter();
    
    try {
      Node node = (Node) inputItem.getNode();
      
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      transformer.transform(new DOMSource(node), new StreamResult(writer));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    return writer.toString();
  }
  
  private void realizarConsulta (String inputConsulta) {
    try {
      XQPreparedExpression xqConsulta = connection.prepareExpression(inputConsulta);
      XQResultSequence xqResultado = xqConsulta.executeQuery();
      
      XQResultItem resultItem;
      while (xqResultado.next()) {
        resultItem = (XQResultItem) xqResultado.getItem();
        System.out.println(eliminarNamespace(resultItem));
      }
    } catch (XQException ex) {
      ex.printStackTrace();
    }
  }
  
  private boolean existeNodo (String inputConsulta) {
    boolean nodoExiste = false;
    
    try {
      XQExpression xqConsulta = connection.createExpression();
      String cadenaConsulta = "exists(" + inputConsulta + ")";
      XQResultSequence xqResultado = xqConsulta.executeQuery(cadenaConsulta);
      
      if (xqResultado.next() && xqResultado.getBoolean()) {
        nodoExiste = false;
      } else {
        nodoExiste = true;
      }
      
    } catch (XQException ex) {
      ex.printStackTrace();
    }
    
    return nodoExiste;
  }
  
 //-----------------------------------------------------------------------------------------------------------------------------
   // metodos publicos ->
    // metodo | creacion de un empleado =>
  public void crearEmpleado (int inputSalario, String inputPuesto, String inputNombre) {
    try {
      XQExpression xqConsulta = connection.createExpression();
      String cadenaConsulta = "update insert <empleado salario='" + inputSalario + "'><puesto>" + inputPuesto + "</puesto>" + 
              "<nombre>" + inputNombre + "</nombre></empleado> into /universidad/departamento[2]";
      
      xqConsulta.executeCommand(cadenaConsulta);
    } catch (XQException ex) {
      ex.printStackTrace();
    }
  }
  
    // metodo | actualizar el salario a los empleados =>
  public void actualizarSalarioEmpleados () {
    try {
      XQExpression xqConsulta = connection.createExpression();
      String cadenaConsulta = "for $em in /universidad/departamento[codigo='MAT1']/empleado" +
              " let $salario = data($em/@salario)" + " return update value $em/@salario with data($sal)+100";
      
      xqConsulta.executeCommand(cadenaConsulta);
    } catch (XQException ex) {
      ex.printStackTrace();
    }
  }
  
    // metodo | leer codigo de departamento y mostrar sus empleado =>
  public void mostrarDepartamento () {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print(" -> Introduce el codigo del departamento: ");
    String codDept = scanner.nextLine();
    
    String cadenaConsulta = "/universidad/departamento[codigo='" + codDept + "']";
    realizarConsulta(cadenaConsulta);
  }
  
    // metodo | insercion de departamento =>
  public void insertarDepartamento () {
    try {
      Scanner scanner = new Scanner(System.in);
      boolean operacionRealizada = false;
      XQExpression xqConsulta = connection.createExpression();
      
      System.out.print(" -> ingresa el codigo del departamento: ");
      String deptNo = scanner.nextLine();
      
      System.out.print(" -> ingresa el nombre del departamento: ");
      String deptNombre = scanner.nextLine();
      
      System.out.print(" -> ingresa la localizacion del departamento: ");
      String deptLoc = scanner.nextLine();
      
      while (operacionRealizada == false) {
        String cadenaConsultaTestDept = "/departamento/DEP_ROW[DEPT_NO='" + deptNo + "']";
        
        try {
          if (existeNodo(cadenaConsultaTestDept)) {
            String consultaInsercion = "update insert <DEPT_ROW><DEPT_NO>" + deptNo + "</DEPT_NO>" +
                    "<DNOMBRE>" + deptNombre + "</DNOMBRE><LOC>" + deptLoc + "</LOC></DEPT_ROW> into /departamentos";
            
            xqConsulta.executeCommand(consultaInsercion);
            System.out.println("  --> departamento insertado correctamente.");
            operacionRealizada = true;
          }
        } catch (XQException ex) {
          System.err.println("  --> ha ocurrido un problema al insertar el departamento");
        }
      }
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
    // metodo | modificar un departamento =>
  public void modificarDepartamento () {
    try {
      Scanner scanner = new Scanner(System.in);
      boolean operacionRealizada = false;
      XQExpression xqConsulta = connection.createExpression();
      
      System.out.print(" -> ingresa el codigo del departamento: ");
      String deptNo = scanner.nextLine();
      
      System.out.print(" -> ingresa el nuevo nombre del departamento: ");
      String deptNombre = scanner.nextLine();
      
      System.out.print(" -> ingresa la nueva localizacion del departamento: ");
      String deptLoc = scanner.nextLine();
      
      while (operacionRealizada == false) {
        String cadenaConsultaTestDept = "/departamento/DEP_ROW[DEPT_NO='" + deptNo + "']";
        
        try {
          if (existeNodo(cadenaConsultaTestDept)) {
            String updateQuery = "update value /departamentos/DEP_ROW[DEPT_NO='" + deptNo + "']/DNOMBRE with '" + deptNombre + "'";
            xqConsulta.executeCommand(updateQuery);
            
            updateQuery = "update value /departamentos/DEP_ROW[DEPT_NO='" + deptNo + "']/LOC with '" + deptLoc + "'";
            xqConsulta.executeCommand(updateQuery);
            
            System.out.println("  --> departamento modificado correctamente.");
            operacionRealizada = true;
          } else {
            System.out.println("  --> no se puede realizar la modificacion porque no existe un departamento con ese codigo.");
            operacionRealizada = true;
          }
        } catch (XQException ex) {
          ex.printStackTrace();
        }
      }
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
    // metodo | borrar un departamento =>
  public void eliminarDepartamento () {
    try {
      Scanner scanner = new Scanner(System.in);
      boolean operacionRealizada = false;
      XQExpression xqConsulta = connection.createExpression();
      
      System.out.print(" -> ingresa el codigo del departamento: ");
      String deptNo = scanner.nextLine();
      
      while (operacionRealizada == false) {
        String cadenaConsultaTestDept = "/departamento/DEP_ROW[DEPT_NO='" + deptNo + "']"; 
        
        if (existeNodo(cadenaConsultaTestDept)) {
          String deleteQuery = "update delete /departamentos/DEP_ROW[DEPT_NO='" + deptNo + "']";
          xqConsulta.executeCommand(deleteQuery);
        
          System.out.println("  - departamento borrado correctamente.");
          operacionRealizada = false;
        } else {
          System.out.println("  --> no existe un departamento con ese codigo.");
          operacionRealizada = true;
        }
      }
    } catch (XQException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
 //-----------------------------------------------------------------------------------------------------------------------------
  
}
