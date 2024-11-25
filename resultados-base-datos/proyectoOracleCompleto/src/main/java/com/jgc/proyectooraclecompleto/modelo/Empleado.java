/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectooraclecompleto.modelo;

import com.jgc.proyectooraclecompleto.conection.OperacionesBBDD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rezzt
 */
public class Empleado {
   // constantes & atributos -->
  private int numEmp;
  private String apellido;
  private String oficio;
  private int dirEmp;
  private Date fechaAlta;
  private double salario;
  private double comision;
  private int numDept;
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // constructores -->
  public Empleado () {}
  
  public Empleado(int numEmp, String apellido, String oficio, int dirEmp, Date fechaAlta, double salario, double comision, int numDept) {
    this.numEmp = numEmp;
    this.apellido = apellido;
    this.oficio = oficio;
    this.dirEmp = dirEmp;
    this.fechaAlta = fechaAlta;
    this.salario = salario;
    this.comision = comision;
    this.numDept = numDept;
  }
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // metodos privados & publicos -->
    // metodo | insert | inserta un empleado de la bbdd -->  
  public void insert (OperacionesBBDD bbdd) {
    boolean boolCerrar = false;
    
    try {
      while (boolCerrar == true) {
        // Comprobar si el departamento existe
        if (!departamentoExiste(bbdd, numDept)) {
          System.out.println("Error: El departamento no existe.");
          boolCerrar = true;
        }

        // Comprobar si el número de empleado ya existe
        if (empleadoExiste(bbdd, numEmp)) {
          System.out.println("Error: El número de empleado ya existe.");
          boolCerrar = true;
        }

        // Comprobar que el salario sea mayor o igual a 0
        if (salario <= 0) {
          System.out.println("Error: El salario debe ser mayor que 0.");
          boolCerrar = true;
        }

        // Comprobar que el director exista
        if (dirEmp == 0 || empleadoExiste(bbdd, dirEmp)) {
          System.out.println("Error: El director no existe.");
          boolCerrar = true;
        }

        // Comprobar que el apellido y el oficio no sean nulos
        if (apellido == null || apellido.trim().isEmpty()) {
          System.out.println("Error: El apellido no puede ser nulo o vacío.");
          boolCerrar = true;
        }

        if (oficio == null || oficio.trim().isEmpty()) {
          System.out.println("Error: El oficio no puede ser nulo o vacío.");
          boolCerrar = true;
        }
        
        fechaAlta = Date.valueOf(LocalDate.now());
        bbdd.insert("INSERT INTO empleados values (?,?,?,?,?,?,?,?)",
                numEmp, apellido, oficio, dirEmp, fechaAlta, salario, comision, numDept);
        
        System.out.println("Empleado insertado correctamente");
        selectById(bbdd, numEmp);
        
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  private boolean departamentoExiste (OperacionesBBDD bbdd, int nDept) {
    boolean deptExiste = false;
    
    try {
      Optional<ResultSet> result = bbdd.select("SELECT COUNT(*) FROM departamentos WHERE dept_no = ?", nDept);
      if (result.isPresent()) {
        ResultSet rs = result.get();
        
        if (rs.next()) {
          deptExiste = rs.getInt(1)>0;
        } else {
          deptExiste = false;
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return deptExiste;
  }
  
  private boolean empleadoExiste (OperacionesBBDD bbdd, int nEmp) {
    boolean empleExiste = false;
    
    try {
      Optional<ResultSet> result = bbdd.select("SELECT COUNT(*) FROM empleados WHERE emp_no = ?", nEmp);
      
      if (result.isPresent()) {
        ResultSet rs = result.get();
        
        if (rs.next()) {
          empleExiste = rs.getInt(1)>0;
        } else {
          empleExiste = false;
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return empleExiste;
  }

   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | update | actualiza un empleado de la bbdd -->
  public void update (OperacionesBBDD bbdd) {
    try {
      bbdd.update("UPDATE empleados set apellido = ?, oficio = ?, dir = ?, fecha_alt = ?, salario = ?, comision = ?, dept = ? where emp_no = ?",
              this.apellido, this.oficio, this.dirEmp, this.fechaAlta, this.salario, this.comision, this.numDept, this.numEmp);
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | delete | elimina un empleado de la bbdd -->
  public void delete (OperacionesBBDD bbdd, int nEmp) {
    try {
      bbdd.delete("DELETE FROM empleados WHERE emp_no = ?", nEmp);
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | selectById | selecciona un empleado por su id -->
  public void selectById (OperacionesBBDD bbdd, int inputId) {
    try {
      Optional<ResultSet> result = bbdd.select("SELECT * FROM empleados WHERE emp_no = ?", inputId);
      
      if (result.isPresent()) {
        while (result.get().next()) {
          this.numEmp = result.get().getInt("emp_no");
          this.apellido = result.get().getString("apellido");
          this.oficio = result.get().getString("oficio");
          this.dirEmp = result.get().getInt("dir");
          this.fechaAlta = result.get().getDate("fecha_alt");
          this.salario = result.get().getDouble("salario");
          this.comision = result.get().getDouble("comision");
          this.numDept = result.get().getInt("dept_no");
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | update | actualizamos un departamento pero con resultset -->
  public void update (ResultSet result) {
    try {
      result.beforeFirst();
      
      while (result.next()) {
        result.updateString("apellido", apellido);
        result.updateString("oficio", oficio);
        result.updateInt("dir", dirEmp);
        result.updateDate("fecha_alt", fechaAlta);
        result.updateDouble("salario", salario);
        result.updateDouble("comision", comision);
        result.updateInt("dept_no", numDept);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | selectAll | seleccionamos todos los empleados que haya en la tabla -->
  public static Optional<ResultSet> selectAll (OperacionesBBDD bbdd) {
    Optional<ResultSet> result = null;
    
    try {
      result = bbdd.select("SELECT * FROM empleados");
    } catch (SQLException ex) {
      System.out.println(" > Ha ocurrido un error: ");
      System.out.println("  - Mensaje: " + ex.getMessage());
      System.out.println("  - SQL Estado: " + ex.getSQLState());
      
      switch (ex.getErrorCode()) {
        case 942:
          System.out.println("  - Esta tabla no existe.");
        default:
          System.out.println("  - Error no identificado.");
      }
    }
    
    return result;
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getApellidoEmp | obtenemos el apellido de un empleado -->
  public static void getApellidoEmp (OperacionesBBDD bbdd, int inputNumEmp) {
    String salida = "";
    Connection conexion = bbdd.getConexion();
    
    try {
      String sqlProced = "{call p_nombre_emple (?,?)}";
      CallableStatement llamada = conexion.prepareCall(sqlProced);
      
      llamada.setInt(1, inputNumEmp);
      llamada.registerOutParameter(2, Types.VARCHAR);
      
      llamada.executeUpdate();
      salida = " > Empleado " + inputNumEmp + ": " + llamada.getString(2);
    } catch (SQLException ex) {
      if (ex.getSQLState().equals("02000") || ex.getErrorCode() == 1403) {
        salida = " > El Empleado con el id " + inputNumEmp + " no existe.";
      }
    } finally {
      System.out.println(salida);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | mostrarResultSet | obtener informacion de un empleado con resultset -->
  public static void mostrarResultSet (Optional<ResultSet> result) {
    try {
      if (result.isPresent()) {
        while (result.get().next()) {
          System.out.println(" > Empleado " + result.get().getInt("emp_no") + 
                  " | Apellido: " + result.get().getString("apellido") + 
                  " | Oficio: " + result.get().getString("oficio") + 
                  " | Direccion: " + result.get().getInt("dir") + 
                  " | Fecha Alta: " + result.get().getDate("fecha_alt") + 
                  " | Salario: " + result.get().getDouble("salario") + 
                  " | Comision: " + result.get().getDouble("comision") + 
                  " | Num Depart: " + result.get().getInt("dept_no"));
        }
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getNumEmpleados | obtiene el numero de empleados que existen -->
  public static int getNumEmpleados (OperacionesBBDD bbdd) {
    int numEmples = 0;
    
    try {
      Optional<ResultSet> result = bbdd.select("SELECT COUNT(*) AS total FROM empleados");
      
      if (result.isPresent() && result.get().next()) {
        numEmples = result.get().getInt("total");
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return numEmples;
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getEmpleadoFromDepart | obtiene los empleados que esten en x departamento -->
  public static void getEmpleadoFromDepart (OperacionesBBDD bbdd, int nDept) {
    try {
      Optional<ResultSet> result = bbdd.select("SELECT * FROM empleados WHERE dept_no = ?", nDept);
      
      if (result.isPresent()) {
        ResultSet rs = result.get();
        
        if (rs.isBeforeFirst()) {
          System.out.println(" > No se encontraron empleados en el departamento " + nDept);
        } else {
          while (rs.next()) {
            System.out.println(" > Empleado " + rs.getInt("emp_no") + 
                    " Apellido: " + rs.getString("apellido") + 
                    "Oficio: " + rs.getString("oficio") + 
                    "Direccion: " + rs.getInt("dir") + 
                    "Fecha Alta: " + rs.getDate("fecha_alt") + 
                    "Salario: " + rs.getDouble("salario") + 
                    "Comision: " + rs.getDouble("comision"));
          }
        }
      } else {
        System.out.println(" > No se pudo obtener empleados del departamento " + nDept);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getApellidoEmpleFromMaxSalario | obtiene el empleado con mayor salario -->
  public static void getApellidoEmpleFromMaxSalario (OperacionesBBDD bbdd) {
    try {
      Optional<ResultSet> result = bbdd.select("SELECT apellido, salario FROM empleados WHERE salario = (SELECT MAX(salario) FROM empleados)");
      
      if (result.isPresent()) {
        ResultSet rs = result.get();
        
        if (rs.next()) {
          String apellido = rs.getString("apellido");
          double salario = rs.getDouble("salario");
          System.out.println(" > El empleado con mayor salario es " + apellido + " con un salario de " + salario);
        }
      } else {
        System.out.println(" > No se encontraron empleados.");
      }
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | setSalarioSubidaBajadaPorcentaje | aplica una subida/bajada a todos los salarios de los empleados basado en un porcentaje -->
  public static void setSalarioSubidaBajadaPorcentaje (OperacionesBBDD bbdd, double inputPorcentaje, String inputOpcion) {
    try {
      if (!inputOpcion.equalsIgnoreCase("subida") && !inputOpcion.equalsIgnoreCase("bajada")) {
        System.out.println("La opcion debe ser subida o bajada");
      }
      
      double factor = (inputOpcion.equalsIgnoreCase("subida")) ? (1 + inputPorcentaje / 100) : (1 + inputPorcentaje / 100);
      int emplesAfectados = bbdd.update("UPDATE empleados SET salario = salario * ?", factor);
      
      System.out.println(" > Se actualzaron los salario de " + emplesAfectados + " empleados.");
      
    } catch (SQLException ex) {
      Logger.getLogger(Empleado.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // getters, setters & override -->
  public int getNumEmp() {
    return numEmp;
  }

  public void setNumEmp(int numEmp) {
    this.numEmp = numEmp;
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

  public int getDirEmp() {
    return dirEmp;
  }

  public void setDirEmp(int dirEmp) {
    this.dirEmp = dirEmp;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
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

  public int getNumDept() {
    return numDept;
  }

  public void setNumDept(int numDept) {
    this.numDept = numDept;
  }
}
