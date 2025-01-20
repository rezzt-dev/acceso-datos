/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.break4learning.objectdbproyecto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jgarc
 */
public class Metodos { 
    

    public static Date convertirDate(String fecha){
        Date date = null;
        try{
            date = new SimpleDateFormat("dd/MM/yy").parse(fecha);
            } catch (ParseException pe){
                System.out.println(pe);
            }
        return date;
    }
}
