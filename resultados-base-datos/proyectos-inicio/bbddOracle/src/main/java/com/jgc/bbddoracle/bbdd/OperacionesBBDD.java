/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.bbddoracle.bbdd;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 nov 2024
 */
public class OperacionesBBDD {
  private String driver;
  private String urlconnection;

  private Connection conexion = null;
  private Properties propiedades = null;
  
  private static DatabaseMetaData dbmd;
  
  private static OperacionesBBDD operacionesBBDD;
  private PreparedStatement preparedStatement;
  
  private OperacionesBBDD () {
    driver = "oracle.jdbc.driver.OracleDriver";
    urlconnection = "jdbc:oracle:thin:@localhost:1521";
  }
  
  public static OperacionesBBDD getInstance () {
    if (operacionesBBDD == null) {
      operacionesBBDD = new OperacionesBBDD();
    }
    
    return operacionesBBDD;
  }
  
  public void abrirConexion () {
    try {
      this.propiedades = new Properties();
      this.propiedades.setProperty("user", "dam2");
      this.propiedades.setProperty("password", "dam2");
      this.propiedades.setProperty("bbdd", "FREE");
      
      Class.forName(driver);
      this.conexion = DriverManager.getConnection(urlconnection, propiedades);
      
      dbmd = conexion.getMetaData();
    } catch (ClassNotFoundException | SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void cerrarConexion () {
    try {
      this.conexion.close();
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public Optional<ResultSet> insert(String inputInsertSQL, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputInsertSQL,PreparedStatement.RETURN_GENERATED_KEYS);
    
     // inputInsertSQL => "insert into Departamentos values (?,?,?)"
     // params =>  [1, "informatica", "ciudad real"]
    
    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
    
    preparedStatement.executeUpdate();
    return  Optional.of(preparedStatement.getGeneratedKeys());
  }
  
  private ResultSet executeQuery (String inputQuerySQL, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputQuerySQL);
    
    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
    
    return preparedStatement.executeQuery();
  }
  
  public Optional<ResultSet> select (String inputQuerySQL, Object... params) throws SQLException {
    return Optional.of(executeQuery(inputQuerySQL, params));
  }
  
  private int updateQuery (String inputQuerySQL, Object... params) throws SQLException {
    preparedStatement = conexion.prepareStatement(inputQuerySQL);

    for (int i=0; i<params.length; i++) {
      preparedStatement.setObject(i+1, params[i]);
    }
     
    return preparedStatement.executeUpdate();
  }
  
  public int update (String inputQuerySQL, Object... params) throws SQLException {
    return updateQuery(inputQuerySQL, params);
  }
  
  public int delete (String inputQuerySQL, Object... params) throws SQLException {
    return updateQuery(inputQuerySQL, params);
  }
  
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
  
  public void obtenerInformacionDeLasTablas() {
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
  
  public void obtenerInformacionDeLasColumnas(String nombreTabla) {
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
  
  public void obtenerInformacionDelResultSet(Optional<ResultSet> rs) {
    try {
      ResultSetMetaData rsmd = rs.get().getMetaData();

      //Obtiene el número de columnas devueltas por la tabla
      int numColumnas = rsmd.getColumnCount();

      //Obtiene el nombre de la columna de la posición "i"
      String nombre_columna = rsmd.getColumnName(2);

      //Obtiene el tipo de datos de la columna de la posición "i"
      String tipo_columna = rsmd.getColumnTypeName(2);

      //Obtiene "0" si la columna de la posición "i" puede contener valores nulos
      int valores_nulos = rsmd.isNullable(2);

      //Obtiene el máximo número de caracteres de la columna de la posición "i"
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
  
  public void obtenerNumeroFilasDevueltas(Optional<ResultSet> rs) {
    try {
      //Solo para tener en cuenta que este método necesitará un conjunto de resultados sensible al desplazamiento.
      //El valor predeterminado es FORWARD (ADELANTE) y el uso de este método generará una excepción.
      //Para poder hacer el last() o beforeFirst la siguiente instrucción debe modificarse
      //preparedStatement = conexion.prepareStatement(querySQL);
      //por
      //preparedStatement = conexion.prepareStatement(querySQL,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

      int rows = 0; //0 porque si rs.last no funciona no entraría en el if y entonces es que no ha devuelto datos

      if (rs.get().last()) {
        rows = rs.get().getRow();
        // Nos volvemos asituar en el primero por si queremos seguir trabajandocon el resulset
        rs.get().beforeFirst();
      }

      System.out.println("El número de filas devueltas es:" + rows);
    } catch (SQLException ex) {
      Logger.getLogger(OperacionesBBDD.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public boolean obtenerInformacionOperacionesResultSet(){
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

  public Connection getConexion() {
    return conexion;
  }
}
