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
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author MMC by Mateo Molina Campos
 * @version 1.0
 * Created on 29 nov 2024
 *
 */
@Entity
@Table(name = "EMPLEADOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e"),
    @NamedQuery(name = "Empleados.findByEmpNo", query = "SELECT e FROM Empleados e WHERE e.empNo = :empNo"),
    @NamedQuery(name = "Empleados.findByApellido", query = "SELECT e FROM Empleados e WHERE e.apellido = :apellido"),
    @NamedQuery(name = "Empleados.findByOficio", query = "SELECT e FROM Empleados e WHERE e.oficio = :oficio"),
    @NamedQuery(name = "Empleados.findByDir", query = "SELECT e FROM Empleados e WHERE e.dir = :dir"),
    @NamedQuery(name = "Empleados.findByFechaAlt", query = "SELECT e FROM Empleados e WHERE e.fechaAlt = :fechaAlt"),
    @NamedQuery(name = "Empleados.findBySalario", query = "SELECT e FROM Empleados e WHERE e.salario = :salario"),
    @NamedQuery(name = "Empleados.findByComision", query = "SELECT e FROM Empleados e WHERE e.comision = :comision")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMP_NO")
    private Short empNo;
    @Size(max = 10)
    @Column(name = "APELLIDO")
    private String apellido;
    @Size(max = 10)
    @Column(name = "OFICIO")
    private String oficio;
    @Column(name = "DIR")
    private Short dir;
    @Column(name = "FECHA_ALT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALARIO")
    private BigDecimal salario;
    @Column(name = "COMISION")
    private BigDecimal comision;
    @JoinColumn(name = "DEPT_NO", referencedColumnName = "DEPT_NO")
    @ManyToOne
    private Departamentos deptNo;

    public Empleados() {
    }

    public Empleados(Short empNo) {
        this.empNo = empNo;
    }

    public Short getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Short empNo) {
        this.empNo = empNo;
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

    public Short getDir() {
        return dir;
    }

    public void setDir(Short dir) {
        this.dir = dir;
    }

    public Date getFechaAlt() {
        return fechaAlt;
    }

    public void setFechaAlt(Date fechaAlt) {
        this.fechaAlt = fechaAlt;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Departamentos getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Departamentos deptNo) {
        this.deptNo = deptNo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empNo != null ? empNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.empNo == null && other.empNo != null) || (this.empNo != null && !this.empNo.equals(other.empNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mmc.proyectojpav2.Empleados[ empNo=" + empNo + " ]";
    }

}
