package se.iths.vimton.impl;

import se.iths.vimton.dao.StudyLevelDao;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.StudyLevel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyLevelDaoImpl implements StudyLevelDao {
    EntityManager em;

    public StudyLevelDaoImpl(EntityManagerFactory emf) {
        this.em = emf.createEntityManager();
    }

    @Override
    public void create(StudyLevel studyLevel) {
        //todo: ifExists(studyLevel) return;    -> avoid duplication
        em.getTransaction().begin();
        em.persist(studyLevel);
        em.getTransaction().commit();
    }

    @Override
    public void update(StudyLevel studyLevel) {
        em.getTransaction().begin();
        em.merge(studyLevel);
        em.getTransaction().commit();
    }

    @Override
    public void delete(StudyLevel studyLevel) {
        //todo: !ifExists(studyLevel) return    -> avoid IllegalArgumentException
        em.getTransaction().begin();
        em.remove(studyLevel);
        em.getTransaction().commit();
    }

    //todo: return optional - NoResultException ("no entity found for query)
    @Override
    public StudyLevel getById(int id) {
        return em.createQuery("SELECT s FROM StudyLevel s WHERE s.id = :id", StudyLevel.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<StudyLevel> getByName(String name) {
        return em.createQuery("SELECT s FROM StudyLevel s WHERE s.name LIKE :name", StudyLevel.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    @Override
    public List<StudyLevel> getAll() {
        return em.createQuery("SELECT s FROM StudyLevel s", StudyLevel.class).getResultList();
    }

    @Override
    public Map<String, Long> getStudentsPerProgram() {
        Map<String, Long> map = new HashMap<>();

        List<Program> programs = em.createQuery("SELECT p FROM Program p", Program.class).getResultList();

        programs.forEach(program -> {
            Long count = em.createQuery("SELECT COUNT(*) FROM Student s WHERE s.program.id = :id", Long.class)
                    .setParameter("id", program.getId())
                    .getSingleResult();
            map.put(program.getName(), count);
        });

        return map;
    }

    @Override
    public Map<String, Long> getStudentsPerStudyLevel() {
        Map<String, Long> map = new HashMap<>();

        List<StudyLevel> levels = em.createQuery("SELECT s FROM StudyLevel s", StudyLevel.class).getResultList();

        levels.forEach(level -> {
            Long count = em.createQuery(
                            "SELECT COUNT(*) FROM Student s WHERE s.program.programType.studyLevel.id = :id",
                            Long.class
                    )
                    .setParameter("id", level.getId())
                    .getSingleResult();
            map.put(level.getName(), count);
        });

        return map;
    }
}


