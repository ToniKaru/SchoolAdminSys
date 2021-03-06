package se.iths.vimton.impl;

import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Program;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProgramDaoImpl implements ProgramDao {
    EntityManager em;

    public ProgramDaoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void create(Program program) {
        if(exists(program))  {
            System.out.println("Program: " + program.getName() + " already exists.");
            return;
        }
        em.getTransaction().begin();
        em.persist(program);
        em.getTransaction().commit();
    }

    private boolean exists(Program program) {
        return em.createQuery("SELECT p FROM Program p WHERE p.name = :name", Program.class)
                .setParameter("name", program.getName())
                .getResultList()
                .contains(program);
    }

    @Override
    public void update(Program program) {
        if(!exists(program)) {
            System.out.println("Could not update program. Program: " + program.getName() + " does not exist.");
            return;
        }
        em.getTransaction().begin();
        em.merge(program);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Program program) {
        if(!exists(program))
            return;
        em.getTransaction().begin();
        em.remove(program);
        em.getTransaction().commit();
    }

    @Override
    public void addCourse(Program program, Course course) {
        program.addCourse(course);
        em.getTransaction().begin();
        em.merge(program);
        em.getTransaction().commit();
    }


    @Override
    public Optional<Program> getById(int id) {
        return em.createQuery("SELECT p FROM Program p WHERE p.id = :id", Program.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Program> getByName(String name) {
        return em.createQuery("SELECT p FROM Program p " +
                        "WHERE p.name LIKE :name", Program.class)
                .setParameter("name", "%"+name+"%")
                .getResultList();
    }

    @Override
    public List<Program> getByPace(int pace) {
        return em.createQuery("SELECT p FROM Program p WHERE p.pace = :pace", Program.class)
                .setParameter("pace", pace)
                .getResultList();
    }

    @Override
    public List<Program> getByCourse(Course course) {
        return em.createQuery("SELECT p FROM Program p JOIN p.courses c " +
                        "WHERE p.courses = :course", Program.class)
                .setParameter("course", course)
                .getResultList();
    }

    @Override
    public List<Program> getAll() {
        return em.createQuery("SELECT p FROM Program p", Program.class).getResultList();
    }

    @Override
    public Map<String, Long> getStudentsPerProgram() {
        Map<String, Long> map = new HashMap<>();

        List<Program> programs = em.createQuery("SELECT p FROM Program p", Program.class).getResultList();

        programs.forEach(program -> {
            Long count = numberOfStudentsPerProgram(program).orElse(0L);
            map.put(program.getName(), count);
        });

        return map;
    }

    private Optional<Long> numberOfStudentsPerProgram(Program program) {
        return em.createQuery("SELECT COUNT(*) FROM Student s WHERE s.program.id = :id", Long.class)
                .setParameter("id", program.getId())
                .getResultStream()
                .findFirst();
    }

}
