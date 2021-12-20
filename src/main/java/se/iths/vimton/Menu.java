package se.iths.vimton;

import se.iths.vimton.menus.ProgramMenu;

import javax.persistence.EntityManagerFactory;
import java.util.Scanner;

public class Menu {

    public static Scanner scanner = new Scanner(System.in);
    private EntityManagerFactory emf;

    public Menu(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void run() {
        int choice;
        do {
            displayMenu();
            choice = Menu.getChoice();
            executeChoice(choice);
        } while (choice != 0);
    }


    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
            case 1 -> programOptions(emf); //t
            case 2 -> courseOptions();  // v
            case 3 -> studentOptions(); // t
            case 4 -> teacherOptions(); //v
            case 5 -> statistics(); //v
            default -> System.out.println("invalid choice");
        }
    }

    private void statistics() {
    }

    private void teacherOptions() {
    }

    private void studentOptions() {
    }

    private void courseOptions() {

    }

    private void programOptions(EntityManagerFactory emf) {
        ProgramMenu programMenu = new ProgramMenu(emf);
        programMenu.run();
    }

    private void displayMenu() {
        System.out.println("""
                
                -- School Admin System Options --
                1. Programs
                2. Courses
                3. Students
                4. Teachers
                5. Statistics
                0. Exit""");
//                
//                6. Add a course
//                7. Update a course
//                8. Show course details
//                9. List all courses
//                10. Delete a course
//                
//                11. Add a student
//                12. Update a student
//                13. Show student details
//                14. List all students
//                15. Delete a student
//                
//                16. Add a course to a program
//                17. List all courses in a program
//                18. Remove a course from a program
//                19. Add a student to a program
//                20. Remove a student from a program
//                21. List all students in a program
//                                            
//                22. Get the number of students in a program 
//                23. Show number of students per program
//                            
//                
//                0. Exit

    }

    public static int getChoice(){
        int c = Integer.parseInt(scanner.nextLine());
        return c;
    }

    public static void cancel() {
        System.out.println("Exiting main menu");
    }

    private void quit(){
        System.out.println("quitting...");
        System.exit(0);
    }
}
