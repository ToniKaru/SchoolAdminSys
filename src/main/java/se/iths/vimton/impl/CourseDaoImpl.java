package se.iths.vimton.impl;

import se.iths.vimton.dao.CourseDao;
import se.iths.vimton.entities.Course;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {
    private EntityManager em;

    public CourseDaoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void create(Course course) {
        if (exists(course))
            throw new IllegalArgumentException("Course: " + course.getName() + " already exists.");
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
    }

    private boolean exists(Course course) {
        return getByName(course.getName()).contains(course);
    }

    @Override
    public void update(Course course) {
        if(!exists(course))
            throw new IllegalArgumentException("Could not update course. Course: " + course.getName() + " does not exist.");;
        em.getTransaction().begin();
        em.merge(course);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Course course) {
        if(!exists(course))
            return;
        em.getTransaction().begin();
        em.remove(course);
        em.getTransaction().commit();
    }

    @Override
    public Optional<Course> getById(int id) {
        return em.createQuery("SELECT c FROM Course c WHERE c.id = :id", Course.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Course> getByName(String name) {
        return em.createQuery("SELECT c FROM Course c " +
                "WHERE c.name LIKE :name", Course.class)
                .setParameter("name", "%"+name+"%")
                .getResultList();
    }

    @Override
    public List<Course> getByCreditRange(int min, int max) {
        return em.createQuery("SELECT c FROM Course c " +
                        "WHERE c.credits BETWEEN :min AND :max", Course.class)
                .getResultList();
    }


    @Override
    public List<Course> getAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class)
                .getResultList();
    }
}
