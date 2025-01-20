/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.break4learning.objectdbproyecto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author jgarc
 */
@Entity
public class Productosesp implements Serializable {

    private static final long serialVersionUID = 5369277129485652678L;
    @Id @GeneratedValue
    //@Column(name = "IDPRODUCTO")
    private int idproducto;
    //@Column(name = "NOMBRE")
    private String nombre;
    //@Column(name = "PRECIO")
    private int precio;
    @OneToOne(mappedBy = "idproducto",orphanRemoval=true)
    private Pedidos pedido;
    
    public Productosesp() {
    }

    public Productosesp(int idproducto) {
        this.idproducto = idproducto;
    }
    
    public Productosesp(String nombre, int precio){
        this.nombre = nombre;
        this.precio = precio;
    }

    public Productosesp(int idproducto, String nombre, int precio) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }
    
    
    
    
    
}
