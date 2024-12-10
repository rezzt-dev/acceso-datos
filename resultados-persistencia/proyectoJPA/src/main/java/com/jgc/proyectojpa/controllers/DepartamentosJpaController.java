/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jgc.proyectojpa.controllers;

import com.jgc.proyectojpa.exceptions.NonexistentEntityException;
import com.jgc.proyectojpa.exceptions.PreexistingEntityException;
import com.jgc.proyectojpa.model.Departamentos;
import com.jgc.proyectojpa.model.Empleados;

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
public class DepartamentosJpaController implements Serializable {

  public DepartamentosJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Departamentos departamentos) throws PreexistingEntityException, Exception {
    if (departamentos.getEmpleadosCollection() == null) {
      departamentos.setEmpleadosCollection(new ArrayList<Empleados>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Collection<Empleados> attachedEmpleadosCollection = new ArrayList<Empleados>();
      for (Empleados empleadosCollectionEmpleadosToAttach : departamentos.getEmpleadosCollection()) {
        empleadosCollectionEmpleadosToAttach = em.getReference(empleadosCollectionEmpleadosToAttach.getClass(), empleadosCollectionEmpleadosToAttach.getEmpNo());
        attachedEmpleadosCollection.add(empleadosCollectionEmpleadosToAttach);
      }
      departamentos.setEmpleadosCollection(attachedEmpleadosCollection);
      em.persist(departamentos);
      for (Empleados empleadosCollectionEmpleados : departamentos.getEmpleadosCollection()) {
        Departamentos oldDeptNoOfEmpleadosCollectionEmpleados = empleadosCollectionEmpleados.getDeptNo();
        empleadosCollectionEmpleados.setDeptNo(departamentos);
        empleadosCollectionEmpleados = em.merge(empleadosCollectionEmpleados);
        if (oldDeptNoOfEmpleadosCollectionEmpleados != null) {
          oldDeptNoOfEmpleadosCollectionEmpleados.getEmpleadosCollection().remove(empleadosCollectionEmpleados);
          oldDeptNoOfEmpleadosCollectionEmpleados = em.merge(oldDeptNoOfEmpleadosCollectionEmpleados);
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      if (findDepartamentos(departamentos.getDeptNo()) != null) {
        throw new PreexistingEntityException("Departamentos " + departamentos + " already exists.", ex);
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Departamentos departamentos) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Departamentos persistentDepartamentos = em.find(Departamentos.class, departamentos.getDeptNo());
      Collection<Empleados> empleadosCollectionOld = persistentDepartamentos.getEmpleadosCollection();
      Collection<Empleados> empleadosCollectionNew = departamentos.getEmpleadosCollection();
      Collection<Empleados> attachedEmpleadosCollectionNew = new ArrayList<Empleados>();
      for (Empleados empleadosCollectionNewEmpleadosToAttach : empleadosCollectionNew) {
        empleadosCollectionNewEmpleadosToAttach = em.getReference(empleadosCollectionNewEmpleadosToAttach.getClass(), empleadosCollectionNewEmpleadosToAttach.getEmpNo());
        attachedEmpleadosCollectionNew.add(empleadosCollectionNewEmpleadosToAttach);
      }
      empleadosCollectionNew = attachedEmpleadosCollectionNew;
      departamentos.setEmpleadosCollection(empleadosCollectionNew);
      departamentos = em.merge(departamentos);
      for (Empleados empleadosCollectionOldEmpleados : empleadosCollectionOld) {
        if (!empleadosCollectionNew.contains(empleadosCollectionOldEmpleados)) {
          empleadosCollectionOldEmpleados.setDeptNo(null);
          empleadosCollectionOldEmpleados = em.merge(empleadosCollectionOldEmpleados);
        }
      }
      for (Empleados empleadosCollectionNewEmpleados : empleadosCollectionNew) {
        if (!empleadosCollectionOld.contains(empleadosCollectionNewEmpleados)) {
          Departamentos oldDeptNoOfEmpleadosCollectionNewEmpleados = empleadosCollectionNewEmpleados.getDeptNo();
          empleadosCollectionNewEmpleados.setDeptNo(departamentos);
          empleadosCollectionNewEmpleados = em.merge(empleadosCollectionNewEmpleados);
          if (oldDeptNoOfEmpleadosCollectionNewEmpleados != null && !oldDeptNoOfEmpleadosCollectionNewEmpleados.equals(departamentos)) {
            oldDeptNoOfEmpleadosCollectionNewEmpleados.getEmpleadosCollection().remove(empleadosCollectionNewEmpleados);
            oldDeptNoOfEmpleadosCollectionNewEmpleados = em.merge(oldDeptNoOfEmpleadosCollectionNewEmpleados);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Short id = departamentos.getDeptNo();
        if (findDepartamentos(id) == null) {
          throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Short id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Departamentos departamentos;
      try {
        departamentos = em.getReference(Departamentos.class, id);
        departamentos.getDeptNo();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.", enfe);
      }
      Collection<Empleados> empleadosCollection = departamentos.getEmpleadosCollection();
      for (Empleados empleadosCollectionEmpleados : empleadosCollection) {
        empleadosCollectionEmpleados.setDeptNo(null);
        empleadosCollectionEmpleados = em.merge(empleadosCollectionEmpleados);
      }
      em.remove(departamentos);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Departamentos> findDepartamentosEntities() {
    return findDepartamentosEntities(true, -1, -1);
  }

  public List<Departamentos> findDepartamentosEntities(int maxResults, int firstResult) {
    return findDepartamentosEntities(false, maxResults, firstResult);
  }

  private List<Departamentos> findDepartamentosEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Departamentos.class));
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

  public Departamentos findDepartamentos(Short id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Departamentos.class, id);
    } finally {
      em.close();
    }
  }

  public int getDepartamentosCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Departamentos> rt = cq.from(Departamentos.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
