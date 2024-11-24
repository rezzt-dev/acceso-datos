/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectooraclecompleto.conection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rezzt
 */
public class OperacionesBBDD {
   // constantes & atributo ->
  private String driver;
  private String urlconnection;

  private Connection conexion = null;
  private Properties propiedades = null;
  
  private static DatabaseMetaData dbmd;
  
  private static OperacionesBBDD operacionesBBDD;
  private PreparedStatement preparedStatement;
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // constructores ->
  private OperacionesBBDD () {
    driver = "oracle.jdbc.driver.OracleDriver";
    urlconnection = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
  }
  
  public static OperacionesBBDD getInstance () {
    if (operacionesBBDD == null) {
      operacionesBBDD = new OperacionesBBDD();
    }
    
    return operacionesBBDD;
  }
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodos privados & publicos ->
     // metodo | abrirConexion | abre la conexion con la bbdd -->
  public void abrirConexion () {
    try {
      this.propiedades = new Properties();
      this.propiedades.setProperty("user", "oracle");
      this.propiedades.setProperty("password", "oracle");
      this.propiedades.setProperty("bbdd", "FREEPDB1");
      
      Class.forName(driver);
      this.conexion = DriverManager.getConnection(urlconnection, propiedades);
      
      dbmd = conexion.getMetaData();
    } catch (ClassNotFoundException | SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | cerrarConexion | cierra la conexion con la bbdd -->
  public void cerrarConexion () {
    try {
      this.conexion.close();
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | executeQuery | ejecuta una consulta en la bbdd -->
  private ResultSet executeQuery (String inputSQLQuery, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputSQLQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    
    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
    
    return preparedStatement.executeQuery();
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | updateQuery | actualiza una consulta en la bbdd -->
  private int updateQuery (String inputSQLQuery, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputSQLQuery);
    
    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
    
    return preparedStatement.executeUpdate();
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getInformationFromConexion | obtiene informacion de la conexion -->
  public void getInformationFromConexion () {
    try {
      String dbName = dbmd.getDatabaseProductName();
      String dbDriver = dbmd.getDriverName();
      String dbUrl = dbmd.getURL();
      
      String dbUser = dbmd.getUserName();

      System.out.println("Nombre del SGBD:" + dbName);
      System.out.println("Driver:" + dbDriver);
      System.out.println("Url:" + dbUrl);
      System.out.println("Usuario:" + dbUser);
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getInformationFromTables | obtiene informacion de las tablas -->
  public void getInformationFromTables() {
    try {
      ResultSet rs;
      String[] tipos = {"TABLE"};

      rs = dbmd.getTables(this.propiedades.getProperty("bbdd").toUpperCase(), 
              this.propiedades.getProperty("user").toUpperCase(),
              null, 
              tipos);

      String nombre_usuario;
      String nombre_tabla;

      while (rs.next()){
        nombre_usuario = rs.getString("TABLE_SCHEM");
        nombre_tabla = rs.getString("TABLE_NAME");

        System.out.println("USUARIO:" +nombre_usuario+ " TABLA:" + nombre_tabla);
      }
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getInformationFromColumns | obtiene informacion de las columnas -->
  public void getInformationFromColumns(String nombreTabla) {
    try {
      ResultSet rs;

      rs = dbmd.getColumns(this.propiedades.getProperty("bbdd").toUpperCase(), 
              this.propiedades.getProperty("user").toUpperCase(), 
              nombreTabla.toUpperCase(), 
              null);

      String nombre_tabla;
      String nombre_columna;

      while (rs.next()){
        nombre_tabla = rs.getString("TABLE_NAME");
        nombre_columna = rs.getString("COLUMN_NAME");

        System.out.println("TABLA:" +nombre_tabla+ " COLUMN:" + nombre_columna);
      }
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getInformationFromResultSet | obtiene informacion del resultset -->
  public void getInformationFromResultSet (Optional<ResultSet> rs) {
    try {
      ResultSetMetaData rsmd = rs.get().getMetaData();
      int numColumnas = rsmd.getColumnCount();
      String nombre_columna = rsmd.getColumnName(2);
      String tipo_columna = rsmd.getColumnTypeName(2);
      int valores_nulos = rsmd.isNullable(2);
      int tamaño_columna = rsmd.getColumnDisplaySize(2);

      System.out.println("Numero de columnas devueltas:" + numColumnas);
      System.out.println("Nombre de la columna 2:" + nombre_columna);
      System.out.println("Tipo de la columna 2:" + tipo_columna);
      System.out.println("Tamaño de la columna 2:" + tamaño_columna);
      System.out.println("Acepta nulos:" + ((valores_nulos==1)?"Si":"No"));
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getNumReturnRows | obtiene el numero de las filas devueltas -->
  public void obtenerNumeroFilasDevueltas(Optional<ResultSet> rs) {
    try {
      int rows = 0; //0 porque si rs.last no funciona no entraría en el if y entonces es que no ha devuelto datos

      if (rs.get().last()) {
        rows = rs.get().getRow();
        rs.get().beforeFirst();
      }

      System.out.println("El número de filas devueltas es:" + rows);
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | getOperationsInfoFromResultSet | obtiene informacion de las operaciones de resultset -->
  public boolean getOperationsInfoFromResultSet(){
    try {
      boolean isUpdatable = dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

      if (!isUpdatable) {
        return false;
      }
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return true;
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | insert | inserta una consulta en la bbdd -->
  public Optional<ResultSet> insert (String inputSQLQuery, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputSQLQuery, PreparedStatement.RETURN_GENERATED_KEYS);
    
    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
    
    preparedStatement.executeUpdate();
    return Optional.of(preparedStatement.getGeneratedKeys());
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | select | selecciona un objeto de la base de datos -->
  public Optional<ResultSet> select (String inputSQLQuery, Object... params) throws SQLException {
    return Optional.of(executeQuery(inputSQLQuery, params));
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | update | actualiza un objeto de la base de datos -->
  public int update (String inputQuerySQL, Object... params) throws SQLException {
    return updateQuery(inputQuerySQL, params);
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | delete | elimina un objeto de la base de datos -->
  public int delete (String inputQuerySQL, Object... params) throws SQLException {
    return updateQuery(inputQuerySQL, params);
  }
  
   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | callProcedSubidaSal | ejecuta el procedimiento "subida sal" -->
  public void callProcedSubidaSal (int numDept, double inputSubida) {
    String sql = "{call p_subida_sal(?, ?)}";
    try (CallableStatement stmt = conexion.prepareCall(sql)) {
      stmt.setInt(1, numDept);
      stmt.setDouble(2, inputSubida);
      stmt.execute();
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

   //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    // metodo | callFuncNumEmpleado | ejecuta la funcion "num empleado" -->
  public int callFuncNumEmpleado (int numDept) {
    String sql = "{? = call f_n_empleado(?)}";
    try (CallableStatement stmt = conexion.prepareCall(sql)) {
      stmt.registerOutParameter(1, Types.INTEGER);
      stmt.setInt(2, numDept);
      stmt.execute();
      int numEmpleados = stmt.getInt(1);
      
      System.out.println("Número de empleados en el departamento " + numDept + ": " + numEmpleados);
      return numEmpleados;
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
      return -1;
    }
  } 
  
 //—————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
   // getters, setters & overrides ->
    // getter | getConexion | devuelve la conexion de la bbdd -->
  public Connection getConexion() {
    return conexion;
  }
}
