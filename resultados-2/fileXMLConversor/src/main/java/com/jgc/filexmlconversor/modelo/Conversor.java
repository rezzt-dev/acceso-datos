/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.filexmlconversor.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 18 oct 2024
 */
public class Conversor {
  private Source dataOrigin = null;
  private Source stylePage = null;
  private FileOutputStream htmlPage = null;
  
  public Conversor (String inputDataOrigin, String inputStylePage, String inputHTMLOutput) {
    try {
      this.dataOrigin = new StreamSource(inputDataOrigin);
      this.stylePage = new StreamSource(inputStylePage);
      this.htmlPage = new FileOutputStream(new File(inputHTMLOutput));
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void ConvertToHTML () {
    try {
      Result returnResult = new StreamResult(this.htmlPage);
      
      Transformer transformer = TransformerFactory.newInstance().newTransformer(this.stylePage);
      transformer.transform(this.dataOrigin, returnResult);
    } catch (TransformerConfigurationException ex) {
      Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
    } catch (TransformerException ex) {
      Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        this.htmlPage.close();
      } catch (IOException ex) {
        Logger.getLogger(Conversor.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }
}