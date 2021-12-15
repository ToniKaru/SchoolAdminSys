package se.iths.vimton;


import io.github.cdimascio.dotenv.Dotenv;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

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


        ProgramType type1 = new ProgramType(400, true, DIPLOMA);
        ProgramType type2 = new ProgramType(300, true, CERTIFICATE);

        Program program1 = new Program("Javautvecklare", 20, type1);
        Program program2 = new Program("Mjuvaratestare", 16, type2);

        Student student1 = new Student("Toni", "Karunaratne", "1979-05-05","toni.is.amazoids@hot.com", "2021-08-21", program1);
        Student student2 = new Student("Vimbayi", "Mandaza", "1987-04-15", "vimbayi@chips.com", "2020-05-19", program2);


        Teacher teacher1 = new Teacher("Eddie", "Neumann", "19990201-5118", "0777-777777","eddie.the.teach@iths.se");
        Teacher teacher2 = new Teacher("Martin", "Svensson", "19820301-4319","0732-222222", "martin.svensson@iths.se");

        Language swedish = new Language("Swedish");

        Course course1 = new Course("Databases", "MySQL, JDBC & JPA", 30, swedish, program1);
        Course course2 = new Course("Java Programming", "Introduction to Java programming", 60, swedish, program1);


    }


}
