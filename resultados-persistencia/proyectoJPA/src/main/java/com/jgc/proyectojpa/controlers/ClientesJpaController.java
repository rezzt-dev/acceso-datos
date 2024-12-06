/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectojpa.controlers;

import com.jgc.proyectojpa.Clientes;
import com.jgc.proyectojpa.Ventas;
import com.jgc.proyectojpa.exceptions.IllegalOrphanException;
import com.jgc.proyectojpa.exceptions.NonexistentEntityException;
import com.jgc.proyectojpa.exceptions.PreexistingEntityException;
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
public class ClientesJpaController implements Serializable {

  public ClientesJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Clientes clientes) throws PreexistingEntityException, Exception {
    if (clientes.getVentasCollection() == null) {
      clientes.setVentasCollection(new ArrayList<Ventas>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Collection<Ventas> attachedVentasCollection = new ArrayList<Ventas>();
      for (Ventas ventasCollectionVentasToAttach : clientes.getVentasCollection()) {
        ventasCollectionVentasToAttach = em.getReference(ventasCollectionVentasToAttach.getClass(), ventasCollectionVentasToAttach.getIdventa());
        attachedVentasCollection.add(ventasCollectionVentasToAttach);
      }
      clientes.setVentasCollection(attachedVentasCollection);
      em.persist(clientes);
      for (Ventas ventasCollectionVentas : clientes.getVentasCollection()) {
        Clientes oldIdclienteOfVentasCollectionVentas = ventasCollectionVentas.getIdcliente();
        ventasCollectionVentas.setIdcliente(clientes);
        ventasCollectionVentas = em.merge(ventasCollectionVentas);
        if (oldIdclienteOfVentasCollectionVentas != null) {
          oldIdclienteOfVentasCollectionVentas.getVentasCollection().remove(ventasCollectionVentas);
          oldIdclienteOfVentasCollectionVentas = em.merge(oldIdclienteOfVentasCollectionVentas);
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findClientes(clientes.getId()) != null) {
        throw new PreexistingEntityException("Clientes " + clientes + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Clientes clientes) throws IllegalOrphanException, NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Clientes persistentClientes = em.find(Clientes.class, clientes.getId());
      Collection<Ventas> ventasCollectionOld = persistentClientes.getVentasCollection();
      Collection<Ventas> ventasCollectionNew = clientes.getVentasCollection();
      List<String> illegalOrphanMessages = null;
      for (Ventas ventasCollectionOldVentas : ventasCollectionOld) {
        if (!ventasCollectionNew.contains(ventasCollectionOldVentas)) {
          if (illegalOrphanMessages == null) {
            illegalOrphanMessages = new ArrayList<String>();
          }
          illegalOrphanMessages.add("You must retain Ventas " + ventasCollectionOldVentas + " since its idcliente field is not nullable.");
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
      clientes.setVentasCollection(ventasCollectionNew);
      clientes = em.merge(clientes);
      for (Ventas ventasCollectionNewVentas : ventasCollectionNew) {
        if (!ventasCollectionOld.contains(ventasCollectionNewVentas)) {
          Clientes oldIdclienteOfVentasCollectionNewVentas = ventasCollectionNewVentas.getIdcliente();
          ventasCollectionNewVentas.setIdcliente(clientes);
          ventasCollectionNewVentas = em.merge(ventasCollectionNewVentas);
          if (oldIdclienteOfVentasCollectionNewVentas != null && !oldIdclienteOfVentasCollectionNewVentas.equals(clientes)) {
            oldIdclienteOfVentasCollectionNewVentas.getVentasCollection().remove(ventasCollectionNewVentas);
            oldIdclienteOfVentasCollectionNewVentas = em.merge(oldIdclienteOfVentasCollectionNewVentas);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Short id = clientes.getId();
        if (findClientes(id) == null) {
          throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.");
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
      Clientes clientes;
      try {
        clientes = em.getReference(Clientes.class, id);
        clientes.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The clientes with id " + id + " no longer exists.", enfe);
      }
      List<String> illegalOrphanMessages = null;
      Collection<Ventas> ventasCollectionOrphanCheck = clientes.getVentasCollection();
      for (Ventas ventasCollectionOrphanCheckVentas : ventasCollectionOrphanCheck) {
        if (illegalOrphanMessages == null) {
          illegalOrphanMessages = new ArrayList<String>();
        }
        illegalOrphanMessages.add("This Clientes (" + clientes + ") cannot be destroyed since the Ventas " + ventasCollectionOrphanCheckVentas + " in its ventasCollection field has a non-nullable idcliente field.");
      }
      if (illegalOrphanMessages != null) {
        throw new IllegalOrphanException(illegalOrphanMessages);
      }
      em.remove(clientes);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Clientes> findClientesEntities() {
    return findClientesEntities(true, -1, -1);
  }

  public List<Clientes> findClientesEntities(int maxResults, int firstResult) {
    return findClientesEntities(false, maxResults, firstResult);
  }

  private List<Clientes> findClientesEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Clientes.class));
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

  public Clientes findClientes(Short id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Clientes.class, id);
    } finally {
      em.close();
    }
  }

  public int getClientesCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Clientes> rt = cq.from(Clientes.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
