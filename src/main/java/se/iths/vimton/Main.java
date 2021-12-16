package se.iths.vimton;


import io.github.cdimascio.dotenv.Dotenv;
import se.iths.vimton.entities.*;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;
import static se.iths.vimton.Guard.Against.*;



public class Main {

    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

    private static final Map<String, Object> configOverrides = Map.of(
            "javax.persistence.jdbc.user", dotenv.get("USER_NAME"),
            "javax.persistence.jdbc.password", dotenv.get("PASSWORD")
    );

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("Lab5", configOverrides);

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

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

        databases.addTeacher(teacher1);
        databases.addTeacher(teacher2);

        javaDeveloper.addCourse(databases);


    }


}


