package se.iths.vimton.impl;

import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class TeacherDaoImpl implements TeacherDao {

    EntityManager em;

    public TeacherDaoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void create(Teacher teacher) {
        if (exists(teacher))
            throw new IllegalArgumentException(teacher.getFirstName() + " " + teacher.getLastName() + " already exists.");
        em.getTransaction().begin();
        em.persist(teacher);
        em.getTransaction().commit();
    }

    private boolean exists(Teacher teacher) {
        return em.createQuery("SELECT t FROM Teacher t WHERE t.firstName = :firstName AND t.lastName = :lastName",
                        Teacher.class)
                .setParameter("firstName", teacher.getFirstName())
                .setParameter("lastName", teacher.getLastName())
                .getResultList()
                .contains(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        if (!exists(teacher))
            throw new IllegalArgumentException("Updated teacher does not exists.");;
        em.getTransaction().begin();
        em.merge(teacher);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Teacher teacher) {
        if (!exists(teacher))
            return;
        em.getTransaction().begin();
        em.remove(teacher);
        em.getTransaction().commit();
    }

    @Override
    public Optional<Teacher> getById(int id) {
        return em.createQuery("SELECT t FROM Teacher t WHERE t.id = :id", Teacher.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Teacher> getByName(String name) {
        return em.createQuery("SELECT t FROM Teacher t WHERE t.firstName LIKE :name OR t.lastName LIKE :name",
                        Teacher.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<Teacher> getAll() {
        return em.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
    }
}
