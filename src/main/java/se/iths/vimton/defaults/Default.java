package se.iths.vimton.defaults;

import se.iths.vimton.dao.LanguageDao;
import se.iths.vimton.entities.*;
import se.iths.vimton.impl.LanguageDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Default {

    EntityManagerFactory emf;
    EntityManager em;
    LanguageDao languageDao;

//    List<Language> languages;
//    List<ProgramType> programTypes;
//    List<Course> courses;
//    List<Program> programs;
//    List<Teacher> teachers;
//    List<Student> students;

    public Default(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = emf.createEntityManager();

        this.languageDao = new LanguageDaoImpl(emf);

//        this.languages = languages();
//        this.programTypes = programTypes();
//        this.courses = courses();
//        this.programs = programs();
//        this.teachers = teachers();
//        this.students = students();
    }

    private void createDefaults() {
        createLanguages();

    }

    private void createLanguages() {
        languageDao.create(new Language("Swedish"));
        languageDao.create(new Language("English"));
    }


    private List<ProgramType> programTypes() {
        return List.of(
                new ProgramType("diploma", 400, true),
                new ProgramType("certificate", 300, true)
        );
    }

    private List<Course> courses() {
        return List.of(
//                new Course("Databases", "MySQL, JDBC & JPA", 30, null),
//                new Course("Java Programming", "Introduction to Java programming", 60, null)
        );
    }

    private List<Program> programs() {
        return List.of(

        );
    }

    private List<Teacher> teachers() {
        return List.of(

        );
    }

    private List<Student> students() {
        return List.of(

        );
    }
}

