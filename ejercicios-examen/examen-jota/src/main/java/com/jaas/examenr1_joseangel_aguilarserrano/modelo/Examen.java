/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/

package com.jaas.examenr1_joseangel_aguilarserrano.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
* @author JAAS by Jose Angel Aguilar Serrano
* @version 1.0
* Created on 23 oct 2024
*/
public class Examen {

  Document documento;
  DocumentBuilderFactory factory; //Para trabajar con XML hay que crear siempre uno de estos
  DocumentBuilder builder; //Tambien lo necesitamos, pero previamente hay que crear el factory

  private final int LONGITUD_DOUBLE = 8;
  private final int LONGITUD_INT = 4;
  private final int LONGITUD_CHAR=2;

  private final int CARACTERES_CIUDAD_CARRERA=20;

  private final int LONGITUD_IDENTIFICADOR = LONGITUD_INT;
  private final int LONGITUD_CARRERA = CARACTERES_CIUDAD_CARRERA*LONGITUD_CHAR;
  private final int LONGITUD_CIUDAD = CARACTERES_CIUDAD_CARRERA*LONGITUD_CHAR;
  private final int LONGITUD_NOTA = LONGITUD_DOUBLE;

  //Tamaño total del registro
  private final int LONGITUD_TOTAL=LONGITUD_IDENTIFICADOR+LONGITUD_CARRERA+LONGITUD_CIUDAD+LONGITUD_NOTA;

  public Examen(){}

  public void CrearEstructuraDeCarpetas(){
      File p1 = new File("ORIGEN");
      File p2 = new File("DESTINO");
      if ((p1.exists()&&p2.exists())&&
          (p1.isDirectory()&&p2.isDirectory())){
          File [] listaF1 = p1.listFiles();
          File [] listaF2 = p2.listFiles();
          for (File file:listaF1){
              file.delete();
          }
          for (File file:listaF2){
              file.delete();
          }
          p1.delete();
          p2.delete();
      }
      p1.mkdir();
      p2.mkdir();
  }   

  public boolean AltaDatosCarrerasUniversitarias(){

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
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
          resultado = false;
      }
      return resultado;

  }

  /**
   * Este metodo esta implementado en un unico metodo, pero se podria separar en 2
   * La primera parte carga el archivo binario en memoria
   * La segunda parte genera el XML a partir del archivo cargado en memoria
   */
  public void GeneraXMLCarrerasUniversitarias(){
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
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      }

      // Crear y guardar el archivo XML a partir de la lista cargada
      try {
          //Las siguienteslineas son todas casi iguales en todos los casos
          factory = DocumentBuilderFactory.newInstance();
          builder = factory.newDocumentBuilder();
          DOMImplementation implementation = builder.getDOMImplementation();

          //La siguiente linea crea el nodo principal del XML
          this.documento =(Document) implementation.createDocument(null, "Universidades", null);
          this.documento.setXmlVersion("1.0");

      } catch (ParserConfigurationException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      }
      //Por cada universidad que haya leido previamente:
      for (Universidad uni : universidadList) {

          //Añado el nodo Universidad
              Element elem = addNodo("Universidad");
              //A ese nodo le añado otros con sus atributos
              addNodoYTexto("Identificador", Integer.toString(uni.getId()), elem);
              addNodoYTexto("Carrera", uni.getCarrera() , elem);
              addNodoYTexto("Ciudad", uni.getCiudad(), elem);
              addNodoYTexto("Nota", Double.toString(uni.getNotaCorte()) , elem);

          }
      //El siguiente metodo genera el archivo XML con la informacion que esta cargada en memoria
          generarArchivodelDOM("./ORIGEN/carreras.xml");
      //El siguiente metodo muestra el XML que acabamos de generar en consola, pero con indentacion
          mostrarPantalla();
  }

  /**
   * Metodo para modificar el campo Carrera de una Universidad en el XML
   * @param id
   * @param ciudad
   * @return 
   */
  public boolean ModificaCarreraUniversitaria(int id, String ciudad){
      boolean resultado=true;
      //Sacamos una lista de las universidades del documento XML
      //que tiene como elemento padre <Universidades>
      NodeList nodelist = this.documento.getElementsByTagName("Universidad");
      //Por cada nodo en nodelist, genero un Element nuevo
      if (nodelist.getLength() == 0){
          //Si nodelist esta vacia significa que no hay Universidades
          resultado=false;
      }

      for(int i = 0 ; i< nodelist.getLength();i++){
          Element universidad = (Element)nodelist.item(i);
          String idEmpleString = getTagValue("Identificador", universidad);
          int idEmple = Integer.parseInt(idEmpleString);

          //Si encuentro una Universidad con el ID que estoy buscando
          if(idEmple == id){
              //Busco dentro de los nodos de la universidad aquel que se llama Ciudad
              NodeList nodeListCiudad = universidad.getElementsByTagName("Ciudad");
              //Si el nodo existe le cambio el valor
              if(nodeListCiudad.getLength()>0){
                  //Si existe el nodo salario lo modifico
                  //Miro solo el primer item, en caso de que la universidad tuviera
                  //varios nodos Ciudad solo le haria caso al primero
                  nodeListCiudad.item(0).setTextContent(ciudad);
              }else{
                  //si no existia el nodo salario, lo creo ahora
                  addNodoYTexto("Ciudad", ciudad, universidad);
              }
          }
      }
      //Lo guardo nuevamente llamando a generarArchivo
      if(resultado){
          generarArchivodelDOM("./ORIGEN/carreras.xml");
          System.out.println("He generado un nuevo xml");
      }else{
          //Esto no se deberia hacer aqui porque esto es el modelo, esto es cosa de la vista
          System.out.println("No existen empleados en el fichero");
      }
      return resultado;
  }

  public void generarXSL(){
      //Esta es la cadena que conforma el archivo XSL que voy a necesitar
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
"    </body>\n" +
" </xsl:template>\n" +
" <xsl:template match='Universidad'>\n" +
"   <tr><xsl:apply-templates /></tr>\n" +
" </xsl:template>\n" +
" <xsl:template match='Identificador|Ciudad|Carrera|Nota'>\n" +
"   <td><xsl:apply-templates /></td>\n" +
" </xsl:template>\n" +
"</xsl:stylesheet>\n";

      //Hago un split de la cadena por cada salto de linea que me encuentre
      String[] listaLineas = cadena.split("\n");

      File archivoCopia = new File("./DESTINO/plantilla.xsl");
      FileWriter fw=null;
      try {
          fw = new FileWriter(archivoCopia);

          //Por cada linea que se me ha generado al hacer split:
          for(int i = 0; i<listaLineas.length;i++){

              //Escribo la linea y hago un "enter" para irme a la siguiente linea
              fw.write(listaLineas[i]);
              fw.write("\n");
          }
      } catch (IOException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      }finally{
          try {
              fw.close();
          } catch (IOException ex) {
              Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
          }
      }

  }

  public void convertirAHTML(){
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
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      } catch (TransformerException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      } catch (FileNotFoundException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      } finally {

          try {
              pagHTML.close();
          } catch (IOException ex) {
              Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
          }

      }

  }

    //metodos privados

  /**
   * Obtiene el valor de un atributo determinado dentro de un elemento dado
   * @param tag   Elemento que estoy buscando
   * @param elemento Nodo padre donde voy a buscar
   * @return 
   */
  public String getTagValue(String tag, Element elemento){

      NodeList nodeList = elemento        //Crea una lista de nodos que sean hijos del elemento 
                                          //y que coincida con las siguientes caracteristicas:
              .getElementsByTagName(tag)  //Saca todos los elementos contenidos en el padre y que tienen tag como elemento hijo
              .item(0)                    //Coje el primer valor que coincide con tag
              .getChildNodes();           //coje los nodos hijos (ignora atributos, por ejemplo)
      Node node = nodeList.item(0);       //Creo un nodo con la informacion del nodo hijo
      if (node != null){
          return node.getNodeValue();     //Si nodo no esta vacio lo devuelvo
      }else{
          return null;    //Si no existe ningun nodo que tenga la tag que buscamos devuelve null
      }

  }

  /**
   * Añade un nodo
   * @param nombreNodo
   * @return 
   */
  private Element addNodo(String nombreNodo){
      //Asi creamos un nodo (un elemento hijo) para empleados
      Element nodoPrincipal = this.documento.createElement(nombreNodo);
      //Lo añado al documento
      documento.getDocumentElement().appendChild(nodoPrincipal);

      return nodoPrincipal;
  }

  /**
   * Añade un nodo y le asigna un valor
   * @param datoHijo es el elemento que se va a crear 
   * @param texto es el valor que tendra el elemento que creamos
   * @param raiz es el padre del elemento que creamos
   */
  private void addNodoYTexto(String datoHijo, String texto, Element raiz){
      //Creamos un elemento hijo
      Element dato = this.documento.createElement(datoHijo);
      //Le meto valor al elemento
      Text textoDato= this.documento.createTextNode(texto);
      //Primero al nodo le meto los datos
      dato.appendChild(textoDato);
      //Y luego meto el nodo en el elemento raiz
      raiz.appendChild(dato);
  }

  /**
   * preprocesa el XML
   * @param indent
   * @return 
   */
  private Transformer preProcess(String indent){
      Transformer transformer =null;
      try {
          //Creo el transformer para luego poder llamarlo en mostrar pantalla
          transformer = TransformerFactory.newInstance().newTransformer();
      } catch (TransformerConfigurationException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      }
      //La linea de abajo da formato al XML
      transformer.setOutputProperty(OutputKeys.INDENT, indent);
      return transformer;
  }
  /**
   * Genera el archivo XML que esta en memoria
   * @param nombreArchivo nombre que queremos que tenga el archivo generado
   */
 private void generarArchivodelDOM(String nombreArchivo) {

          Source source = new DOMSource(this.documento);
          Result salida = new StreamResult(new File(nombreArchivo));
      try {
          preProcess("no").transform(source, salida); //"no" porque queremos que todo este en la misma linea
      } catch (TransformerException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  /**
   * Muestra el archivo XML que esta cargado en memoria
   */
  private void mostrarPantalla(){
      try {
          //Le indico el origen que quiero mostrar en pantalla
          //En este caso, este documento
          Source source = new DOMSource(this.documento);
          //Le digo por donde quiero que salga
          //En este caso le estoy diciendo que imprima en consola
          Result salida = new StreamResult(System.out);
          //Como en preProcess me returnea un Transformer (una instancia de este)
          //Puedo encadenar los metodos que necesito
          //uso .transform y le indico la fuente de informacion y la salida que quiero
          //que esa información tome
          preProcess("yes").transform(source, salida);//"yes" para que indente el XML


      } catch (TransformerConfigurationException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      } catch (TransformerException ex) {
          Logger.getLogger(Examen.class.getName()).log(Level.SEVERE, null, ex);
      }
  } 

}