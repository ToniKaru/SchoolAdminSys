package se.iths.vimton.impl;

import se.iths.vimton.dao.CourseDao;
import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Student;
import se.iths.vimton.entities.StudyLevel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    private EntityManager em;

    public CourseDaoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void create(Course course) {
        //todo: !ifExists(course) return;
        em.getTransaction().begin();
        em.persist(course);
        em.getTransaction().commit();
    }

    @Override
    public void update(Course course) {
        em.getTransaction().begin();
        em.merge(course);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Course course) {
        //todo: !ifExists(course) throw error
        em.getTransaction().begin();
        em.remove(course);
        em.getTransaction().commit();
    }

    @Override
    public Course getById(int id) {
        //todo: return optional in case id not found
        return em.find(Course.class, id);
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
    public List<Student> getStudents(Course course) {
        //note: added this as a method in Course class &
        // updated ER diagram with many to many table
        // Remove this method?
        return null;
    }

    @Override
    public List<Course> getAll() {
        return em.createQuery("SELECT c FROM Course c", Course.class)
                .getResultList();
    }
}
