/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectojpa.model;

import jakarta.persistence.Basic;
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
import java.util.Collection;

/**
 *
 * @author rezzt
 */
@Entity
@Table(name = "DEPARTAMENTOS")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Departamentos.findAll", query = "SELECT d FROM Departamentos d"),
  @NamedQuery(name = "Departamentos.findByDeptNo", query = "SELECT d FROM Departamentos d WHERE d.deptNo = :deptNo"),
  @NamedQuery(name = "Departamentos.findByDnombre", query = "SELECT d FROM Departamentos d WHERE d.dnombre = :dnombre"),
  @NamedQuery(name = "Departamentos.findByLoc", query = "SELECT d FROM Departamentos d WHERE d.loc = :loc")})
public class Departamentos implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "DEPT_NO")
  private Short deptNo;
  @Size(max = 15)
  @Column(name = "DNOMBRE")
  private String dnombre;
  @Size(max = 15)
  @Column(name = "LOC")
  private String loc;
  @OneToMany(mappedBy = "deptNo")
  private Collection<Empleados> empleadosCollection;

  public Departamentos() {
  }

  public Departamentos(Short deptNo) {
    this.deptNo = deptNo;
  }

  public Short getDeptNo() {
    return deptNo;
  }

  public void setDeptNo(Short deptNo) {
    this.deptNo = deptNo;
  }

  public String getDnombre() {
    return dnombre;
  }

  public void setDnombre(String dnombre) {
    this.dnombre = dnombre;
  }

  public String getLoc() {
    return loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  @XmlTransient
  public Collection<Empleados> getEmpleadosCollection() {
    return empleadosCollection;
  }

  public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
    this.empleadosCollection = empleadosCollection;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (deptNo != null ? deptNo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Departamentos)) {
      return false;
    }
    Departamentos other = (Departamentos) object;
    if ((this.deptNo == null && other.deptNo != null) || (this.deptNo != null && !this.deptNo.equals(other.deptNo))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.jgc.proyectojpa.model.Departamentos[ deptNo=" + deptNo + " ]";
  }
  
}
