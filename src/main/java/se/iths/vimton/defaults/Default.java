package se.iths.vimton.defaults;

import se.iths.vimton.dao.*;
import se.iths.vimton.entities.*;
import se.iths.vimton.impl.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class Default {

    LanguageDao languageDao;
    ProgTypeDao progTypeDao;
    CourseDao courseDao;
    ProgramDao programDao;
    TeacherDao teacherDao;
    StudentDao studentDao;

    public Default(EntityManagerFactory emf) {
        this.languageDao = new LanguageDaoImpl(emf);
        this.progTypeDao = new ProgTypeDaoImpl(emf);
        this.courseDao = new CourseDaoImpl(emf);
        this.programDao = new ProgramDaoImpl(emf);
        this.teacherDao = new TeacherDaoImpl(emf);
        this.studentDao = new StudentDaoImpl(emf);
    }

    private void createDefaults() {
        createLanguages();
        createProgramTypes();
        createCourses();
        createPrograms();
    }

    private void createLanguages() {
        languageDao.create(new Language("Swedish"));
        languageDao.create(new Language("English"));
    }


    private void createProgramTypes() {
        progTypeDao.create(new ProgramType("diploma", 400, true));
        progTypeDao.create(new ProgramType("certificate", 300, true));
    }

    private void createCourses() {
        Optional<Language> swedish = languageDao.getByName("Swedish").stream().findFirst();

        swedish.ifPresentOrElse(
            language -> {
                courseDao.create(new Course("Databases", "MySQL, JDBC & JPA", 30, language));
                courseDao.create(new Course("Java Programming", "Introduction to Java programming", 60, language));
            },
            () -> { throw new RuntimeException("Default language not found in Default.createCourses."); }
        );
    }

    private void createPrograms() {
        Optional<ProgramType> diploma = progTypeDao.getByName("diploma").stream().findFirst();
        Optional<ProgramType> certificate = progTypeDao.getByName("certificate").stream().findFirst();

        if(diploma.isEmpty() || certificate.isEmpty())
            throw new RuntimeException("Default program types not found in Default.createPrograms");

        Program javaDeveloper;
        Program softwareTester;

        javaDeveloper = new Program("Javautvecklare", 22, diploma.get());
        softwareTester = new Program("Mjukvarutestare", 17, certificate.get());

        programDao.create(javaDeveloper);
        programDao.create(softwareTester);

        addCoursesToPrograms();
    }

    private void addCoursesToPrograms() {
        Optional<Course> databases = courseDao.getByName("Databases").stream().findFirst();
        Optional<Course> javaProgramming = courseDao.getByName("Java Programming").stream().findFirst();

        if(databases.isEmpty() || javaProgramming.isEmpty())
            throw new RuntimeException("Default courses not found in Default.addCoursesToPrograms");

        Optional<Program> javaDeveloper = programDao.getByName("Javautvecklare").stream().findFirst();
        Optional<Program> softwareTester = programDao.getByName("Mjukvarutestare").stream().findFirst();

        if(javaDeveloper.isEmpty() || softwareTester.isEmpty())
            throw new RuntimeException("Default programs not found in Default.addCoursesToPrograms");

        programDao.addCourse(javaDeveloper.get(), databases.get());
        programDao.addCourse(javaDeveloper.get(), javaProgramming.get());
        programDao.addCourse(softwareTester.get(), javaProgramming.get());
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

