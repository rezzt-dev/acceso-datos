/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.filedomxml.modelo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 * Created on 16 oct 2024
 */
public class GestionContenidoDOM {
 // atributos & constantes =>
  Document documento;
  DocumentBuilderFactory docFactory;
  DocumentBuilder docBuilder;

 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public GestionContenidoDOM (String inputNombre) {
    try {
      this.docFactory = DocumentBuilderFactory.newInstance();
      docFactory.setIgnoringElementContentWhitespace(true);
      this.docBuilder = this.docFactory.newDocumentBuilder();
      
      DOMImplementation domImplementation = this.docBuilder.getDOMImplementation();
      this.documento = (Document) domImplementation.createDocument(null, inputNombre, null);
      this.documento.setXmlVersion("1.0");
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(GestionContenidoDOM.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    System.out.println(" > documento '" + inputNombre + "' creado en memoria.");
  }
  
 //——————————————————————————————————————————————————————————————————————
  // metodos privados =>
   // metodo "preProcess" | crea y devuelve un transformer ->
  private Transformer preProcess (boolean indent) {
    Transformer domTransformer = null;
    
    try {
      domTransformer = TransformerFactory.newInstance().newTransformer();
    } catch (TransformerConfigurationException ex) {
      Logger.getLogger(GestionContenidoDOM.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    if (indent == true) {
      domTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
    } else {
      domTransformer.setOutputProperty(OutputKeys.INDENT, "no");
    }
    
    return domTransformer;
  }
  
 //——————————————————————————————————————————————————————————————————————
  // metodos publicos =>
   // metodo "addNodo" | agrega un nodo principal al XML ->
  public Element addNodo (String inputNombreNodo) {
    Element mainNodo = this.documento.createElement(inputNombreNodo);
    this.documento.getDocumentElement().appendChild(mainNodo);
    return mainNodo;
  }
  
   // metodo "addNodo" | agrega un nodo hijo a otro nodo ->
  public Element addNodo (String inputDatoEmple, Element nodoPadre) {
    Element dato = this.documento.createElement(inputDatoEmple);
    nodoPadre.appendChild(dato);
    
    return dato;
  }
  
   // metodo "addNodoTexto" | agrega a un nodo un valor ->
  public void addNodoTexto (String inputDatoEmple, String inputTexto, Element nodoRoot) {
    Element dato = this.documento.createElement(inputDatoEmple);
    Text textoData = this.documento.createTextNode(inputTexto);
    
    dato.appendChild(textoData);

    nodoRoot.appendChild(dato);
  }
  
   // metodo "screenPrinter" | muestra en pantalla el resultado ->
  public void screenPrinter () {
    try {
      Source domSource = new DOMSource(this.documento);
      Result domResult = new StreamResult(System.out);
     
      preProcess(false).transform(domSource, domResult);
    } catch (TransformerException ex) {
      Logger.getLogger(GestionContenidoDOM.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   // metodo "generateFileFromDOM" | genera un fichero real basado en el de memoria ->
  public void generateFileFromDOM (String inputFilename) {
    try {
      Source domSource = new DOMSource(this.documento);
      Result domResult = new StreamResult(new File(inputFilename));
      
      preProcess(true).transform(domSource, domResult);
    } catch (TransformerException ex) {
      Logger.getLogger(GestionContenidoDOM.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   // metodo "chargeFileInMemory" | carga un fichero en memoria ->
  public void chargeFileInMemory (String inputFilename) {
    try {
      this.documento = this.docBuilder.parse(new File(inputFilename));
      this.documento.getDocumentElement().normalize();
      
    } catch (SAXException | IOException ex) {
      Logger.getLogger(GestionContenidoDOM.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   // metodo "addCargoToAllEmpleados" | agrega el elemento cargo a empleado ->
  public void addCargoToAllEmpleados () {
    NodeList empleadoList = this.documento.getElementsByTagName("Empleado");
    
    for (int i=0; i<empleadoList.getLength(); i++) {
      Element tempEmpleado =  (Element) empleadoList.item(i);
      
      if (tempEmpleado.getElementsByTagName("Cargo").getLength() == 0) {
        addNodoTexto("Cargo", "Por Especificar", tempEmpleado);
      }
    }
    
    System.out.println(" > Agregado el elemento 'Cargo' a todos los empleados.");
  }
  
   // metodo "deleteElementFromEmpleado" | elimina un elemento especifico de los empleados ->
  public void deleteElementFromEmpleado (String inputDeleteElem) {
    NodeList empleadoList = this.documento.getElementsByTagName("Empleado");
    
    for (int i=0; i < empleadoList.getLength(); i++) {
      Element tempEmpleado = (Element) empleadoList.item(i);
      NodeList elementsToDelete = tempEmpleado.getElementsByTagName(inputDeleteElem);
      
      for (int j = elementsToDelete.getLength() - 1; j >= 0; j--) { // recorrido inverso | evitar problemas con indices
        Node nodeToDelete = elementsToDelete.item(j);
        tempEmpleado.removeChild(nodeToDelete);
      }
    }
    
    System.out.println(" > Elemento '" + inputDeleteElem + "' eliminado a todos los empleados.");
  }
  
   // metodo "modifySalarioFromEmpleado" | modifica el salario de un empleado especifico ->
  public void modifySalarioFromEmpleado (long inputIdentificador, Double inputSalario) {
    NodeList empleadoList = this.documento.getElementsByTagName("Empleados");
    
    for (int i=0; i<empleadoList.getLength(); i++) {
      Element tempEmpleado = (Element) empleadoList.item(i);
      
      String tempIdentificador = getTagValue("Identificador", tempEmpleado);
      if (tempIdentificador != null && Long.parseLong(tempIdentificador) == inputIdentificador) {
        NodeList salarioList = tempEmpleado.getElementsByTagName("Salario");
        
        if (salarioList.getLength() > 0) {
          Element salarioElement = (Element) salarioList.item(0);
          salarioElement.setTextContent(Double.toString(inputSalario));
        } else {
          addNodoTexto("Salario", Double.toString(inputSalario), tempEmpleado);
        }
        
        System.out.println(" > Salario modificado para el empleado con ID: " + tempIdentificador);
        return;
      }
    }
    
    System.out.println(" > No se encontro ningun empleado con el identificador: " + inputIdentificador);
  }
  
   // metodo "modifySalarioFromEmpleado" | modifica el salario de un empleado especifico ->
  public void createNewEmpleado (long inputIdentificador, String inputApellido, int inputDepartamento, Double inputSalario, String inputCargo) {
    Element newEmpleado = addNodo("Empleado");
    addNodoTexto("Identificador", Long.toString(inputIdentificador), newEmpleado);
    addNodoTexto("Apellido", inputApellido, newEmpleado);
    addNodoTexto("Departamento", Integer.toString(inputDepartamento), newEmpleado);
    addNodoTexto("Salario", Double.toString(inputSalario), newEmpleado);
    addNodoTexto("Cargo", inputCargo, newEmpleado);
  }
          
  public void createNewEmpleado (long inputIdentificador, String inputApellido, int inputDepartamento, Double inputSalario) {
    Element newEmpleado = addNodo("Empleado");
    addNodoTexto("Identificador", Long.toString(inputIdentificador), newEmpleado);
    addNodoTexto("Apellido", inputApellido, newEmpleado);
    addNodoTexto("Departamento", Integer.toString(inputDepartamento), newEmpleado);
    addNodoTexto("Salario", Double.toString(inputSalario), newEmpleado);
  }
  
 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
   // getters ->
  public String getMainElement () {
    return this.documento.getDocumentElement().getNodeName();
  }
  
  private String getTagValue (String inputTag, Element inputElem) {
    NodeList nodeList = inputElem.getElementsByTagName(inputTag).item(0).getChildNodes();
    Node node = nodeList.item(0);
    
    if (node != null) {
      return node.getNodeValue();
    } else return null;
  }
  
  private Empleado getEmpleado (Node inputNode) {
    Empleado returnEmpleado = new Empleado();
    
    if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
      Element tempElement = (Element) inputNode;
      returnEmpleado.setIdentificador(Long.parseLong(getTagValue("Identificador", tempElement)));
      returnEmpleado.setApellido(getTagValue("Apellido", tempElement));
      returnEmpleado.setDepartamento(Integer.parseInt(getTagValue("Departamento", tempElement)));
      returnEmpleado.setSalario(Double.parseDouble(getTagValue("Salario", tempElement)));
      returnEmpleado.setCargo(getTagValue("Cargo", tempElement));
    }
    
    return returnEmpleado;
  }
  
  public List<Empleado> getEmpleados () {
    List<Empleado> empleadoList = null;
    
    empleadoList = new ArrayList<>();
    NodeList nodeList = this.documento.getElementsByTagName("Empleado");
      
    for (int i=0; i<nodeList.getLength(); i++) {
      empleadoList.add(getEmpleado(nodeList.item(i)));
    }
    
    return empleadoList;
  }
}
