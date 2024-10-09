/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.fileaccesoaleatorio;

import com.jgc.fileaccesoaleatorio.modelo.Empleado;
import com.jgc.fileaccesoaleatorio.modelo.Escritura;
import com.jgc.fileaccesoaleatorio.modelo.FicheroEmpleados;
import com.jgc.fileaccesoaleatorio.modelo.Lectura;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 */
public class FileAccesoAleatorio {

  public static void main(String[] args) throws IOException {
    try (Scanner scanner = new Scanner(System.in)) {
        FicheroEmpleados fichero = new FicheroEmpleados("empleados.dat");
        Empleado empleado1 = new Empleado(1, "garcia", 12, 300);
        Empleado empleado2 = new Empleado(32, "fernandez", 12, 300); 
        Empleado empleado3 = new Empleado(3, "cadiz", 12, 300);


        Escritura modeloEs = new Escritura("empleados.dat");
        modeloEs.escribirEmpleadoFinalArchivo(empleado1);
        modeloEs.almacenarRegistro(empleado2);
        modeloEs.almacenarRegistro(empleado3);


        Lectura modeloLe = new Lectura("empleados.dat");
        Empleado newEmpleado = modeloLe.lecturaEmpleado(1);
        System.out.println(newEmpleado.toString());
        modeloLe.mostrarRegistros();
        
    } catch (IOException e) {
        System.err.println("Error de E/S: " + e.getMessage());
        e.printStackTrace();
    } catch (Exception e) {
        System.err.println("Error inesperado: " + e.getMessage());
        e.printStackTrace();
    }
  }
}
