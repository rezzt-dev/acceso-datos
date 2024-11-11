/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jaas.examenr1_joseangel_aguilarserrano.controlador;

import com.jaas.examenr1_joseangel_aguilarserrano.modelo.Examen;
import com.jaas.examenr1_joseangel_aguilarserrano.modelo.Universidad;
import com.jaas.examenr1_joseangel_aguilarserrano.vista.InterfazVista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author JAAS by Jose Angel Aguilar Serrano
 * @version 1.0
 * Created on 23 oct 2024
 */
public class ControladorExamen implements ActionListener{

    private final InterfazVista vista;
    private final Examen modelo;
    
    public ControladorExamen(InterfazVista vista,Examen modelo) {
        this.modelo=modelo;
        this.vista=vista;
        
        this.vista.setControladorE(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case InterfazVista.CREARCRPETAS -> {
                modelo.CrearEstructuraDeCarpetas();
            }
            case InterfazVista.ALTADATOSCARRERASUNIVERSITARIAS -> {
                modelo.AltaDatosCarrerasUniversitarias();
            }
            case InterfazVista.GENERARARCHIVOXML -> {
                modelo.GeneraXMLCarrerasUniversitarias();
            }
            case InterfazVista.MODIFICARCARRERA -> {
               
                modelo.ModificaCarreraUniversitaria(vista.leeId(), vista.leeString());
            }
            case InterfazVista.GENERARPLANTILLAXSL -> {
               
                modelo.generarXSL();
            }
            case InterfazVista.GENERARPAGINAWEB -> {
               
                modelo.convertirAHTML();
            }
        }
    }
    

}
