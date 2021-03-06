package se.iths.vimton.impl;

import se.iths.vimton.dao.ProgTypeDao;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.ProgramType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProgTypeDaoImpl implements ProgTypeDao {
    EntityManager em;

    public ProgTypeDaoImpl(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    @Override
    public void create(ProgramType programType) {
        if (exists(programType)) {
            System.out.println("ProgramType: " + programType.getName() + " already exists.");
            return;
        }
        em.getTransaction().begin();
        em.persist(programType);
        em.getTransaction().commit();
    }

    private boolean exists(ProgramType programType) {
        return em.createQuery("SELECT p FROM ProgramType p WHERE p.name = :name", ProgramType.class)
                .setParameter("name", programType.getName())
                .getResultList()
                .contains(programType);
    }

    @Override
    public void update(ProgramType programType) {
        if(!exists(programType)) {
            System.out.println("ProgramType: " + programType.getName() + " does not exist.");
            return;
        }
        em.getTransaction().begin();
        em.merge(programType);
        em.getTransaction().commit();
    }

    @Override
    public void delete(ProgramType programType) {
        if(!exists(programType))
            return;
        em.getTransaction().begin();
        em.remove(programType);
        em.getTransaction().commit();
    }

    @Override
    public Optional<ProgramType> getById(int id) {
        return em.createQuery("SELECT p FROM ProgramType p WHERE p.id = :id", ProgramType.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<ProgramType> getByName(String name) {
        return em.createQuery("SELECT p FROM ProgramType p WHERE p.name LIKE :name", ProgramType.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<ProgramType> getByAccreditation(boolean accredited) {
        return em.createQuery("SELECT p FROM ProgramType p " +
                        "WHERE p.accredited = :accredited", ProgramType.class)
                .setParameter("accredited", accredited)
                .getResultList();
    }

    @Override
    public List<ProgramType> getAll() {
        return em.createQuery("SELECT p FROM ProgramType p ", ProgramType.class)
                .getResultList();
    }

}
