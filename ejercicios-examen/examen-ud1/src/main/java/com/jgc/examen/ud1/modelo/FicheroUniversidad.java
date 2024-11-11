/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.examen.ud1.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * 
 * @author JGC by Juan Garcia Cazallas
 * version 1.0
 * created on 11 nov 2024
 */
public class FicheroUniversidad {
 // constantes & variables | necesarias ->
  Document documento;
  DocumentBuilder docBuilder;
  DocumentBuilderFactory docFactory;
  
   // constantes longitudes ->
  private final int LONGITUD_DOUBLE = 8;
  private final int LONGITUD_INT = 4;
  private final int LONGITUD_CHAR = 2;

  private final int CARACTERES_CIUDAD_CARRERA = 20;

  private final int LONGITUD_IDENTIFICADOR = LONGITUD_INT;
  private final int LONGITUD_CARRERA = CARACTERES_CIUDAD_CARRERA*LONGITUD_CHAR;
  private final int LONGITUD_CIUDAD = CARACTERES_CIUDAD_CARRERA*LONGITUD_CHAR;
  private final int LONGITUD_NOTA = LONGITUD_DOUBLE;

  private final int LONGITUD_TOTAL=(LONGITUD_IDENTIFICADOR + LONGITUD_CARRERA + LONGITUD_CIUDAD + LONGITUD_NOTA);
  
  public FicheroUniversidad () {}
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 1 | crear arbol de directorios ->
  public void crearArbolDirectorios () {
    File tempFolder1 = new File("./ORIGEN");
    File tempFolder2 = new File("./DESTINO");
    
    if ((tempFolder1.exists() && tempFolder2.exists()) && (tempFolder1.isDirectory() && tempFolder2.isDirectory())) {
      File[] listaFolder1 = tempFolder1.listFiles();
      File[] listaFolder2 = tempFolder2.listFiles();
      
      for (File file : listaFolder1) { file.delete(); }
      for (File file : listaFolder2) { file.delete(); }
      
      tempFolder1.delete();
      tempFolder2.delete();
    }
    
    tempFolder1.mkdir();
    tempFolder2.mkdir();
  }
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 2 | dar de alta universidades ->
  public boolean AltaDatosCarrerasUniversitarias () {
    ArrayList<Universidad> listaUnis = new ArrayList<>();
    listaUnis.add(new Universidad(3, "Informatica", "Ciudad Real", 7.51));
    listaUnis.add(new Universidad(5, "Lenguas Modernas", "Albacete", 9.61));
    listaUnis.add(new Universidad(7, "Biologia", "Cordoba", 6.9));
    listaUnis.add(new Universidad(8, "Cartografia", "Nosedonde", 10.61));
    
    boolean operacionRealizada = false;
    
    try (RandomAccessFile randomFile = new RandomAccessFile("./ORIGEN/datosUniversidades.dat", "rw")) {
      for (Universidad tempUni : listaUnis) {
        long posUni = (tempUni.getId() - 1) * this.LONGITUD_TOTAL;
        randomFile.seek(posUni);
        
        randomFile.writeInt(tempUni.getId());
        StringBuffer strBuffer = new StringBuffer(tempUni.getCarrera());
        strBuffer.setLength(LONGITUD_CARRERA);
        randomFile.writeChars(strBuffer.toString());
        
        strBuffer = new StringBuffer(tempUni.getCiudad());
        strBuffer.setLength(LONGITUD_CIUDAD);
        randomFile.writeChars(strBuffer.toString());
        
        randomFile.writeDouble(tempUni.getNotaCorte());
      }
      
    } catch (FileNotFoundException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return operacionRealizada;
  }
 
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 3 | generar fichero xml ->
  public void generarXMLFromCarreras () throws FileNotFoundException {
    List<Universidad> listaUnis = new ArrayList<>();
    try (RandomAccessFile randomFile = new RandomAccessFile("./ORIGEN/datosUniversidades.dat", "r")) {
        while (randomFile.getFilePointer() < randomFile.length()) {
            Universidad tempUni = new Universidad();
            tempUni.setId(randomFile.readInt());
            
            StringBuilder carrera = new StringBuilder();
            for (int i = 0; i < CARACTERES_CIUDAD_CARRERA; i++) {
                carrera.append(randomFile.readChar());
            }
            tempUni.setCarrera(carrera.toString().trim());
            
            StringBuilder ciudad = new StringBuilder();
            for (int i = 0; i < CARACTERES_CIUDAD_CARRERA; i++) {
                ciudad.append(randomFile.readChar());
            }
            tempUni.setCiudad(ciudad.toString().trim());
            
            tempUni.setNotaCorte(randomFile.readDouble());
            
            if (tempUni.getId() != 0) {
                listaUnis.add(tempUni);
            }
        }
    } catch (IOException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }

    try {
        docFactory = DocumentBuilderFactory.newInstance();
        docBuilder = docFactory.newDocumentBuilder();
        DOMImplementation domImplement = docBuilder.getDOMImplementation();
        this.documento = domImplement.createDocument(null, "Universidades", null);
        this.documento.setXmlVersion("1.0");
    } catch (ParserConfigurationException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }

    for (Universidad tempUni : listaUnis) {
        Element tempElem = addNodo("Universidad");
        addNodoTexto("Identificador", Integer.toString(tempUni.getId()), tempElem);
        addNodoTexto("Carrera", tempUni.getCarrera(), tempElem);
        addNodoTexto("Ciudad", tempUni.getCiudad(), tempElem);
        addNodoTexto("NotaCorte", Double.toString(tempUni.getNotaCorte()), tempElem);
    }

    generarArchivoDOM("./ORIGEN/carreras.xml");
  }
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 4 | generar una plantilla xsl ->
  public void generarPlantillaXSL () {
    String cadena = "<?xml version=\"1.0\" encoding='ISO-8859-1'?>\n" +
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n" +
            " <xsl:template match='/'>\n" +
            "   <html><xsl:apply-templates /></html>\n" +
            " </xsl:template>\n" +
            " <xsl:template match='Universidades'>\n" +
            "    <head><title>LISTADO DE UNIVERSIDADES</title></head>\n" +
            "    <body> \n" +
            "    <h1>LISTA DE UNIVERSIDADES</h1>\n" +
            "    <table border='1'>\n" +
            "    <tr><th>ID</th><th>Ciudad</th><th>Carrera</th><th>Nota de corte</th></tr>\n" +
            "      <xsl:apply-templates select='Universidad' />\n" +
            "    </table>\n" +
            " </xsl:template>\n" +
            " <xsl:template match='Universidad'>\n" +
            " </xsl:template>\n" +
            " <xsl:template match='Identificador|Ciudad|Carrera|Nota'>\n" +
            "   <td><xsl:apply-templates /></td>\n" +
            " </xsl:template>\n" +
            "</xsl:stylesheet>\n";
    
    String[] listaLineas = cadena.split("\n");
    File copyFile = new File("./DESTINO/plantilla.xsl");
    FileWriter fWriter = null;
    
    try {
      fWriter = new FileWriter(copyFile);
      
      for (int i=0; i<listaLineas.length; i++) {
        fWriter.write(listaLineas[i]);
        fWriter.write("\n");
      } 
    } catch (IOException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        fWriter.close();
      } catch (IOException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 5 | modificar una carrera universitaria (xml) ->
  public boolean modificarCarreraUniversitariaXML (int inputId, String inputCiudad) {
    boolean operacionRealizada = true;
    
    NodeList nodeList = this.documento.getElementsByTagName("Universidad");
    if (nodeList.getLength() == 0) {
      operacionRealizada = false;
    }
    
    for (int i=0; i<nodeList.getLength(); i++) {
      Element tempUni = (Element) nodeList.item(i);
      String idUniString = getTagValue("Identificador", tempUni);
      int idUni = Integer.parseInt(idUniString);
      
      if (inputId == idUni) {
        NodeList nodeListCiudad = tempUni.getElementsByTagName("Ciudad");
        if (nodeListCiudad.getLength() > 0) {
          nodeListCiudad.item(0).setTextContent(inputCiudad);
        } else {
          addNodoTexto("Ciudad", inputCiudad, tempUni);
        }
      }
    }
    
    if (operacionRealizada) {
      generarArchivoDOM("./ORIGEN/carreras.xml");
    }
    
    return operacionRealizada;
  }
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 6 | generar una pagina web (html) ->
  public void generarPaginaWeb () {
    Source origenData = null;
    Source hojaEstilos = null;
    FileOutputStream paginaHTML = null;
    
    try {
      origenData = new StreamSource("./ORIGEN/carreras.xml");
      hojaEstilos = new StreamSource("./DESTINO/plantilla.xsl");
      paginaHTML = new FileOutputStream(new File("./DESTINO/carrerasHTML.html"));
      
      Result result = new StreamResult(paginaHTML);
      Transformer transformer = TransformerFactory.newInstance().newTransformer(hojaEstilos);
      transformer.transform(origenData, result);
    }catch (TransformerConfigurationException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        paginaHTML.close();
      } catch (IOException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
  
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos privados | helper metods ->
   // metodo | addNodo | agrega un nodo -->
  private Element addNodo (String inputNombreNodo) {
    Element mainNodo = this.documento.createElement(inputNombreNodo);
    this.documento.getDocumentElement().appendChild(mainNodo);
    return mainNodo;
  }
  
   // metodo | addNodoTexto | agrega un nodo y texto -->
  private void addNodoTexto (String inputDatoHijo, String inputTexto, Element inputRootNode) {
    Element elemData = this.documento.createElement(inputDatoHijo);
    String cleanedText = cleanInvalidXmlChars(inputTexto);
    Text textData = this.documento.createTextNode(cleanedText);
    elemData.appendChild(textData);
    inputRootNode.appendChild(elemData);
  }
  
   // metodo | cleanInvalidXmlChars | limpia las cadenas de texto -->
  private String cleanInvalidXmlChars(String text) {
    return text.replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFFF]", "");
  }
  
   // metodo | preProcess | preprocesa un xml para el formateo -->
  private Transformer preProcess (String inputIndent) {
    Transformer transformer =null;
    
    try {
      transformer = TransformerFactory.newInstance().newTransformer();
    } catch (TransformerConfigurationException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    transformer.setOutputProperty(OutputKeys.INDENT, inputIndent);
    return transformer;
  }
  
   // metodo | generarArchivoDOM | genera el fichero cargado en memoria -->
  private void generarArchivoDOM(String inputFilename) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
      DOMSource source = new DOMSource(this.documento);
      StreamResult result = new StreamResult(new File(inputFilename));
      transformer.transform(source, result);
    } catch (TransformerException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  
  private String getTagValue (String inputTag, Element inputElem) {
    NodeList nodeList = inputElem.getElementsByTagName(inputTag).item(0).getChildNodes();
    Node tempNode = nodeList.item(0);
    
    if (tempNode != null) {
      return tempNode.getNodeValue();
    } else {
      return null;
    }
  }
}
