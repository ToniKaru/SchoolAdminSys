package se.iths.vimton;

import se.iths.vimton.dao.StudyLevelDao;
import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.*;
import se.iths.vimton.impl.StudyLevelDaoImpl;
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

        //testing entities
        StudyLevel diploma = new StudyLevel("Diploma");
        StudyLevel certificate = new StudyLevel("Certificate");

        ProgramType type1 = new ProgramType(400, true, diploma);
        ProgramType type2 = new ProgramType(300, true, certificate);

        Program javaDeveloper = new Program("Javautvecklare", 22, type1);
        Program softwareTester = new Program("Mjukvarutestare", 17, type2);

        Student student1 = new Student("Toni", "Karunaratne", "1979-05-05","toni.is.amazoids@hot.com", "2021-08-21", javaDeveloper);
        Student student2 = new Student("Vimbayi", "Mandaza", "1987-04-15", "vimbayi@chips.com", "2020-05-19", softwareTester);
        Student student3 = new Student("Patrik", "Andersson", "1982-01-17", "dadjokes@homie.com", "2011-08-21", javaDeveloper);
        Student student4 = new Student("Cheyenne", "Brown", "2001-02-03", "c.b.dwarf@cool.se", "2021-08-21", softwareTester);
        Student student5 = new Student("Charlie", "Bonner", "2002-04-05", "m.b.jr@jr.jr", "2022-01-01", javaDeveloper);


        Teacher teacher1 = new Teacher("Eddie", "Neumann", "19990201-5118", "0777-777777","eddie.the.teach@iths.se");
        Teacher teacher2 = new Teacher("Martin", "Svensson", "19820301-4319","0732-222222", "martin.svensson@iths.se");

        Language swedish = new Language("Swedish");
        Language english = new Language("English");

        Course databases = new Course("Databases", "MySQL, JDBC & JPA", 30, swedish);
        Course javaProgramming = new Course("Java Programming", "Introduction to Java programming", 60, swedish);

        //requires CourseDaoImpl to add courses to database first
//        databases.addTeacher(teacher1);
//        databases.addTeacher(teacher2);
//        javaDeveloper.addCourse(databases);


        //testing implementations
        TeacherDao teacherDao = new TeacherDaoImpl(emf);
//        teacherDao.create(teacher1);
//
//        teacher1.setLastName("Karlsson");
//        teacherDao.update(teacher1);
//
//        Teacher dataBaseTeacher = teacherDao.getById(1);
//        System.out.println(dataBaseTeacher);
//
//        List<Teacher> ed = teacherDao.getByName("ed");
//        printMany(ed, "Teachers called 'ed'");
//
//        teacherDao.delete(teacher1);
//
//        List<Teacher> allTeachers = teacherDao.getAll();
//        printMany(allTeachers, "All teachers:");

        StudyLevelDao studyLevelDao = new StudyLevelDaoImpl(emf);
        studyLevelDao.create(diploma);
        studyLevelDao.create(certificate);
//
//        certificate.setName("certificated");
//        studyLevelDao.update(certificate);

        List<StudyLevel> studyLevels = studyLevelDao.getAll();
        printMany(studyLevels, "Study levels:");

//        StudyLevel cert = studyLevelDao.getById(1);
//        System.out.println("\n1. " + cert);
//
//        List<StudyLevel> withE = studyLevelDao.getByName("e");
//        printMany(withE, "Levels with 'e'");

//        studyLevelDao.delete(diploma);
        studyLevels = studyLevelDao.getAll();
        printMany(studyLevels, "Study levels:");

        //todo: test this when StudentDaoImpl created
//        Map<String, Long> studentsPerStudyLevel = studyLevelDao.getStudentsPerStudyLevel();
//        System.out.println("StudyLevel \tNo. of students");
//        studentsPerStudyLevel.forEach((a, b) -> {
//            System.out.println(a + "\t\t" + b);
//        });
    }

    private static <T> void printMany(List<T> items, String heading) {
        System.out.println("\n" + heading);
        if (items.isEmpty())
            System.out.println("No items found");
        else
            items.forEach(System.out::println);
    }
}