package se.iths.vimton;


import io.github.cdimascio.dotenv.Dotenv;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

import static se.iths.vimton.Language.SWEDISH;

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

//        StudyLevel diploma = new StudyLevel("Diploma");
//        StudyLevel certificate = new StudyLevel("Certificate");
//
//
//        ProgramType type1 = new ProgramType(400, true, diploma);
//        ProgramType type2 = new ProgramType(300, true, certificate);
//
//        Program program1 = new Program("Javautvecklare", 20, type1);
//        Program program2 = new Program("Mjuvaratestare", 16, type2);
//
//        Student student1 = new Student("Toni", "Karunaratne", "1979-05-05","toni.is.amazoids@hot.com", "2021-08-21", program1);
//        Student student2 = new Student("Vimbayi", "Mandaza", "1987-04-15", "vimbayi@chips.com", "2020-05-19", program2);
//        Student student3 = new Student("Patrik", "Andersson", "1982-01-17", "dadjokes@homie.com", "2011-08-21", program1);
//        Student student4 = new Student("Cheyenne", "Brown", "2001-02-03", "c.b.dwarf@cool.se", "2021-08-21", program2);
//        Student student5 = new Student("Charlie", "Bonner", "2002-04-05", "m.b.jr@jr.jr", "2022-01-01", program1);
//
//
//        Teacher teacher1 = new Teacher("Eddie", "Neumann", "19990201-5118", "0777-777777","eddie.the.teach@iths.se");
//        Teacher teacher2 = new Teacher("Martin", "Svensson", "19820301-4319","0732-222222", "martin.svensson@iths.se");
//
//        Language swedish = new Language("Swedish");
//        Language english = new Language("English");
//
//        Course course1 = new Course("Databases", "MySQL, JDBC & JPA", 30, swedish, program1);
//        Course course2 = new Course("Java Programming", "Introduction to Java programming", 60, swedish, program1);


    }


}
