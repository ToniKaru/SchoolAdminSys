package se.iths.vimton.utils;

import se.iths.vimton.dao.*;
import se.iths.vimton.entities.*;
import se.iths.vimton.impl.*;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public class DefaultData {

    LanguageDao languageDao;
    ProgTypeDao progTypeDao;
    CourseDao courseDao;
    ProgramDao programDao;
    TeacherDao teacherDao;
    StudentDao studentDao;

    public DefaultData(EntityManagerFactory emf) {
        this.languageDao = new LanguageDaoImpl(emf);
        this.progTypeDao = new ProgTypeDaoImpl(emf);
        this.courseDao = new CourseDaoImpl(emf);
        this.programDao = new ProgramDaoImpl(emf);
        this.teacherDao = new TeacherDaoImpl(emf);
        this.studentDao = new StudentDaoImpl(emf);
    }

    private void createDefaults() {
        createTeachers();
        createLanguages();
        createProgramTypes();
        createCourses();
        createPrograms();
        createStudents();
    }

//    todo: check that updating course to database automatically updates program, same applies for teacher-course

    private void createTeachers() {
        teacherDao.create(new Teacher("Eddie", "Karlsson", "19990201-5118", "0777-777777","eddie.the.teacher@iths.se"));
        teacherDao.create(new Teacher("Martin", "Svensson", "19820301-4319","0732-222222", "martin.svensson@iths.se"));
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

        addTeachersToCourses();
    }

    private void addTeachersToCourses() {
        Optional<Teacher> martin  = teacherDao.getByName("martin").stream().findFirst();
        Optional<Teacher> eddie  = teacherDao.getByName("eddie").stream().findFirst();

        if(martin.isEmpty() || eddie.isEmpty())
            throw new RuntimeException("Default teachers not found in Default.addTeachersToCourses");

        Optional<Course> databases = courseDao.getByName("Databases").stream().findFirst();
        Optional<Course> javaProgramming = courseDao.getByName("Java Programming").stream().findFirst();

        databases.ifPresentOrElse(course -> {
                course.addTeacher(eddie.get());
                courseDao.update(course);
            },
            () -> { throw new RuntimeException("Default database course not found in Default.addTeachersToCourses"); }
        );

        javaProgramming.ifPresentOrElse(course -> {
                course.addTeacher(martin.get());
                courseDao.update(course);
            },
            () -> { throw new RuntimeException("Default java programming course not found in Default.addTeachersToCourses"); }
        );

    }

    private void createPrograms() {
        addNewPrograms();
        addCoursesToPrograms();
    }

    private void addNewPrograms() {
        Optional<ProgramType> diploma = progTypeDao.getByName("diploma").stream().findFirst();
        Optional<ProgramType> certificate = progTypeDao.getByName("certificate").stream().findFirst();

        diploma.ifPresentOrElse(
                programType -> programDao.create(new Program("Javautvecklare", 22, programType)),
                () -> { throw new RuntimeException("Default program types not found in Default.createPrograms"); }
        );

        certificate.ifPresentOrElse(
                programType -> programDao.create(new Program("Mjukvarutestare", 17, programType)),
                () -> { throw new RuntimeException("Default program types not found in Default.createPrograms"); }
        );
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

    private void createStudents() {
        Optional<Program> javaDeveloper = programDao.getByName("Javautvecklare").stream().findFirst();
        Optional<Program> softwareTester = programDao.getByName("Mjukvarutestare").stream().findFirst();

        javaDeveloper.ifPresentOrElse(
            program -> {
               studentDao.create(new Student("Toni", "Karunaratne", "19790505-0000",
                       "toni.is.amazoids@hot.com", "2021-08-21", program));
               studentDao.create(new Student("Patrik", "Andersson", "19820117-2222",
                       "dadjokes@homie.com", "2011-08-21", program));
               studentDao.create(new Student("Charlie", "Bonner",  "20020405-4444",
                       "m.b.jr@jr.jr", "2022-01-01", program));
            },
            () -> { throw new RuntimeException("Java program not found in Default.students"); }
        );

        softwareTester.ifPresentOrElse(
            program -> {
               studentDao.create(new Student("Vimbayi", "Mandaza", "19870415-1111",
                       "vimbayi@chips.com", "2020-05-19", program));
               studentDao.create(new Student("Cheyenne", "Brown", "20010203-3333",
                       "c.b.dwarf@cool.se", "2021-08-21", program));
            },
            () -> { throw new RuntimeException("Software tester program not found in Default.students"); }
        );

    }

}

