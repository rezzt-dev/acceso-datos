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
    //Creo los objetos que me piden
      ArrayList<Universidad> listaUni = new ArrayList<>();
      listaUni.add(new Universidad(3, "Informatica", "Ciudad Real", 7.51));
      listaUni.add(new Universidad(5, "Lenguas Modernas", "Albacete", 9.61));
      listaUni.add(new Universidad(7, "Biologia", "Cordoba", 6.9));
      listaUni.add(new Universidad(8, "Cartografia", "Nosedonde", 10.61));

      boolean resultado = true;

      //Procedo a añadirlos al fichero datosUniversidades.dat
      try (RandomAccessFile randomFile = new RandomAccessFile("./ORIGEN/datosUniversidades.dat", "rw")) {
          for (Universidad u : listaUni) {
              long posUni = (u.getId() - 1) * this.LONGITUD_TOTAL;
              //Posiciono el puntero en la posicion correspondiente al ID del objeto
              randomFile.seek(posUni);

              //Escribo el ID
              randomFile.writeInt(u.getId());

              //Escribo la Carreara
              StringBuffer bufferStr = new StringBuffer(u.getCarrera());
              bufferStr.setLength(CARACTERES_CIUDAD_CARRERA);
              //Seteo la longitud AL NUMERO DE CARACTERES que debe tener la cadena
              randomFile.writeChars(bufferStr.toString());

              //Escribo la ciudad
              bufferStr = new StringBuffer(u.getCiudad());
              //Seteo la longitud AL NUMERO DE CARACTERES que debe tener la cadena
              bufferStr.setLength(CARACTERES_CIUDAD_CARRERA);
              randomFile.writeChars(bufferStr.toString());

              //Escribo la nota de corte
              randomFile.writeDouble(u.getNotaCorte());

          }
      } catch (IOException ex) {
          Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
          resultado = false;
      }
      return resultado;
  }
 
 //——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
 // metodos menu | opcion 3 | generar fichero xml ->
  public void generarXMLFromCarreras(){
    List<Universidad> universidadList = new ArrayList<>();

// Cargar universidades desde el archivo binario
    try (RandomAccessFile randomFile = new RandomAccessFile("./ORIGEN/datosUniversidades.dat", "r")) {
        //Mientras el cursor no este al final del fichero:
      while (randomFile.getFilePointer() < randomFile.length()) {
        //Creo un objeto donde almacenar lo que leo
        Universidad uni = new Universidad();

        //Leo el id y se lo añado al objeto
        uni.setId(randomFile.readInt());

        //leo la carrera
        byte[] carrera = new byte[LONGITUD_CARRERA];// Leo la longitud esperada para la cadena EN BYTES
        randomFile.readFully(carrera);
        String carreraS = new String(carrera);
        //Importante esta parte, porque si no, la cadena trae caracteres nulos y XML no acepta eso
        carreraS=carreraS.replace("\0", "");
        //IMPORTANTE LO DE ARRIBA
        uni.setCarrera(carreraS);

        //leo la ciudad
        byte[] ciudad = new byte[LONGITUD_CIUDAD];
        randomFile.readFully(ciudad);
        String ciudadS = new String(ciudad);
        ciudadS=ciudadS.replace("\0", "");
        uni.setCiudad(ciudadS);

        //Leo la nota de corte
        uni.setNotaCorte(randomFile.readDouble());          

        //Finalmente, compruebo el id del objeto y lo añado al array
        //unicamente si su id es distinto de 0
        // si es 0 significa que es un registro vacio
        if(uni.getId()!=0){
          universidadList.add(uni);
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }

    // Crear y guardar el archivo XML a partir de la lista cargada
    try {
      //Las siguienteslineas son todas casi iguales en todos los casos
      docFactory = DocumentBuilderFactory.newInstance();
      docBuilder = docFactory.newDocumentBuilder();
      DOMImplementation implementation = docBuilder.getDOMImplementation();

      //La siguiente linea crea el nodo principal del XML
      this.documento =(Document) implementation.createDocument(null, "Universidades", null);
      this.documento.setXmlVersion("1.0");

    } catch (ParserConfigurationException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }
    //Por cada universidad que haya leido previamente:
    for (Universidad uni : universidadList) {
      Element elem = addNodo("Universidad");
      //A ese nodo le añado otros con sus atributos
      addNodoTexto("Identificador", String.valueOf(uni.getId()), elem);
      addNodoTexto("Carrera", uni.getCarrera() , elem);
      addNodoTexto("Ciudad", uni.getCiudad(), elem);
      addNodoTexto("Nota", Double.toString(uni.getNotaCorte()) , elem);
    }
    //El siguiente metodo genera el archivo XML con la informacion que esta cargada en memoria
    generarArchivoDOM("./ORIGEN/carreras.xml");
    mostrarEnPantalla();
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
            "    <tr><th>Identificador</th><th>Carrera</th><th>Ciudad</th><th>Nota</th></tr>\n" +
            "      <xsl:apply-templates select='Universidad' />\n" +
            "    </table>\n" +
            "    </body>\n" +
            " </xsl:template>\n" +
            " <xsl:template match='Universidad'>\n" +
            "   <tr>\n" +
            "    <xsl:apply-templates select='Identificador|Ciudad|Carrera|Nota'/>\n" +
            "   </tr>\n" +
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
    FileOutputStream pagHTML = null;

    try {
      //Archivo XML
      origenData = new StreamSource("./ORIGEN/carreras.xml");
      //Archivo XSL
      hojaEstilos = new StreamSource("./DESTINO/plantilla.xsl");
      //El HTML que vamos a crear
      pagHTML = new FileOutputStream (new File("./DESTINO/carrerasHTML.html"));

      //Redirijo la salida al HTML
      Result result = new StreamResult(pagHTML);
      //Construyo un transformador con el archivo XSL como parametro
      Transformer transformer = TransformerFactory.newInstance().newTransformer(hojaEstilos);
      //Creo el HTML
      transformer.transform(origenData, result);
    } catch (TransformerConfigurationException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } catch (FileNotFoundException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        pagHTML.close();
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
    Source source = new DOMSource(this.documento);
    Result salida = new StreamResult(new File(inputFilename));
    
    try {
      preProcess("no").transform(source, salida); //"no" porque queremos que todo este en la misma linea
    } catch (TransformerException ex) {
      Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   // metodo | escribirEnPantalla | Obtiene el valor de un elemento -->
  private void mostrarEnPantalla () {
    try {
      Source source = new DOMSource(this.documento);
      Result salida = new StreamResult(System.out);

      preProcess("yes").transform(source, salida);//"yes" para que indente el XML

    } catch (TransformerConfigurationException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
        Logger.getLogger(FicheroUniversidad.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

   // metodo | getTagValue | Obtiene el valor de un elemento -->
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
