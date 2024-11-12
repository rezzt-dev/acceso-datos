/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyecto.practica.examen.modelo;

import java.io.File;
import java.io.FileNotFoundException;
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
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author rezzt
 */
public class FicheroUniversidades {
 // constantes & variables ->
   // atributos clase => fichero universidades ->
  private File path;
  
   // constantes fichero binario ->
  private final int LONGITUD_LONG = 8;
  private final int LONGITUD_DOUBLE = 8;
  private final int LONGITUD_CHAR = 2;
  
  final int CARACTERES_NOMBRE_CARRERA = 20;
  final int CARACTERES_CIUDAD_CARRERA = 20;
  
  final int LONGITUD_ID = LONGITUD_LONG;
  final int LONGITUD_NOMBRE_CARRERA = (LONGITUD_CHAR * CARACTERES_NOMBRE_CARRERA);
  final int LONGITUD_CIUDAD_CARRERA = (LONGITUD_CHAR * CARACTERES_CIUDAD_CARRERA);
  final int LONGITUD_NOTA_CORTE = LONGITUD_DOUBLE;
  
  final int LONGITUD_TOTAL = (LONGITUD_ID + LONGITUD_NOMBRE_CARRERA + LONGITUD_CIUDAD_CARRERA + LONGITUD_NOTA_CORTE);

   // atributos fichero binario a xml ->
  private String filenameXML;
  private Document documento;
  private DocumentBuilder docBuilder;
  private DocumentBuilderFactory docFactory;

 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // constructor | fichero universidades ->
  public FicheroUniversidades (String inputPath) {
    this.path = new File(inputPath);
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 1 | crear arbol de directorios -> 
  public void crearCarpeta (String inputFolderName) {
    File tempFolder = new File("./" + inputFolderName);
    if (!tempFolder.exists()) {
      tempFolder.mkdir();
    }
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 2 | alta datos carreras universitarias -> 
  // metodo | darAltaCarreraUniversitaria -->
  public boolean darAltaCarreraUniversitaria (Universidad inputUniversidad) {
    RandomAccessFile randomFile = null;
    long pos = 0;
    StringBuffer buffer = null;
    
    boolean operacionRealizada = false;
    
    try {
      randomFile = new RandomAccessFile(path, "rw");
      if (carreraExiste(randomFile, inputUniversidad.getIdUni()) == true) {
        operacionRealizada = false;
      } else {
        if (randomFile.length() != 0) {
          pos = randomFile.length();
        }
        
        randomFile.seek(pos);
        randomFile.writeLong(inputUniversidad.getIdUni());
        
        buffer = new StringBuffer(inputUniversidad.getNombreCarrera());
        buffer.setLength(LONGITUD_NOMBRE_CARRERA);
        randomFile.writeChars(buffer.toString());
        
        buffer = new StringBuffer(inputUniversidad.getCiudadCarrera());
        buffer.setLength(LONGITUD_CIUDAD_CARRERA);
        randomFile.writeChars(buffer.toString());
        
        randomFile.writeDouble(inputUniversidad.getNotaCorte());
      }
    } catch (FileNotFoundException ex) {
      Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        if (randomFile != null) {
          randomFile.close();
        }
      } catch (IOException ex) {
        Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
      }
    } 
    
    return operacionRealizada;
  }
  
  // metodo | carreraExiste -->  
  private boolean carreraExiste (RandomAccessFile inputFile, long inputId) {
    boolean existeCarrera = false;
    
    try {
      long currentPos = inputFile.getFilePointer();
      inputFile.seek(0);
      
      while (inputFile.getFilePointer() < inputFile.length()) {
        long carreraUniId = inputFile.readLong();
        if (carreraUniId == inputId) {
          inputFile.seek(currentPos);
          existeCarrera = true;
        }
        
        inputFile.skipBytes(LONGITUD_TOTAL - 8);
      }
      
      inputFile.seek(currentPos);
      existeCarrera = false;
    } catch (IOException ex) {
      Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return existeCarrera;
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 3 | generar archivo xml con carreras universitarias ->
  public boolean generarArchivoXML () {
    boolean operacionRealizada = false;
    
     // crear el fichero en memoria =>
    try {
      this.docFactory = DocumentBuilderFactory.newInstance();
      this.docFactory.setIgnoringElementContentWhitespace(true);
      this.docBuilder = this.docFactory.newDocumentBuilder();
      
      DOMImplementation domImplementation = this.docBuilder.getDOMImplementation();
      this.documento = (Document) domImplementation.createDocument(null, "Carreras", null);
      this.documento.setXmlVersion("1.0");
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    List<Universidad> listaUniversidades = new ArrayList<>();
    RandomAccessFile randomFile = null;
    byte[] cadena = null;
    byte[] cadena2 = null;
    
    try {
      randomFile = new RandomAccessFile(this.path, "r");
      
      while (randomFile.getFilePointer() < randomFile.length()) {
        Universidad tempUniversidad = new Universidad();
        tempUniversidad.setIdUni(randomFile.readLong());
        
        cadena = new byte[LONGITUD_NOMBRE_CARRERA];
        randomFile.readFully(cadena);
        String nombreCarrera = new String(cadena);
        nombreCarrera = nombreCarrera.replaceAll("\0", "");
        tempUniversidad.setNombreCarrera(nombreCarrera);
        
        cadena2 = new byte[LONGITUD_CIUDAD_CARRERA];
        randomFile.readFully(cadena2);
        String ciudadCarrera = new String(cadena2);
        ciudadCarrera = nombreCarrera.replaceAll("\0", "");
        tempUniversidad.setCiudadCarrera(ciudadCarrera);
        
        tempUniversidad.setNotaCorte(randomFile.readDouble());
        
        if (tempUniversidad.getIdUni() != 0) {
          listaUniversidades.add(tempUniversidad);
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    for (Universidad tempUniversidad : listaUniversidades) {
      long tempId = tempUniversidad.getIdUni();
      String tempNomCarrera = tempUniversidad.getNombreCarrera();
      String tempCiuCarrera = tempUniversidad.getCiudadCarrera();
      double tempNotaCorte = tempUniversidad.getNotaCorte();
      
      Element elemTempUni = addNodo("Carrera");
      addNodoTexto("Identificador", Long.toString(tempId), elemTempUni);
      addNodoTexto("Nombre", tempNomCarrera, elemTempUni);
      addNodoTexto("Ciudad", tempCiuCarrera, elemTempUni);
      addNodoTexto("NotaCorte", String.valueOf(tempNotaCorte), elemTempUni);
    }
    
    generateFileFromDOM();
    return operacionRealizada;
  }
  
  // metodo | addNodo --> 
  private Element addNodo (String inputNombreNodo) {
    Element mainNodo = this.documento.createElement(inputNombreNodo);
    this.documento.getDocumentElement().appendChild(mainNodo);
    return mainNodo;
  }
  
  // metodo | addNodoTexto --> 
  private void addNodoTexto (String inputDatoUni, String inputTexto, Element nodoRoot) {
    Element nodoDato = this.documento.createElement(inputDatoUni);
    Text textoDato = this.documento.createTextNode(inputTexto);
    
    nodoDato.appendChild(textoDato);
    nodoRoot.appendChild(nodoDato);
  }
  
  // metodo | limpiarTexto --> 
  private String limpiarTexto (String inputString) {
    if (inputString == null) {
      return "";
    }
    
    return inputString.replaceAll("\0", "");
  }
  
  // metodo | generateFileFromDOM --> 
  private void generateFileFromDOM () {
    try {
      Source domSource = new DOMSource(this.documento);
      Result domResult = new StreamResult(new File("./ORIGEN/CarrerasUniversitarias.xml"));
      
      Transformer domTransformer = TransformerFactory.newInstance().newTransformer();
      domTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
      domTransformer.transform(domSource, domResult);
    } catch (TransformerConfigurationException ex) {
      Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
      Logger.getLogger(FicheroUniversidades.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 4 | generacion de una plantill xsl ->
  public boolean generarPlantillaXSL () {
    boolean operacionRealizada = false;
    
    return operacionRealizada;
  }
  
 //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // getters & setters ->
  public void setPath (String inputPath) {
    this.path = new File(inputPath);
  }
}
