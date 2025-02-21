/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.proyectoexamen;

import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQItemType;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultItem;
import javax.xml.xquery.XQResultSequence;
import net.xqj.exist.ExistXQDataSource;
import org.w3c.dom.Node;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 21 feb 2025
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
      server.setProperty("password", "dam2");

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
    // metodo eliminar cabeceras de la respuesta =>
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
  
 //-----------------------------------------------------------------------------------------------------------------------------
   // metodos publicos ->
    // metodo | mostrar clientes de la bbdd =>
  public void MostrarClientes () {
    String inputConsulta = "/clientes/clien/(string(nombre),string(poblacion),string(tlf),string(direccion))";
    
    try {
      XQPreparedExpression xqConsulta = connection.prepareExpression(inputConsulta);
      XQResultSequence xqResultado = xqConsulta.executeQuery();
      
      XQResultItem resultItem;
      while (xqResultado.next()) {
        resultItem = (XQResultItem) xqResultado.getItem();

        if (resultItem.getItemType().getBaseType() == XQItemType.XQBASETYPE_STRING) {
          System.out.println(resultItem.getAtomicValue());
        } else {
          System.out.println(eliminarNamespace(resultItem));
        }
      }
    } catch (XQException ex) {
      ex.printStackTrace();
    }
  }
  
    
    // metodo | eliminar productos =>
  public void EliminarProductos () {
    String inputConsulta = "update delete /productos/product[@categoria='A' and number(@pvp)<200]";
    
    try {
      XQExpression xqConsulta = connection.createExpression();
      
      xqConsulta.executeCommand(inputConsulta);
      System.out.println("  - productos borrados correctamente.");
    } catch (XQException ex) {
      ex.printStackTrace();
    }
  }
}
