package com.jgc.proyectopersistence;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase que representa la entidad Departamentos.
 */
@Entity
@Table(name = "DEPARTAMENTOS")
@NamedQueries({
  @NamedQuery(name = "Departamentos.findAll", query = "SELECT d FROM Departamentos d"),
  @NamedQuery(name = "Departamentos.findByDeptNo", query = "SELECT d FROM Departamentos d WHERE d.deptNo = :deptNo"),
  @NamedQuery(name = "Departamentos.findByDnombre", query = "SELECT d FROM Departamentos d WHERE d.dnombre = :dnombre"),
  @NamedQuery(name = "Departamentos.findByLoc", query = "SELECT d FROM Departamentos d WHERE d.loc = :loc")
})
public class Departamentos implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Basic(optional = false)
  @Column(name = "DEPT_NO")
  private Short deptNo;

  @Column(name = "DNOMBRE")
  private String dnombre;

  @Column(name = "LOC")
  private String loc;

  // Relación bidireccional con la entidad Empleados
  @OneToMany(mappedBy = "deptNo")
  private Collection<Empleados> empleadosCollection;

  // Constructor por defecto
  public Departamentos() {
  }

  // Constructor con parámetros
  public Departamentos(Short deptNo) {
    this.deptNo = deptNo;
  }

  // Getters y Setters
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

  public Collection<Empleados> getEmpleadosCollection() {
    return empleadosCollection;
  }

  public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
    this.empleadosCollection = empleadosCollection;
  }

  // Métodos hashCode y equals
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (deptNo != null ? deptNo.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Departamentos)) {
      return false;
    }
    Departamentos other = (Departamentos) object;
    return (this.deptNo != null || other.deptNo == null) && (this.deptNo == null || this.deptNo.equals(other.deptNo));
  }

  // Método toString
  @Override
  public String toString() {
    return "com.jgc.proyectopersistence.Departamentos[ deptNo=" + deptNo + " ]";
  }
}
