package se.iths.vimton;

import se.iths.vimton.dao.ProgTypeDao;
import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.*;
import se.iths.vimton.impl.ProgTypeDaoImpl;
import se.iths.vimton.impl.ProgramDaoImpl;
import se.iths.vimton.impl.TeacherDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

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

        Program javaDeveloper = new Program("Javautvecklare", 22, type1);
        Program softwareTester = new Program("Mjukvarutestare", 17, type2);

        Student student1 = new Student("Toni", "Karunaratne", "19790505-0000",
                "toni.is.amazoids@hot.com", "2021-08-21", javaDeveloper);
        Student student2 = new Student("Vimbayi", "Mandaza", "19870415-1111",
                "vimbayi@chips.com", "2020-05-19", softwareTester);
        Student student3 = new Student("Patrik", "Andersson", "19820117-2222",
                "dadjokes@homie.com", "2011-08-21", javaDeveloper);
        Student student4 = new Student("Cheyenne", "Brown", "20010203-3333",
                "c.b.dwarf@cool.se", "2021-08-21", softwareTester);
        Student student5 = new Student("Charlie", "Bonner",
                "20020405-4444", "m.b.jr@jr.jr", "2022-01-01", javaDeveloper);
        
        Teacher teacher1 = new Teacher("Eddie", "Neumann", "19990201-5118", "0777-777777","eddie.the.teach@iths.se");
        Teacher teacher2 = new Teacher("Martin", "Svensson", "19820301-4319","0732-222222", "martin.svensson@iths.se");

        Language swedish = new Language("Swedish");
        Language english = new Language("English");

        Course databases = new Course("Databases", "MySQL, JDBC & JPA", 30, swedish);
        Course javaProgramming = new Course("Java Programming", "Introduction to Java programming", 60, swedish);

        System.out.println("test data added.");

        //requires CourseDaoImpl to add courses to database first
//        databases.addTeacher(teacher1);
//        databases.addTeacher(teacher2);
//        javaDeveloper.addCourse(databases);


        //testing implementations
        TeacherDao teacherDao = new TeacherDaoImpl(emf);
//        try {
//            teacherDao.create(teacher1);
//            teacherDao.create(teacher2);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        }

//        List<Teacher> allTeachers = teacherDao.getAll();
//        printMany(allTeachers, "All teachers before deletion:");

        ProgTypeDao progTypeDao = new ProgTypeDaoImpl(emf);
        try {
            progTypeDao.create(type1);
            progTypeDao.create(type2);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        printMany(progTypeDao.getAll(), "All program types in testimpl");


        ProgramDao programDao = new ProgramDaoImpl(emf);
        try {

            programDao.create(softwareTester);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        printMany(programDao.getAll(), "All programs in testimpl");

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

//        allTeachers = teacherDao.getAll();
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
