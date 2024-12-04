/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mmc.proyectojpav2;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 dic 2024
 */
@Entity
@Table(name = "PRODUCTOS")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p"),
  @NamedQuery(name = "Productos.findById", query = "SELECT p FROM Productos p WHERE p.id = :id"),
  @NamedQuery(name = "Productos.findByDescripcion", query = "SELECT p FROM Productos p WHERE p.descripcion = :descripcion"),
  @NamedQuery(name = "Productos.findByStockactual", query = "SELECT p FROM Productos p WHERE p.stockactual = :stockactual"),
  @NamedQuery(name = "Productos.findByStockminimo", query = "SELECT p FROM Productos p WHERE p.stockminimo = :stockminimo"),
  @NamedQuery(name = "Productos.findByPvp", query = "SELECT p FROM Productos p WHERE p.pvp = :pvp")})
public class Productos implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "ID")
  private Short id;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 50)
  @Column(name = "DESCRIPCION")
  private String descripcion;
  @Column(name = "STOCKACTUAL")
  private Short stockactual;
  @Column(name = "STOCKMINIMO")
  private Short stockminimo;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(name = "PVP")
  private BigDecimal pvp;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "idproducto")
  private Collection<Ventas> ventasCollection;

  public Productos() {
  }

  public Productos(Short id) {
    this.id = id;
  }

  public Productos(Short id, String descripcion) {
    this.id = id;
    this.descripcion = descripcion;
  }

  public Short getId() {
    return id;
  }

  public void setId(Short id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Short getStockactual() {
    return stockactual;
  }

  public void setStockactual(Short stockactual) {
    this.stockactual = stockactual;
  }

  public Short getStockminimo() {
    return stockminimo;
  }

  public void setStockminimo(Short stockminimo) {
    this.stockminimo = stockminimo;
  }

  public BigDecimal getPvp() {
    return pvp;
  }

  public void setPvp(BigDecimal pvp) {
    this.pvp = pvp;
  }

  @XmlTransient
  public Collection<Ventas> getVentasCollection() {
    return ventasCollection;
  }

  public void setVentasCollection(Collection<Ventas> ventasCollection) {
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
    if (!(object instanceof Productos)) {
      return false;
    }
    Productos other = (Productos) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.mmc.proyectojpav2.Productos[ id=" + id + " ]";
  }

}
