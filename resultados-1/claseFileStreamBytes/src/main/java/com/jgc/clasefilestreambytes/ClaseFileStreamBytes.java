/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.clasefilestreambytes;

import com.jgc.clasefilestreambytes.modelo.Copia;
import com.jgc.clasefilestreambytes.modelo.Escritura;
import com.jgc.clasefilestreambytes.modelo.Lectura;
import com.jgc.clasefilestreambytes.modelo.myObject.Empleado;
import java.util.ArrayList;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class ClaseFileStreamBytes {

    public static void main(String[] args) {
      Escritura modeloEs = new Escritura("binaryFile.bin");
      modeloEs.simpleDataWrite();
      
      Lectura modeloLec = new Lectura("binaryFile.bin");
      System.out.println(modeloLec.simpleDataRead());
      
      Copia modeloCo = new Copia("C:\\Users\\b15-11m\\images\\wallpaper\\simple-ui\\01.png", "./copia.png");
      modeloCo.copyFile();
      
      Empleado empleado1 = new Empleado ("Fernando", "Garcia", 23, 500);
      modeloEs.writeObjects(empleado1);
      ArrayList<Empleado> empleados = (ArrayList<Empleado>) (ArrayList<?>) modeloLec.readObjects();
      empleados.forEach(System.out::println);
    }
}
