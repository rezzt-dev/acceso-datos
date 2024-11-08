/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.bbddoracle.modelo;

import com.jgc.bbddoracle.bbdd.OperacionesBBDD;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 nov 2024
 */
public class Empleado {
    private int emp_no;
    private String apellido;
    private String oficio;
    private int dir;
    private Date fecha_alt;
    private double salario;
    private double comision;
    private int dept_no;

    public Empleado(int emp_no, String apellido, String oficio, int dir, Date fecha_alt, double salario, double comision, int dept_no) {
      this.emp_no = emp_no;
      this.apellido = apellido;
      this.oficio = oficio;
      this.dir = dir;
      this.fecha_alt = fecha_alt;
      this.salario = salario;
      this.comision = comision;
      this.dept_no = dept_no;
    }
    
    public Empleado(){
        
    }

    public int getEmp_no() {
      return emp_no;
    }

    public void setEmp_no(int emp_no) {
      this.emp_no = emp_no;
    }

    public String getApellido() {
      return apellido;
    }

    public void setApellido(String apellido) {
      this.apellido = apellido;
    }

    public String getOficio() {
      return oficio;
    }

    public void setOficio(String oficio) {
      this.oficio = oficio;
    }

    public int getDir() {
      return dir;
    }

    public void setDir(int dir) {
      this.dir = dir;
    }

    public Date getFecha_alt() {
      return fecha_alt;
    }

    public void setFecha_alt(Date fecha_alt) {
      this.fecha_alt = fecha_alt;
    }

    public double getSalario() {
      return salario;
    }

    public void setSalario(double salario) {
      this.salario = salario;
    }

    public double getComision() {
      return comision;
    }

    public void setComision(double comision) {
      this.comision = comision;
    }

    public int getDept_no() {
      return dept_no;
    }

    public void setDept_no(int dept_no) {
      this.dept_no = dept_no;
    }
    
    
    //Insertar en la tabla empleados
    public void insertar(OperacionesBBDD bbdd){
      try {
        bbdd.insert("insert into empleados values (?,?,?,?,?,?,?,?)" ,this.emp_no, this.apellido, this.oficio, this.dir, this.fecha_alt, this.salario, this.comision, this.dept_no);

      } catch (SQLException ex) {
        Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    
    //Seleccionar un empleado por su identificador
    public void selectById(OperacionesBBDD bbdd, int id_empleado){
      try {
        Optional<ResultSet> rs = bbdd.select("SELECT * FROM empleados WHERE emp_no = ?",id_empleado);

        if(rs.isPresent()){
          while(rs.get().next()){ 
            this.emp_no = (rs.get().getInt("emp_no"));
            this.apellido = (rs.get().getString("apellido"));
            this.oficio = (rs.get().getString("oficio"));
            this.dir = (rs.get().getInt("dir"));
            this.fecha_alt = (rs.get().getDate("fecha_alt"));
            this.salario = (rs.get().getDouble("salario"));
            this.comision = (rs.get().getDouble("comision"));
            this.dept_no = (rs.get().getInt("dept_no"));                    
          } 
        }

      } catch (SQLException ex) {
        Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    
    public static Optional<ResultSet> selectAll(OperacionesBBDD bbdd){
      Optional<ResultSet> rs = null;
        
      try {    
        rs = bbdd.select("SELECT * FROM empleados");   
      } catch (SQLException ex) {
        Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
        
      }
      return rs;
    }
    
    
    public static void mostrarResultSet(Optional<ResultSet> rs){
      try {
        if(rs.isPresent()){
          while(rs.get().next()){
            System.out.println("Número empleado: " + rs.get().getInt("emp_no") +
                    "; apellido: " + rs.get().getString("apellido") +
                    "; oficio: " + rs.get().getString("oficio") +
                    "; dir: " + rs.get().getInt("dir") +
                    "; fecha de alta: " + rs.get().getDate("fecha_alt") +
                    "; salario: " + rs.get().getDouble("salario") + 
                    "; comision: "+ rs.get().getDouble("comision") + 
                    "; numero departamento: " + rs.get().getInt("dept_no"));
          }
        }
      } catch (SQLException ex) {
        Logger.getLogger(Departamento.class.getName()).log(Level.SEVERE, null, ex);
      } 
    }
    
    
    public void update(OperacionesBBDD bbdd){
      try {  
        bbdd.update("UPDATE empleados SET apellido = ?, oficio = ?, dir = ?, fecha_alt = ?, salario = ?, comision = ?, dept_no = ? WHERE emp_no = ?",
                this.apellido, this.oficio, this.dir, this.fecha_alt, this.salario, this.comision, this.dept_no, this.emp_no); 
      } catch (SQLException ex) {
        Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
      } 
    }
    
    
    public static void delete(OperacionesBBDD bbdd, int id_empleado){
      try {
        bbdd.delete("DELETE FROM empleados WHERE emp_no = ?", id_empleado);

      } catch (SQLException ex) {
        Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    @Override
    public String toString() {
      return "Empleado{" + "emp_no=" + emp_no + ", apellido=" + apellido + ", oficio=" + oficio + ", dir=" + dir + ", fecha_alt=" + fecha_alt + ", salario=" + salario + ", comision=" + comision + ", dept_no=" + dept_no + '}';
    }
}