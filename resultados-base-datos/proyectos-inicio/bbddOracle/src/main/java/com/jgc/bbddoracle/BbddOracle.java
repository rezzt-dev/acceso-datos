/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.jgc.bbddoracle;

import com.jgc.bbddoracle.modelo.ConexionOracle;

/**
 *
 * @author rezzt
 */
public class BbddOracle {
  public static void main(String[] args) {
    ConexionOracle conOracle = new ConexionOracle();
    
    conOracle.abrirConexion();
    conOracle.cierraConexion();
  }
}
