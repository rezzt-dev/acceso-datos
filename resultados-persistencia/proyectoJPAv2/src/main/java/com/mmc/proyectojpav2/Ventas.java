/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mmc.proyectojpav2;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author JGC by Juan Garcia Cazallas
 * @version 1.0
 * Created on 4 dic 2024
 */
@Entity
@Table(name = "VENTAS")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Ventas.findAll", query = "SELECT v FROM Ventas v"),
  @NamedQuery(name = "Ventas.findByIdventa", query = "SELECT v FROM Ventas v WHERE v.idventa = :idventa"),
  @NamedQuery(name = "Ventas.findByFechaventa", query = "SELECT v FROM Ventas v WHERE v.fechaventa = :fechaventa"),
  @NamedQuery(name = "Ventas.findByCantidad", query = "SELECT v FROM Ventas v WHERE v.cantidad = :cantidad")})
public class Ventas implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "IDVENTA")
  private Integer idventa;
  @Basic(optional = false)
  @NotNull
  @Column(name = "FECHAVENTA")
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaventa;
  @Basic(optional = false)
  @NotNull
  @Column(name = "CANTIDAD")
  private short cantidad;
  @JoinColumn(name = "IDCLIENTE", referencedColumnName = "ID")
  @ManyToOne(optional = false)
  private Clientes idcliente;
  @JoinColumn(name = "IDPRODUCTO", referencedColumnName = "ID")
  @ManyToOne(optional = false)
  private Productos idproducto;

  public Ventas() {
  }

  public Ventas(Integer idventa) {
    this.idventa = idventa;
  }

  public Ventas(Integer idventa, Date fechaventa, short cantidad) {
    this.idventa = idventa;
    this.fechaventa = fechaventa;
    this.cantidad = cantidad;
  }

  public Integer getIdventa() {
    return idventa;
  }

  public void setIdventa(Integer idventa) {
    this.idventa = idventa;
  }

  public Date getFechaventa() {
    return fechaventa;
  }

  public void setFechaventa(Date fechaventa) {
    this.fechaventa = fechaventa;
  }

  public short getCantidad() {
    return cantidad;
  }

  public void setCantidad(short cantidad) {
    this.cantidad = cantidad;
  }

  public Clientes getIdcliente() {
    return idcliente;
  }

  public void setIdcliente(Clientes idcliente) {
    this.idcliente = idcliente;
  }

  public Productos getIdproducto() {
    return idproducto;
  }

  public void setIdproducto(Productos idproducto) {
    this.idproducto = idproducto;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idventa != null ? idventa.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Ventas)) {
      return false;
    }
    Ventas other = (Ventas) object;
    if ((this.idventa == null && other.idventa != null) || (this.idventa != null && !this.idventa.equals(other.idventa))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.mmc.proyectojpav2.Ventas[ idventa=" + idventa + " ]";
  }

}
