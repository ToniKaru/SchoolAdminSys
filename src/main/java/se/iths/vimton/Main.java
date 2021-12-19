package se.iths.vimton;


import io.github.cdimascio.dotenv.Dotenv;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
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

//        TestImpl test = new TestImpl(emf);
//        test.run();

        Menu menu = new Menu(emf);
        menu.run();

    }

    public static <T> void printMany(List<T> items, String heading) {
        System.out.println("\n" + heading);
        if (items.isEmpty())
            System.out.println("No items found");
        else
            items.forEach(System.out::println);
    }


}


