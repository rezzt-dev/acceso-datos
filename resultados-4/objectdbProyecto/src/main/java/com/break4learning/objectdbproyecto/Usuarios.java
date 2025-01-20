/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.break4learning.objectdbproyecto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author jgarc
 */
@Entity
@NamedQueries({
    @NamedQuery(name="USUARIOS.findAll",
                query="SELECT u FROM Usuarios u"),
    @NamedQuery(name="USUARIOS.findById",
                query="Select u from Usuarios u WHERE u.idusuario=:IDUSUARIOP"),
})
public class Usuarios implements Serializable {


    private static final long serialVersionUID = 5369277129485652678L;

    @Id @GeneratedValue
   // @Column(name = "IDUSUARIO")
    private int idusuario;
   // @Column(name = "NOMBRE")
    private String nombre;
   // @Column(name = "CONTRA")
    private String contra;
    @OneToMany(mappedBy = "idusuario",orphanRemoval=true)
    private List<Pedidos> pedidosCollection;

    public Usuarios() {
    }
    
    public Usuarios(int idusuario, String nombre, String contra) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.contra = contra;
    }

    public Usuarios(int idusuario, String nombre, String contra, List<Pedidos> pedidosCollection) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.contra = contra;
        this.pedidosCollection = pedidosCollection;
    }

    public Usuarios(String nombre, String contra) {
        this.nombre = nombre;
        this.contra = contra;
    }


    public Usuarios(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public List<Pedidos> getPedidosCollection() {
        return pedidosCollection;
    }

    public void setPedidosCollection(List<Pedidos> pedidosCollection) {
        this.pedidosCollection = pedidosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuarios other = (Usuarios) obj;
        if (this.idusuario != other.idusuario) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.contra, other.contra)) {
            return false;
        }
        if (!Objects.equals(this.pedidosCollection, other.pedidosCollection)) {
            return false;
        }
        return true;
    }
}
