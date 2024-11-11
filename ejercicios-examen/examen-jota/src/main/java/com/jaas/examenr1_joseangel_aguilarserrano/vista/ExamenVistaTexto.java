/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jaas.examenr1_joseangel_aguilarserrano.vista;

import com.jaas.examenr1_joseangel_aguilarserrano.controlador.ControladorExamen;
import com.jaas.examenr1_joseangel_aguilarserrano.modelo.Universidad;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JAAS by Jose Angel Aguilar Serrano
 * @version 1.0
 * Created on 23 oct 2024
 */
public class ExamenVistaTexto implements InterfazVista{
    
    private ControladorExamen controlador;
    
    private final BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
    
    private void solicitaOperacion() {
        System.out.println("=============================================================");
        System.out.println("0. Salir");
        System.out.println("1. Crear estructura de carpetas");
        System.out.println("2. Alta datos Carreras Universitarias");
        System.out.println("3. Generar archivo XML con Carreras Universitarias");
        System.out.println("4. Generacion de plantilla XSL");
        System.out.println("5. Modificar Carrera Universitaria");
        System.out.println("6. Generacion de pagina web con Carreras Universitarias");
        System.out.println("=============================================================");
        System.out.print("Introduzca la opcion: ");
    }

    @Override
    public void arranca() {
        procesaNuevaOperacion();
    }

    private void procesaNuevaOperacion() {
        int operacion;
        solicitaOperacion();
        operacion = leeOpcion();
        switch (operacion) {
            case 0 -> {
                System.out.println("Ejecucion finalizada");
                System.exit(0);
            }
            case 1 -> controlador.actionPerformed(new ActionEvent(this, operacion, CREARCRPETAS));
            case 2 -> controlador.actionPerformed(new ActionEvent(this, operacion, ALTADATOSCARRERASUNIVERSITARIAS));
            case 3 -> controlador.actionPerformed(new ActionEvent(this, operacion, GENERARARCHIVOXML));
            case 4 -> controlador.actionPerformed(new ActionEvent(this, operacion, GENERARPLANTILLAXSL));
            case 5 -> controlador.actionPerformed(new ActionEvent (this, operacion, MODIFICARCARRERA));
            case 6 -> controlador.actionPerformed(new ActionEvent (this, operacion, GENERARPAGINAWEB));
//            default -> operacionIncorrecta();
        }
        procesaNuevaOperacion();
    }
    
    private int leeOpcion() {
        String s = null;
        try {
            s = in.readLine();
            return Integer.parseInt(s);
        } catch (IOException | NumberFormatException e) {
            operacionIncorrecta();
            return 0;
        }
    }
    
    
    @Override
    public String leeString(){
        String s = null;
        try {
            System.out.print("Introduce el nombre: ");
            s=in.readLine();
            return s;
        } catch (IOException e) {
            System.out.println("Error en la cadena introducida");
            return leeString();
        }
    }
    
    private void operacionIncorrecta() {
        System.out.print("Operación incorrecta. ");
        procesaNuevaOperacion();
    }

    @Override
    public void setControladorE(ControladorExamen controlador) {
    this.controlador = controlador;
    }

    @Override
    public Universidad getUniversidad() {
        Universidad universidad = null;
        System.out.print("Introduce el ID de la universidad: ");
        int id = leeOpcion();
        System.out.println("Introduce el nombre de la carrera ");
        String carrera = leeString();
        System.out.println("Introduce el nombre de la ciudad ");
        String ciudad = leeString();
        System.out.print("Introduce la nota de corte: ");
        double notaCorte=0;
        try {
            notaCorte = Double.parseDouble(in.readLine());
        } catch (IOException ex) {
            Logger.getLogger(ExamenVistaTexto.class.getName()).log(Level.SEVERE, null, ex);
        }
        universidad = new Universidad(id, carrera, ciudad, notaCorte);
        return universidad;
    }

    @Override
    public int leeId() {
        System.out.print("Introduce el id: ");
        String s = null;
        try {
            s = in.readLine();
            return Integer.parseInt(s);
        } catch (IOException | NumberFormatException e) {
            operacionIncorrecta();
            return 0;
        }
    }

}
