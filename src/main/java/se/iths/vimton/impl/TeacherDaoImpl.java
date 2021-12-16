package se.iths.vimton.impl;

import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {

    EntityManager em;

    public TeacherDaoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void create(Teacher teacher) {
        em.getTransaction().begin();
        em.persist(teacher);
        em.getTransaction().commit();
    }

    @Override
    public void update(Teacher teacher) {
        em.getTransaction().begin();
        em.merge(teacher);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Teacher teacher) {
        em.getTransaction().begin();
        em.remove(teacher);
        em.getTransaction().commit();
    }

    @Override
    public Teacher getById(int id) {
        return em.createQuery("SELECT t FROM Teacher t WHERE t.id = :id", Teacher.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Teacher> getByName(String name) {
        return em.createQuery("SELECT t FROM Teacher t WHERE t.firstName LIKE :name AND t.lastName LIKE :name", Teacher.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<Teacher> getAll() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }
}
