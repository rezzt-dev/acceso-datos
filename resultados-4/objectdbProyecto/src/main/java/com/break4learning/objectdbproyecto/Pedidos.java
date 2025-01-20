/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.break4learning.objectdbproyecto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author jgarc
 */
@Entity
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 5369277129485652678L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id @GeneratedValue
    //@Column(name = "IDPEDIDO")
    private int idpedido;
    @Id
    //@Column(name = "PRECIO_TOTAL")
    private int precioTotal;
    //@Column(name = "FECHA_ENTREGA")
    private Date fechaEntrega;
    @JoinColumn(name = "IDPRODUCTO", referencedColumnName = "IDPRODUCTO")
    @OneToOne
    private Productosesp idproducto;
    @JoinColumn(name = "IDUSUARIO", referencedColumnName = "IDUSUARIO")
    @ManyToOne
    private Usuarios idusuario;

    public Pedidos() {
    }

    public Pedidos(int idpedido) {
        this.idpedido = idpedido;
    }
    
    public Pedidos(int precioTotal, Date fechaEntrega, Productosesp idproducto, Usuarios idusuario){
        this.precioTotal=precioTotal;
        this.fechaEntrega=fechaEntrega;
        this.idproducto=idproducto;
        this.idusuario=idusuario;
    }

    public Pedidos(int idpedido, int precioTotal, Date fechaEntrega, Productosesp idproducto, Usuarios idusuario) {
        this.idpedido = idpedido;
        this.precioTotal = precioTotal;
        this.fechaEntrega = fechaEntrega;
        this.idproducto = idproducto;
        this.idusuario = idusuario;
    }
    
    

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(int precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Productosesp getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Productosesp idproducto) {
        this.idproducto = idproducto;
    }

    public Usuarios getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuarios idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public String toString() {
        return "Pedidos{" + "idpedido=" + idpedido + ", precioTotal=" + precioTotal + ", fechaEntrega=" + fechaEntrega + ", idproducto=" + idproducto + ", idusuario=" + idusuario + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Pedidos other = (Pedidos) obj;
        if (this.idpedido != other.idpedido) {
            return false;
        }
        if (this.precioTotal != other.precioTotal) {
            return false;
        }
        if (!Objects.equals(this.fechaEntrega, other.fechaEntrega)) {
            return false;
        }
        if (!Objects.equals(this.idproducto, other.idproducto)) {
            return false;
        }
        if (!Objects.equals(this.idusuario, other.idusuario)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
