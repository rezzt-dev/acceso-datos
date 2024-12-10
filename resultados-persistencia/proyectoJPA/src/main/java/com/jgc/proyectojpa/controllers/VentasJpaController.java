/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectojpa.controllers;

import com.jgc.proyectojpa.exceptions.NonexistentEntityException;
import com.jgc.proyectojpa.exceptions.PreexistingEntityException;
import com.jgc.proyectojpa.model.Clientes;
import com.jgc.proyectojpa.model.Productos;
import com.jgc.proyectojpa.model.Ventas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author rezzt
 */
public class VentasJpaController implements Serializable {

  public VentasJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Ventas ventas) throws PreexistingEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Clientes idcliente = ventas.getIdcliente();
      if (idcliente != null) {
        idcliente = em.getReference(idcliente.getClass(), idcliente.getId());
        ventas.setIdcliente(idcliente);
      }
      Productos idproducto = ventas.getIdproducto();
      if (idproducto != null) {
        idproducto = em.getReference(idproducto.getClass(), idproducto.getId());
        ventas.setIdproducto(idproducto);
      }
      em.persist(ventas);
      if (idcliente != null) {
        idcliente.getVentasCollection().add(ventas);
        idcliente = em.merge(idcliente);
      }
      if (idproducto != null) {
        idproducto.getVentasCollection().add(ventas);
        idproducto = em.merge(idproducto);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findVentas(ventas.getIdventa()) != null) {
        throw new PreexistingEntityException("Ventas " + ventas + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Ventas ventas) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Ventas persistentVentas = em.find(Ventas.class, ventas.getIdventa());
      Clientes idclienteOld = persistentVentas.getIdcliente();
      Clientes idclienteNew = ventas.getIdcliente();
      Productos idproductoOld = persistentVentas.getIdproducto();
      Productos idproductoNew = ventas.getIdproducto();
      if (idclienteNew != null) {
        idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getId());
        ventas.setIdcliente(idclienteNew);
      }
      if (idproductoNew != null) {
        idproductoNew = em.getReference(idproductoNew.getClass(), idproductoNew.getId());
        ventas.setIdproducto(idproductoNew);
      }
      ventas = em.merge(ventas);
      if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
        idclienteOld.getVentasCollection().remove(ventas);
        idclienteOld = em.merge(idclienteOld);
      }
      if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
        idclienteNew.getVentasCollection().add(ventas);
        idclienteNew = em.merge(idclienteNew);
      }
      if (idproductoOld != null && !idproductoOld.equals(idproductoNew)) {
        idproductoOld.getVentasCollection().remove(ventas);
        idproductoOld = em.merge(idproductoOld);
      }
      if (idproductoNew != null && !idproductoNew.equals(idproductoOld)) {
        idproductoNew.getVentasCollection().add(ventas);
        idproductoNew = em.merge(idproductoNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = ventas.getIdventa();
        if (findVentas(id) == null) {
          throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Integer id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Ventas ventas;
      try {
        ventas = em.getReference(Ventas.class, id);
        ventas.getIdventa();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
      }
      Clientes idcliente = ventas.getIdcliente();
      if (idcliente != null) {
        idcliente.getVentasCollection().remove(ventas);
        idcliente = em.merge(idcliente);
      }
      Productos idproducto = ventas.getIdproducto();
      if (idproducto != null) {
        idproducto.getVentasCollection().remove(ventas);
        idproducto = em.merge(idproducto);
      }
      em.remove(ventas);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Ventas> findVentasEntities() {
    return findVentasEntities(true, -1, -1);
  }

  public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
    return findVentasEntities(false, maxResults, firstResult);
  }

  private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Ventas.class));
      Query q = em.createQuery(cq);
      if (!all) {
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
      }
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  public Ventas findVentas(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Ventas.class, id);
    } finally {
      em.close();
    }
  }

  public int getVentasCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Ventas> rt = cq.from(Ventas.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
