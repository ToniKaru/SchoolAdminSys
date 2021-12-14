package se.iths.vimton;


import io.github.cdimascio.dotenv.Dotenv;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;

public class Main {


    private static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("Lab5");

    public static void main( String[] args ) {
        EntityManager em = emf.createEntityManager();






    }


}
