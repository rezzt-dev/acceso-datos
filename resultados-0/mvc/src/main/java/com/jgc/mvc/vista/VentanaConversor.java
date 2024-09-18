/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.mvc.vista;

import com.jgc.mvc.controlador.ControlConversor;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 sept 2024
 */
public class VentanaConversor extends JFrame implements InterfazVista {
  private final JButton convertirApesetas;
  private final JButton convertirAeuros;
  private final JTextField cantidad;
  private final JTextField comision;

  private final JLabel resultado;
  
  public VentanaConversor () {
    super("Conversor de Euros y Pesetas");
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panelPrincipal = new JPanel();
    panelPrincipal.setLayout(new BorderLayout(10, 10));
    
    cantidad = new JTextField(8);
    JPanel panelaux = new JPanel();
    panelaux.add(cantidad);
    panelPrincipal.add(panelaux, BorderLayout.NORTH);
    
    comision = new JTextField(3);
    panelaux.add(comision);
    panelPrincipal.add(panelaux, BorderLayout.EAST);
    
    resultado = new JLabel("Indique una cantidad y pulse uno de los botones");
    JPanel panelaux2 = new JPanel();
    panelaux2.add(resultado);
    panelPrincipal.add(panelaux2, BorderLayout.CENTER);
    
    // Le indocamos el ActionCommand para el botón y así luego saber desde que botón se ha llamado
    convertirApesetas = new JButton("A pesetas");
    convertirApesetas.setActionCommand(APESETAS);
    
    // Le indocamos el ActionCommand para el botón y así luego saber desde que botón se ha llamado
    convertirAeuros = new JButton("A euros");
    convertirAeuros.setActionCommand(AEUROS);
        
     
    JPanel botonera = new JPanel();
    botonera.add(convertirApesetas);
    botonera.add(convertirAeuros);
    panelPrincipal.add(botonera, BorderLayout.SOUTH);
    getContentPane().add(panelPrincipal); 
  }

  //----------------------------------------------------------------------|
  /**
   * Establece el controlador para los botones de conversión.
   * Este método configura los ActionListeners para los botones de conversión
   * a pesetas y a euros utilizando el controlador proporcionado.
   *
   * @param c El controlador ControlConversor que manejará las acciones de los botones.
  */
  @Override
  public void setControlador(ControlConversor c) {
    convertirApesetas.addActionListener(c);
    convertirAeuros.addActionListener(c);
  }
  
  //----------------------------------------------------------------------|
  /**
   * Inicia la interfaz gráfica de usuario.
   * Este método realiza las siguientes acciones:
   * 1. Ajusta el tamaño de la ventana para acomodar todos los componentes.
   * 2. Centra la ventana en la pantalla.
   * 3. Hace visible la ventana.
  */
  @Override
  public void arranca() {
    pack(); // coloca los componentes graficos
    
    setLocationRelativeTo(null); // centra la ventana en la pantall
    setVisible(true); // hace visible la ventana
  }
  
  //----------------------------------------------------------------------|
  /**
   * Obtiene la cantidad ingresada por el usuario.
   * Este método intenta parsear el texto ingresado en el campo de cantidad
   * como un número de tipo double.
   *
   * @return El valor double de la cantidad ingresada, o 0.0 si no se puede parsear.
  */
  @Override
  public double getCantidad() {
    try {
      return Double.parseDouble(cantidad.getText());
    } catch (NumberFormatException e) {
      return 0.0D;
    }
  }
  
  //----------------------------------------------------------------------|
  /**
   * Muestra el resultado de la conversión en la interfaz.
   * Este método actualiza el campo de resultado con el string proporcionado.
   *
   * @param s El string que contiene el resultado de la conversión a mostrar.
  */
  @Override
  public void escribeCambio(String s) {
    resultado.setText(s);
  }

  @Override
  public int getComision() {
    try {
      return Integer.parseInt(comision.getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }
  
}
