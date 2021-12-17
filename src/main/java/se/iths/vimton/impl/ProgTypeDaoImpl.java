package se.iths.vimton.impl;

import se.iths.vimton.dao.ProgTypeDao;
import se.iths.vimton.entities.Language;
import se.iths.vimton.entities.ProgramType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProgTypeDaoImpl implements ProgTypeDao {
    EntityManager em;

    public ProgTypeDaoImpl(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public void create(ProgramType programType) {
        em.getTransaction().begin();
        em.persist(programType);
        em.getTransaction().commit();
    }

    @Override
    public void update(ProgramType programType) {
        em.getTransaction().begin();
        em.merge(programType);
        em.getTransaction().commit();
    }

    @Override
    public void delete(ProgramType programType) {
        em.getTransaction().begin();
        em.remove(programType);
        em.getTransaction().commit();
    }

    @Override
    public ProgramType getById(int id) {
        return em.find(ProgramType.class, id);
    }

    @Override
    public List<ProgramType> getByAccreditation(boolean accredited) {
        return em.createQuery("SELECT p FROM ProgramType p " +
                        "WHERE p.accredited = :accredited", ProgramType.class)
                .getResultList();
    }

    @Override
    public List<ProgramType> getAll() {
        return em.createQuery("SELECT p FROM ProgramType p ", ProgramType.class)
                .getResultList();
    }



}
