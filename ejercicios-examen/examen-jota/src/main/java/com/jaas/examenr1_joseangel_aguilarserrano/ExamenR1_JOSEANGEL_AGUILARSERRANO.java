/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jaas.examenr1_joseangel_aguilarserrano;

import com.jaas.examenr1_joseangel_aguilarserrano.controlador.ControladorExamen;
import com.jaas.examenr1_joseangel_aguilarserrano.modelo.Examen;
import com.jaas.examenr1_joseangel_aguilarserrano.vista.ExamenVistaTexto;
import com.jaas.examenr1_joseangel_aguilarserrano.vista.InterfazVista;

/**
 *
 * @author JAAS by Jose Angel Aguilar Serrano
 */
public class ExamenR1_JOSEANGEL_AGUILARSERRANO {

    public static void main(String[] args) {
        InterfazVista vista = new ExamenVistaTexto();
        Examen modelo = new Examen();
        
        ControladorExamen controlador = new ControladorExamen(vista, modelo);
        vista.arranca();
        
    }
}
