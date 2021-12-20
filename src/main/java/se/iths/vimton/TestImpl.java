package se.iths.vimton;

import se.iths.vimton.dao.*;
import se.iths.vimton.entities.*;
import se.iths.vimton.impl.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class TestImpl {
    EntityManagerFactory emf;
    EntityManager em;

    public TestImpl(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = emf.createEntityManager();
    }

    public void run() {

        System.out.println("Adding test data...");

        //testing entities
        ProgramType type1 = new ProgramType("diploma", 400, true);
        ProgramType type2 = new ProgramType("certificate", 300, true);


        Teacher teacher1 = new Teacher("Eddie", "Neumann", "19990201-5118", "0777-777777","eddie.the.teach@iths.se");
        Teacher teacher2 = new Teacher("Martin", "Svensson", "19820301-4319","0732-222222", "martin.svensson@iths.se");

        Language language1 = new Language("Swedish");
        Language language2 = new Language("English");

        LanguageDao languageDao = new LanguageDaoImpl(emf);
        languageDao.create(language1);
        Language swedish = languageDao.getByName("swedish").stream().findFirst().get();

        Course databases = new Course("Databases", "MySQL, JDBC & JPA", 30, swedish);
        Course javaProgramming = new Course("Java Programming", "Introduction to Java programming", 60, swedish);

        CourseDao courseDao = new CourseDaoImpl(emf);
//        courseDao.create(javaProgramming);
//        courseDao.create(databases);

        //testing implementations
//        TeacherDao teacherDao = new TeacherDaoImpl(emf);
//        try {
//            teacherDao.create(teacher1);
//            teacherDao.create(teacher2);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//
//        Optional<Course> db = courseDao.getByName("databases").stream().findFirst();
//        Optional<Course> jp = courseDao.getByName("java").stream().findFirst();
//
//        Optional<Teacher> ed = teacherDao.getByName("ed").stream().findFirst();
//        Optional<Teacher> martin = teacherDao.getBySsn("19820301-4319");
//
//
//        db.ifPresent(course -> course.addTeacher(ed.get()));
//        jp.ifPresent(course -> course.addTeacher(martin.get()));
//
//        courseDao.update(db.get());
//        courseDao.update(jp.get());
//
//        List<Course> courses = courseDao.getAll();
//        List<Teacher> teachers = teacherDao.getAll();
//
//        courses.forEach(course -> {
//            System.out.println();
//            System.out.println(course);
//            System.out.println(course.getTeachers());
//        });
//        teachers.forEach(teacher -> {
//            System.out.println();
//            System.out.println(teacher);
//            System.out.println(teacher.getTeacherCourses());
//        });



//        List<Teacher> allTeachers = teacherDao.getAll();
//        printMany(allTeachers, "All teachers");




        //
        ProgTypeDao progTypeDao = new ProgTypeDaoImpl(emf);
        progTypeDao.create(type1);
        progTypeDao.create(type2);

        ProgramDao programDao = new ProgramDaoImpl(emf);

        //getting program types from JPA / database
//        Optional<ProgramType> diploma = progTypeDao.getByName("diploma").stream().findFirst();
//        Optional<ProgramType> certificate = progTypeDao.getByName("certificate").stream().findFirst();
//
//        diploma.ifPresent(programType -> programDao.create(new Program("Javautvecklare", 22, programType)));
//        certificate.ifPresent(programType -> programDao.create(new Program("Mjukvarutestare", 17, programType)));
//
//        Optional<Program> javaDeveloper = programDao.getByName("java").stream().findFirst();
//        Optional<Program> softwareTester = programDao.getByName("mjukvaru").stream().findFirst();
//
//        //get developer from JPA
//        StudentDao studentDao = new StudentDaoImpl(emf);
//
//        javaDeveloper.ifPresentOrElse(
//                program -> {
//                    studentDao.create(new Student("Toni", "Karunaratne", "19790505-0000",
//                            "toni.is.amazoids@hot.com", "2021-08-21", program));
//                    studentDao.create(new Student("Patrik", "Andersson", "19820117-2222",
//                            "dadjokes@homie.com", "2011-08-21", program));
//                    studentDao.create(new Student("Charlie", "Bonner",  "20020405-4444",
//                            "m.b.jr@jr.jr", "2022-01-01", program));
//                },
//                () -> { throw new RuntimeException("Java program not found in Default.students"); }
//        );
//
//        softwareTester.ifPresentOrElse(
//                program -> {
//                    studentDao.create(new Student("Vimbayi", "Mandaza", "19870415-1111",
//                            "vimbayi@chips.com", "2020-05-19", program));
//                    studentDao.create(new Student("Cheyenne", "Brown", "20010203-3333",
//                            "c.b.dwarf@cool.se", "2021-08-21", program));
//                },
//                () -> { throw new RuntimeException("Software tester program not found in Default.students"); }
//        );


        //get teacher1 object again from teacherDao -> ensures that id is matching
        // therefore avoids duplication & object.equals(object) is true
//        teacher1.setLastName("Karlsson");
//        teacherDao.update(teacher1);

//        Optional<Teacher> dataBaseTeacher = teacherDao.getById(1);
//        dataBaseTeacher.ifPresent(System.out::println);

//        List<Teacher> ed = teacherDao.getByName("ed");
//        printMany(ed, "Teachers called 'ed'");

        // get teacher from teacherDao before deletion / update
//        Optional<Teacher> ed = teacherDao.getById(2);
//        ed.ifPresent(teacherDao::delete);
//
//        Optional<Teacher> martin = teacherDao.getBySsn("19820301-4319");
//        martin.ifPresent(teacherDao::delete);

//        List<Teacher> allTeachers = teacherDao.getAll();
//        printMany(allTeachers, "All teachers after deletion:");




    }

    private static <T> void printMany(List<T> items, String heading) {
        System.out.println("\n" + heading);
        if (items.isEmpty())
            System.out.println("No items found");
        else
            items.forEach(System.out::println);
    }
}
