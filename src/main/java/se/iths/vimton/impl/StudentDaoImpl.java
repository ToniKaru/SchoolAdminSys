package se.iths.vimton.impl;

import se.iths.vimton.dao.StudentDao;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {

    EntityManager em;

    public StudentDaoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void create(Student student) {
        if (studentExists(student)) {
            System.out.println(student.getFirstName() + " " + student.getLastName() + " already exists.");
            return;
        }
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
    }

    private boolean studentExists(Student student) {
        return em.createQuery("SELECT s FROM Student s WHERE s.ssn = :ssn", Student.class)
                .setParameter("ssn", student.getSsn())
                .getResultList()
                .contains(student);
    }

    @Override
    public void update(Student student) {
        if (!studentExists(student)) {
            System.out.println("Updated student does not exist.");
            return;
        }
        em.getTransaction().begin();
        em.merge(student);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Student student) {
        if (!studentExists(student))
            return;
        em.getTransaction().begin();
        em.remove(student);
        em.getTransaction().commit();
    }

    @Override
    public Optional<Student> getById(int id) {
        return em.createQuery("SELECT s FROM Student s WHERE s.id = :id", Student.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<Student> getBySsn(String ssn) {
        if (isValidSsn(ssn)) {
            return em.createQuery("SELECT s FROM Student s where s.ssn = :ssn", Student.class)
                    .setParameter("ssn", ssn)
                    .getResultStream()
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }

    private boolean isValidSsn(String ssn) {
        return ssn.length() == 13 && ssn.matches("\\d{8}-\\d{4}");
    }

    @Override
    public List<Student> getByName(String name) {
        return em.createQuery("SELECT s FROM Student s WHERE s.firstName LIKE :name OR s.lastName LIKE :name",
                        Student.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<Student> getByProgram(int programId) {
        return em.createQuery("SELECT s FROM Student s WHERE s.program.id = :programId", Student.class)
                .setParameter("programId", programId)
                .getResultList();
    }

    @Override
    public List<Student> getByProgram(Program program) {
        return em.createQuery("SELECT s FROM Student s WHERE s.program = :program", Student.class)
                .setParameter("program", program)
                .getResultList();
    }

    @Override
    public List<Student> getAll() {
        return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
    }

    @Override
    public Optional<Long> studentsByProgram(Program program) {
        return em.createQuery("SELECT COUNT(*) FROM Student s WHERE s.program.id = :id", Long.class)
                .setParameter("id", program.getId())
                .getResultStream()
                .findFirst();
    }
}
