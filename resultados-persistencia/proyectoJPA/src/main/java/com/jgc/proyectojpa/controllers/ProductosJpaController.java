/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectojpa.controllers;

import com.jgc.proyectojpa.exceptions.IllegalOrphanException;
import com.jgc.proyectojpa.exceptions.NonexistentEntityException;
import com.jgc.proyectojpa.exceptions.PreexistingEntityException;
import com.jgc.proyectojpa.model.Productos;
import com.jgc.proyectojpa.model.Ventas;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author rezzt
 */
public class ProductosJpaController implements Serializable {

  public ProductosJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Productos productos) throws PreexistingEntityException, Exception {
    if (productos.getVentasCollection() == null) {
      productos.setVentasCollection(new ArrayList<Ventas>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Collection<Ventas> attachedVentasCollection = new ArrayList<Ventas>();
      for (Ventas ventasCollectionVentasToAttach : productos.getVentasCollection()) {
        ventasCollectionVentasToAttach = em.getReference(ventasCollectionVentasToAttach.getClass(), ventasCollectionVentasToAttach.getIdventa());
        attachedVentasCollection.add(ventasCollectionVentasToAttach);
      }
      productos.setVentasCollection(attachedVentasCollection);
      em.persist(productos);
      for (Ventas ventasCollectionVentas : productos.getVentasCollection()) {
        Productos oldIdproductoOfVentasCollectionVentas = ventasCollectionVentas.getIdproducto();
        ventasCollectionVentas.setIdproducto(productos);
        ventasCollectionVentas = em.merge(ventasCollectionVentas);
        if (oldIdproductoOfVentasCollectionVentas != null) {
          oldIdproductoOfVentasCollectionVentas.getVentasCollection().remove(ventasCollectionVentas);
          oldIdproductoOfVentasCollectionVentas = em.merge(oldIdproductoOfVentasCollectionVentas);
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findProductos(productos.getId()) != null) {
        throw new PreexistingEntityException("Productos " + productos + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Productos productos) throws IllegalOrphanException, NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Productos persistentProductos = em.find(Productos.class, productos.getId());
      Collection<Ventas> ventasCollectionOld = persistentProductos.getVentasCollection();
      Collection<Ventas> ventasCollectionNew = productos.getVentasCollection();
      List<String> illegalOrphanMessages = null;
      for (Ventas ventasCollectionOldVentas : ventasCollectionOld) {
        if (!ventasCollectionNew.contains(ventasCollectionOldVentas)) {
          if (illegalOrphanMessages == null) {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Ventas " + ventasCollectionOldVentas + " since its idproducto field is not nullable.");
        }
      }
      if (illegalOrphanMessages != null) {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      Collection<Ventas> attachedVentasCollectionNew = new ArrayList<Ventas>();
      for (Ventas ventasCollectionNewVentasToAttach : ventasCollectionNew) {
        ventasCollectionNewVentasToAttach = em.getReference(ventasCollectionNewVentasToAttach.getClass(), ventasCollectionNewVentasToAttach.getIdventa());
        attachedVentasCollectionNew.add(ventasCollectionNewVentasToAttach);
      }
      ventasCollectionNew = attachedVentasCollectionNew;
      productos.setVentasCollection(ventasCollectionNew);
      productos = em.merge(productos);
      for (Ventas ventasCollectionNewVentas : ventasCollectionNew) {
        if (!ventasCollectionOld.contains(ventasCollectionNewVentas)) {
          Productos oldIdproductoOfVentasCollectionNewVentas = ventasCollectionNewVentas.getIdproducto();
          ventasCollectionNewVentas.setIdproducto(productos);
          ventasCollectionNewVentas = em.merge(ventasCollectionNewVentas);
          if (oldIdproductoOfVentasCollectionNewVentas != null && !oldIdproductoOfVentasCollectionNewVentas.equals(productos)) {
            oldIdproductoOfVentasCollectionNewVentas.getVentasCollection().remove(ventasCollectionNewVentas);
            oldIdproductoOfVentasCollectionNewVentas = em.merge(oldIdproductoOfVentasCollectionNewVentas);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Short id = productos.getId();
        if (findProductos(id) == null) {
          throw new NonexistentEntityException("The productos with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Productos productos;
      try {
        productos = em.getReference(Productos.class, id);
        productos.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The productos with id " + id + " no longer exists.", enfe);
      }
      List<String> illegalOrphanMessages = null;
      Collection<Ventas> ventasCollectionOrphanCheck = productos.getVentasCollection();
      for (Ventas ventasCollectionOrphanCheckVentas : ventasCollectionOrphanCheck) {
        if (illegalOrphanMessages == null) {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This Productos (" + productos + ") cannot be destroyed since the Ventas " + ventasCollectionOrphanCheckVentas + " in its ventasCollection field has a non-nullable idproducto field.");
      }
      if (illegalOrphanMessages != null) {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      em.remove(productos);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Productos> findProductosEntities() {
    return findProductosEntities(true, -1, -1);
  }

  public List<Productos> findProductosEntities(int maxResults, int firstResult) {
    return findProductosEntities(false, maxResults, firstResult);
  }

  private List<Productos> findProductosEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Productos.class));
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

  public Productos findProductos(Short id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Productos.class, id);
    } finally {
      em.close();
    }
  }

  public int getProductosCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Productos> rt = cq.from(Productos.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
