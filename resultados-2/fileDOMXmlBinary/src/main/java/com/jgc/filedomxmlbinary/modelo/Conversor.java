/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.filedomxmlbinary.modelo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
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
import org.w3c.dom.Text;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 16 oct 2024
 */
public class Conversor {
 // atributos & constantes =>
   // constantes - tamaño datos ->
  private final int LONGITUD_LONG = 8;
  private final int LONGITUD_DOUBLE = 8;
  private final int LONGITUD_INT = 4;
  private final int LONGITUD_CHAR = 2;
  
   // constantes - tamaño apellido ->
  final int CARACTERES_APELLIDO = 10;
  
   // constantes - tamaño componentes registro ->
  final int LONGITUD_IDENTIFICADOR = LONGITUD_LONG;
  final int LONGITUD_APELLIDO = (CARACTERES_APELLIDO * LONGITUD_CHAR);
  final int LONGITUD_DEPARTAMENTO = LONGITUD_INT;
  final int LONGITUD_SALARIO = LONGITUD_DOUBLE;
  
   // constantes - tamaño registro ->
  final int LONGITUD_TOTAL = (LONGITUD_IDENTIFICADOR + LONGITUD_APELLIDO + LONGITUD_DEPARTAMENTO + LONGITUD_SALARIO);
  
   // atributos ->
  private File path;
  private String xmlFilename;
  Document documento;
  DocumentBuilderFactory docFactory;
  DocumentBuilder docBuilder;

 //——————————————————————————————————————————————————————————————————————
 // constructores =>
  public Conversor (String inputPath, String inputMainNode) {
    this.path = new File(inputPath);
    this.xmlFilename = inputMainNode;
    generateDOMContent(inputMainNode);
  }
  
 //——————————————————————————————————————————————————————————————————————
  // metodos privados =>
   // metodo "generateDOMContent" | crea el fichero en memoria ->
  private void generateDOMContent (String inputMainNode) {
    try {
      this.docFactory = DocumentBuilderFactory.newInstance();
      docFactory.setIgnoringElementContentWhitespace(true);
      this.docBuilder = this.docFactory.newDocumentBuilder();
      
      DOMImplementation domImplementation = this.docBuilder.getDOMImplementation();
      this.documento = (Document) domImplementation.createDocument(null, inputMainNode, null);
      this.documento.setXmlVersion("1.0");
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    System.out.println(" > documento '" + inputMainNode + "' creado en memoria.");
  }
  
   // metodo "generateDOMContent" | crea el fichero en memoria ->
  private Transformer preProcess () {
    Transformer domTransformer = null;
    
    try {
      domTransformer = TransformerFactory.newInstance().newTransformer();
      domTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
    } catch (TransformerConfigurationException ex) {
      Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return domTransformer;
  }
  
   // metodo "addNodo" | agrega un nodo principal al XML ->
  public Element addNodo (String inputNombreNodo) {
    Element mainNodo = this.documento.createElement(inputNombreNodo);
    this.documento.getDocumentElement().appendChild(mainNodo);
    return mainNodo;
  }
  
   // metodo "addNodoTexto" | agrega a un nodo un valor ->
  public void addNodoTexto(String inputDatoEmple, String inputTexto, Element nodoRoot) {
    Element dato = this.documento.createElement(inputDatoEmple);
    Text textoData = this.documento.createTextNode(limpiarTexto(inputTexto));
    
    dato.appendChild(textoData);
    nodoRoot.appendChild(dato);
  }
  
   // metodo "limpiarTexto" | limpia las cadenas invalidas ->
  private String limpiarTexto(String texto) {
    if (texto == null) {
        return "";
    }
    return texto.replaceAll("[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F]", "");
}
  
 //——————————————————————————————————————————————————————————————————————
  // metodos publicos =>
   // metodo "generateFileFromDOM" | genera un fichero real basado en el de memoria ->
  public void generateFileFromDOM (String inputFilename) {
    try {
      Source domSource = new DOMSource(this.documento);
      Result domResult = new StreamResult(new File(inputFilename));
      
      preProcess().transform(domSource, domResult);
    } catch (TransformerException ex) {
      Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   // metodo "fromBinaryToXML" | transforma la lista en elementos de un xml ->
  public void fromEmpleadoToXML(List<Empleado> inputListaEmpleados) {
    for (Empleado tempEmple : inputListaEmpleados) {
      long tempIdentificador = tempEmple.getIdentificador();
      String tempApellido = limpiarTexto(tempEmple.getApellido());
      int tempDepartamento = tempEmple.getDepartamento();
      Double tempSalario = tempEmple.getSalario();
      String tempCargo = limpiarTexto(tempEmple.getCargo());
        
      Element elemTempEmple = addNodo("Empleado");
        
      addNodoTexto("Identificador", Long.toString(tempIdentificador), elemTempEmple);
      addNodoTexto("Apellido", tempApellido, elemTempEmple);
      addNodoTexto("Departamento", Integer.toString(tempDepartamento), elemTempEmple);
      addNodoTexto("Salario", Double.toString(tempSalario), elemTempEmple);
      addNodoTexto("Cargo", tempCargo, elemTempEmple);
    }
    generateFileFromDOM("./Resources/" + this.xmlFilename + ".xml");
    System.out.println("\n > Fichero Binario pasado a XML.");
}
  
   // metodo "lecturaEmpleados" | devuelve una lista de empleados ->
  public List<Empleado> lecturaEmpleados() {
    List<Empleado> empleadoList = new ArrayList<>();
    
    try (RandomAccessFile randomFile = new RandomAccessFile(getPath(), "r")) {
      while (randomFile.getFilePointer() < randomFile.length()) {
        Empleado tempEmpleado = new Empleado();
            
        tempEmpleado.setIdentificador(randomFile.readLong());
            
        byte[] cadena = new byte[getLongitudApellido()];
        randomFile.readFully(cadena);
        tempEmpleado.setApellido(limpiarTexto(new String(cadena, "UTF-8").trim()));
            
        tempEmpleado.setDepartamento(randomFile.readInt());
        tempEmpleado.setSalario(randomFile.readDouble());
            
        empleadoList.add(tempEmpleado);
      }
    } catch (IOException ex) {
      Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return empleadoList;
  }
  
 //——————————————————————————————————————————————————————————————————————
 // getters & setters =>
   // getters ->
  public int getCaracteresApellido () {
    return CARACTERES_APELLIDO;
  }
  
  public int getLongitudApellido () {
    return LONGITUD_APELLIDO;
  }
  
  public int getLongitudTotal () {
    return LONGITUD_TOTAL;
  }
  
  public File getPath () {
    return this.path;
  }

   // setters ->
  public void setPath (String inputPath) {
    this.path = new File(inputPath);
  }
}
