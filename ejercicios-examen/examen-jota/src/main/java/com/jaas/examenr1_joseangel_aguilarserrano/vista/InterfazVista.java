/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.jaas.examenr1_joseangel_aguilarserrano.vista;

import com.jaas.examenr1_joseangel_aguilarserrano.controlador.ControladorExamen;
import com.jaas.examenr1_joseangel_aguilarserrano.modelo.Universidad;

/**
 *
 * @author JAAS by Jose Angel Aguilar Serrano
 * @version 1.0
 * Created on 23 oct 2024
 */
public interface InterfazVista {

    void arranca();
    void setControladorE(ControladorExamen controlador);
    public String leeString();
    public Universidad getUniversidad();
    
    static final String CREARCRPETAS = "Crea 2 carpetas en la carpeta del proyecto";
    static final String ALTADATOSCARRERASUNIVERSITARIAS = "Dar de alta una universidad";
    static final String GENERARARCHIVOXML = "Genera un archivo XML de carreras universitarias";
    static final String GENERARPLANTILLAXSL = "Genera un plantilla XSL";
    static final String MODIFICARCARRERA = "Modificar datos de una carrera universitaria";
    static final String GENERARPAGINAWEB = "Genera una pagina web a partir del XML";

    public int leeId();

}
