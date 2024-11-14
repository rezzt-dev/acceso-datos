/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.ejercicio2examen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 nov 2024
 */
public class Modelo {
  private final int LONGITUD_DOUBLE = 8;
  private final int LONGITUD_INT = 4;
  private final int LONGITUD_CHAR = 2;
  
  private final int CARACTERES_DESCRIPCION = 20;
  private final int CARACTERES_DIRECCION = 20;
  
  private final int LONGITUD_ID = LONGITUD_INT;
  private final int LONGITUD_DESCRIPCION = CARACTERES_DESCRIPCION * LONGITUD_CHAR;
  private final int LONGITUD_DIRECCION = CARACTERES_DIRECCION * LONGITUD_CHAR;
  private final int LONGITUD_COSTE = LONGITUD_DOUBLE;
  
  private final int LONGITUD_TOTAL = LONGITUD_ID + LONGITUD_DESCRIPCION + LONGITUD_DIRECCION + LONGITUD_COSTE;
  
  public void insertaEjercicio2 (Reforma reforma) {
    RandomAccessFile randomFile = null;
    StringBuffer buffer = null;
    StringBuffer buffer2 = null;

    
    try {
      randomFile = new RandomAccessFile("./ORIGEN/datosReformas.dat", "rw");
      long pos = (reforma.getId() - 1) * LONGITUD_TOTAL;
      randomFile.seek(pos);
      
      if (!utilidadExisteReforma(reforma)) {
        randomFile.writeInt(reforma.getId());

        buffer = new StringBuffer(reforma.getDescripcion());
        buffer.setLength(CARACTERES_DESCRIPCION);
        randomFile.writeChars(buffer.toString());

        buffer2 = new StringBuffer(reforma.getDireccion());
        buffer2.setLength(CARACTERES_DIRECCION);
        randomFile.writeChars(buffer2.toString());

        randomFile.writeDouble(reforma.getCoste());
      } else {
        randomFile.skipBytes(LONGITUD_TOTAL - LONGITUD_COSTE);
        randomFile.writeDouble(reforma.getCoste());
      }
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void muestraEjercicio2 (int id) {
    Reforma foundReforma = new Reforma();
    RandomAccessFile randomFile = null;
    
    try {
      randomFile = new RandomAccessFile("./ORIGEN/datosReformas.dat", "r");
      long posRef = (id - 1) * LONGITUD_TOTAL;
      
      randomFile.seek(posRef);
      foundReforma.setId(randomFile.readInt());

      if (id == foundReforma.getId()) {
        byte[] descrip = new byte[LONGITUD_DESCRIPCION];
        randomFile.readFully(descrip);
        String descripS = new String(descrip);
        descripS = descripS.replace("\0", "");
        foundReforma.setDescripcion(descripS);

        byte[] direcc = new byte[LONGITUD_DIRECCION];
        randomFile.readFully(direcc);
        String direccS = new String(direcc);
        direccS = direccS.replace("\0", "");
        foundReforma.setDescripcion(direccS);

        foundReforma.setCoste(randomFile.readDouble());
      } else {
      }
      
      System.out.println(" > Reforma " + foundReforma.getId() + " | Descripcion: " + foundReforma.getDescripcion() + " | Coste: " + String.valueOf(foundReforma.getCoste()));
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  
  private boolean utilidadExisteReforma (Reforma reforma) {
    RandomAccessFile randomFile = null;
    boolean existeReforma = false;
    
    try {
      randomFile = new RandomAccessFile("./ORIGEN/datosReformas.dat", "r");
      long posRef = (reforma.getId() - 1) * LONGITUD_TOTAL;

      while (randomFile.getFilePointer() < randomFile.length()) {
        Reforma tempRef = new Reforma();
        randomFile.seek(posRef);
        
        tempRef.setId(randomFile.readInt());
        
        if ((tempRef.getId() != 0) && (tempRef.getId() == reforma.getId())) {
          existeReforma = true;
          break;
        } else {
          posRef = (tempRef.getId() - 1) * LONGITUD_TOTAL;
        }
      }
    } catch (IOException ex) {
      Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return existeReforma;
  }
}
