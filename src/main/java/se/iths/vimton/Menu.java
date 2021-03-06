package se.iths.vimton;

import se.iths.vimton.menus.*;
import se.iths.vimton.utils.DefaultData;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Locale;
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
            case 1 -> programOptions();
            case 2 -> courseOptions();
            case 3 -> studentOptions();
            case 4 -> teacherOptions();
            case 5 -> statistics();
            case 6 -> defaultData();
            default -> System.out.println("invalid choice");
        }
    }

    private void defaultData() {
        DefaultData defaultData = new DefaultData(emf);
        defaultData.createData();
        System.out.println("Default data added.");
    }

    private void statistics() {
        StatisticsMenu statisticsMenu = new StatisticsMenu(emf);
        statisticsMenu.run();
    }

    private void teacherOptions() {
        TeacherMenu teacherMenu = new TeacherMenu(emf);
        teacherMenu.run();
    }

    private void studentOptions() {
        StudentMenu studentMenu = new StudentMenu(emf);
        studentMenu.run();
    }

    private void courseOptions() {
        CourseMenu courseMenu = new CourseMenu(emf);
        courseMenu.run();
    }

    private void programOptions() {
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
                6. Add Default Data
                0. Exit""");
    }

    public static int getChoice(){
        int choice;
        while (true){
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from the menu above.");
            }
        }
        return choice;
    }

    public static void cancel() {
        System.out.println("Exiting main menu");
    }

    private void quit(){
        System.out.println("quitting...");
        System.exit(0);
    }

    public static <T> void printMany(List<T> items, String heading) {
        System.out.println("\n" + heading + ":");
        if (items.isEmpty())
            System.out.println("No items found");
        else
            items.forEach(System.out::println);
    }

    public static int getUserInput(String property, int min, int max) {
        int selection;
        while(true) {
            System.out.println("Enter " + property + " or '0' to cancel:");
            try {
                selection = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (selection == 0)
                break;
            else if(selection < min || selection > max) {
                System.out.println("Please enter valid " + property);
                continue;
            }
            break;
        }
        return selection;
    }

    public static boolean getUserInput(String object, String property) {
        boolean selection;
        System.out.println("Enter 'T' if " + object + "'s " + property + " " +
                "is true or 'F' for false:");
        while(true) {
            String input = (scanner.nextLine().trim().toLowerCase(Locale.ROOT));

            if ("t".equalsIgnoreCase(input)) {
                selection = true;
                break;
            } else if ("f".equalsIgnoreCase(input)) {
                selection = false;
                break;
            }
            System.out.println("Please enter T or F to indicate " + property + " status.");
        }
        return selection;
    }


    public static boolean propertyIsUpdated(String property) {
        return !property.isEmpty() && !property.equalsIgnoreCase("x");
    }

    public static String getNewDetails(String object, String property) {
        String input;
        System.out.println("Enter " + property + " for the new " + object + ":");
        while(true){
            input = scanner.nextLine();
            if(!input.trim().isEmpty())
                break;
            System.out.println("Please enter a " +  property + " for the new " + object + ".");
        }
        return input;
    }

    public static String getUpdateDetails(String object, String property) {
        System.out.println("Enter " + object + "'s " + property + " or 'x' to skip:");
        return scanner.nextLine().trim();
    }

}
