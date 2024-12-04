/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mmc.proyectojpav2;

import com.mmc.proyectojpav2.exceptions.NonexistentEntityException;
import com.mmc.proyectojpav2.exceptions.PreexistingEntityException;
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
 * @author MMC by Mateo Molina Campos
 * @version 1.0
 * Created on 29 nov 2024
 *
 */
public class EmpleadosJpaController implements Serializable {
    
        private EntityManagerFactory emf = null;

    public EmpleadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos deptNo = empleados.getDeptNo();
            if (deptNo != null) {
                deptNo = em.getReference(deptNo.getClass(), deptNo.getDeptNo());
                empleados.setDeptNo(deptNo);
            }
            em.persist(empleados);
            if (deptNo != null) {
                deptNo.getEmpleadosCollection().add(empleados);
                deptNo = em.merge(deptNo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleados(empleados.getEmpNo()) != null) {
                throw new PreexistingEntityException("Empleados " + empleados + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getEmpNo());
            Departamentos deptNoOld = persistentEmpleados.getDeptNo();
            Departamentos deptNoNew = empleados.getDeptNo();
            if (deptNoNew != null) {
                deptNoNew = em.getReference(deptNoNew.getClass(), deptNoNew.getDeptNo());
                empleados.setDeptNo(deptNoNew);
            }
            empleados = em.merge(empleados);
            if (deptNoOld != null && !deptNoOld.equals(deptNoNew)) {
                deptNoOld.getEmpleadosCollection().remove(empleados);
                deptNoOld = em.merge(deptNoOld);
            }
            if (deptNoNew != null && !deptNoNew.equals(deptNoOld)) {
                deptNoNew.getEmpleadosCollection().add(empleados);
                deptNoNew = em.merge(deptNoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = empleados.getEmpNo();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.");
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
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getEmpNo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            Departamentos deptNo = empleados.getDeptNo();
            if (deptNo != null) {
                deptNo.getEmpleadosCollection().remove(empleados);
                deptNo = em.merge(deptNo);
            }
            em.remove(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
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

    public Empleados findEmpleados(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
