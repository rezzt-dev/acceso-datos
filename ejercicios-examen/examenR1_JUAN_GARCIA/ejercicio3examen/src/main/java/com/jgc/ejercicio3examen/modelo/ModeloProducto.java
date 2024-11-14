/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.ejercicio3examen.modelo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 nov 2024
 */
public class ModeloProducto {
  Document documento;
  DocumentBuilder docBuilder;
  DocumentBuilderFactory docFactory;
  
  public void agregarDatos (String inputNombre, int inputCant, String inputNombreSuper) {
    utilidadCargarFicheroMemoria("./RESOURCES/listaCompra.xml");
    boolean operRealizada = false;
   
    NodeList nodeList = this.documento.getElementsByTagName("producto");
    
    for (int i=0; i<nodeList.getLength(); i++) {
      Element tempProduct = (Element) nodeList.item(i);
      
      String nombreProd = utilidadGetTagValue("nombre", tempProduct);
      if (nombreProd.equalsIgnoreCase(inputNombre)) {
        NodeList cantNodeList = tempProduct.getElementsByTagName("cantidad");
        NodeList superNodeList = tempProduct.getElementsByTagName("supermercado");
        
        if (cantNodeList.getLength() > 0) {
          Element cantidad = (Element) cantNodeList.item(0);
          cantidad.setTextContent(Integer.toString(inputCant));
        } else {
          utilidadAddNodoTexto("cantidad", String.valueOf(inputCant), tempProduct);
        }
        
        if (superNodeList.getLength() > 0) {
          Element nomSuper = (Element) superNodeList.item(0);
          nomSuper.setTextContent(inputNombreSuper);
        } else {
          utilidadAddNodoTexto("supermercado", inputNombreSuper, tempProduct);
        }
        
      } else {
        NodeList nomNodeList = tempProduct.getElementsByTagName("nombre");
        
        NodeList cantNodeList = tempProduct.getElementsByTagName("cantidad");
        NodeList superNodeList = tempProduct.getElementsByTagName("supermercado");
        
        if (nomNodeList.getLength() > 0) {
          Element nombre = (Element) cantNodeList.item(0);
          nombre.setTextContent(inputNombre);
        } else {
          utilidadAddNodoTexto("cantidad", inputNombre, tempProduct);
        }
        
        if (cantNodeList.getLength() > 0) {
          Element cantidad = (Element) cantNodeList.item(0);
          cantidad.setTextContent(Integer.toString(inputCant));
        } else {
          utilidadAddNodoTexto("cantidad", String.valueOf(inputCant), tempProduct);
        }
        
        if (superNodeList.getLength() > 0) {
          Element nomSuper = (Element) superNodeList.item(0);
          nomSuper.setTextContent(inputNombreSuper);
        } else {
          utilidadAddNodoTexto("supermercado", inputNombreSuper, tempProduct);
        }
      }
    }
    
    if (operRealizada == true) {
      utilidadGenerarArchivoDOM("./RESOURCES/listaCompra.xml");
      utilidadMostrarEnPantalla();
    }
  }
  
  private Element utilidadAddNodo (String inputNombreNodo) {
    Element mainNodo = this.documento.createElement(inputNombreNodo);
    this.documento.getDocumentElement().appendChild(mainNodo);
    return mainNodo;
  }
  
  private void utilidadAddNodoTexto (String inputDatoHijo, String inputTexto, Element inputRootNode) {
    Element elemData = this.documento.createElement(inputDatoHijo);
    String cleanedText = utilidadCleanInvalidXmlChars(inputTexto);
    Text textData = this.documento.createTextNode(cleanedText);
    elemData.appendChild(textData);
    inputRootNode.appendChild(elemData);
  }
  
  private String utilidadGetTagValue (String inputTag, Element inputElem) {
    NodeList nodeList = inputElem.getElementsByTagName(inputTag).item(0).getChildNodes();
    Node tempNode = nodeList.item(0);
    
    if (tempNode != null) {
      return tempNode.getNodeValue();
    } else {
      return null;
    }
  }
  
  private String utilidadCleanInvalidXmlChars(String text) {
    return text.replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFFF]", "");
  }
  
  private Transformer utilidadPreProcess (String inputIndent) {
    Transformer transformer =null;
    
    try {
      transformer = TransformerFactory.newInstance().newTransformer();
    } catch (TransformerConfigurationException ex) {
      Logger.getLogger(ModeloProducto.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    transformer.setOutputProperty(OutputKeys.INDENT, inputIndent);
    return transformer;
  }
  
  private void utilidadGenerarArchivoDOM(String inputFilename) {
    Source source = new DOMSource(this.documento);
    Result salida = new StreamResult(new File(inputFilename));
    
    try {
      utilidadPreProcess("yes").transform(source, salida); //"no" porque queremos que todo este en la misma linea
    } catch (TransformerException ex) {
      Logger.getLogger(ModeloProducto.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private void utilidadMostrarEnPantalla () {
    try {
      Source source = new DOMSource(this.documento);
      Result salida = new StreamResult(System.out);

      utilidadPreProcess("yes").transform(source, salida);//"yes" para que indente el XML

    } catch (TransformerConfigurationException ex) {
      Logger.getLogger(ModeloProducto.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
      Logger.getLogger(ModeloProducto.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private void utilidadCargarFicheroMemoria (String inputFilename) {
    this.docFactory = DocumentBuilderFactory.newInstance();
    docFactory.setIgnoringElementContentWhitespace(true);
    
    try {
      this.docBuilder = this.docFactory.newDocumentBuilder();
      this.documento = this.docBuilder.parse(new File(inputFilename));
      this.documento.getDocumentElement().normalize();
      
    } catch (SAXException | IOException ex) {
      Logger.getLogger(ModeloProducto.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(ModeloProducto.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
