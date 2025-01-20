/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.jgc.proyectojpa.model;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 13 dic 2024
 */
@Entity
@Table(name = "CLIENTES")
@NamedQueries({
  @NamedQuery(name = "Clientes.findAll", query = "SELECT c FROM Clientes c"),
  @NamedQuery(name = "Clientes.findById", query = "SELECT c FROM Clientes c WHERE c.id = :id"),
  @NamedQuery(name = "Clientes.findByNombre", query = "SELECT c FROM Clientes c WHERE c.nombre = :nombre"),
  @NamedQuery(name = "Clientes.findByDireccion", query = "SELECT c FROM Clientes c WHERE c.direccion = :direccion"),
  @NamedQuery(name = "Clientes.findByPoblacion", query = "SELECT c FROM Clientes c WHERE c.poblacion = :poblacion"),
  @NamedQuery(name = "Clientes.findByTelef", query = "SELECT c FROM Clientes c WHERE c.telef = :telef"),
  @NamedQuery(name = "Clientes.findByNif", query = "SELECT c FROM Clientes c WHERE c.nif = :nif")})
public class Clientes implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  private Short id;
  @Basic(optional = false)
  private String nombre;
  private String direccion;
  private String poblacion;
  private String telef;
  private String nif;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcliente")
  private List<Ventas> ventasCollection;

  public Clientes() {
  }

  public Clientes(Short id) {
    this.id = id;
  }

  public Clientes(Short id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public Short getId() {
    return id;
  }

  public void setId(Short id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getPoblacion() {
    return poblacion;
  }

  public void setPoblacion(String poblacion) {
    this.poblacion = poblacion;
  }

  public String getTelef() {
    return telef;
  }

  public void setTelef(String telef) {
    this.telef = telef;
  }

  public String getNif() {
    return nif;
  }

  public void setNif(String nif) {
    this.nif = nif;
  }

  public List<Ventas> getVentasCollection() {
    return ventasCollection;
  }

  public void setVentasCollection(List<Ventas> ventasCollection) {
    this.ventasCollection = ventasCollection;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Clientes)) {
      return false;
    }
    Clientes other = (Clientes) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.jgc.proyectojpa.model.Clientes[ id=" + id + " ]";
  }

}
