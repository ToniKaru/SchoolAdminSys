package se.iths.vimton.menus;

import se.iths.vimton.Main;
import se.iths.vimton.Menu;
import se.iths.vimton.dao.ProgTypeDao;
import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.ProgramType;
import se.iths.vimton.impl.ProgTypeDaoImpl;
import se.iths.vimton.impl.ProgramDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static se.iths.vimton.Menu.scanner;

public class ProgramMenu {

    private ProgramDao programDao;
    private ProgTypeDao progTypeDao;

    public ProgramMenu(EntityManagerFactory emf) {
        ProgramDao programDao = new ProgramDaoImpl(emf);
        ProgTypeDao progTypeDao = new ProgTypeDaoImpl(emf);
    }

    public void run() {
        int choice;
        do {
            printMenu();
            choice = Menu.getChoice();
            executeChoice(choice);
        } while (choice != 0);
    }

    public void printMenu() {
        System.out.println("""
                                
                --- Program Options ---
                1. Add Program
                2. Update Program
                3. Show program details
                4. List all programs
                5. Delete a program
                0. Return to main menu""");
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
            case 1 -> addProgram();
            case 2 -> updateProgram();
            case 3 -> programDetails();
            case 4 -> Main.printMany(programDao.getAll(), "-- All Programs --");
            case 5 -> removeProgram();
            default -> System.out.println("invalid choice");
        }
    }

    private void removeProgram() {
        Program program = getProgramFromUser();
        programDao.delete(program);
    }

    private void programDetails() {
        Program program = getProgramFromUser();
        printOneProgram(program);
    }

    private Program getProgramFromUser() {
        System.out.println("Enter program id: ");
        int id = Integer.parseInt(scanner.nextLine());
        return programDao.getById(id).get();
    }


    private void printOneProgram(Program program) {
        System.out.printf("%-10s %-10s %-10s %-10s %-20s \n",
                "Id", "Name", "Length", "Program Type", "Description");
        System.out.printf("%-10d %-10s %-10d %-10s %-20s \n",
                program.getId(), program.getName(), program.getLength(),
                program.getProgramType().getName(), program.getDescription());
    }

    private void updateProgram() {
        Main.printMany(programDao.getAll(),"-- All Programs --");
        Program program = getProgramFromUser();

        System.out.println("Please enter the updated details.");

        System.out.println("Program name: ");
        String name = scanner.nextLine();
        if (scanner.hasNext())
            program.setName(name);

        System.out.println("Program description: ");
        String description = scanner.nextLine();
        if (scanner.hasNext())
            program.setDescription(description);

        System.out.println("How many months is the program? ");
        int length = Integer.parseInt(scanner.nextLine());
        if (scanner.hasNext())
            program.setLength(length);

        ProgramType programType = getProgramTypeFromUser();
        program.setProgramType(programType);

        programDao.update(program);
    }

    private void addProgram() {
        Program program = getNewProgramFromUser();
        programDao.create(program);
    }

    private Program getNewProgramFromUser() {
        ProgramType programType = getProgramTypeFromUser();

        System.out.println("Program name: ");
        String name = scanner.nextLine();

        System.out.println("Program description: ");
        String description = scanner.nextLine();

        System.out.println("How many months is the program? ");
        int length = Integer.parseInt(scanner.nextLine());

        return new Program(name, description, length, programType);
    }

    private ProgramType getProgramTypeFromUser() {
        ProgramType programType = new ProgramType();
        Main.printMany(progTypeDao.getAll(), "-- All Program Types --");
        System.out.println("Is the program type listed? (y/n)");
        if ('y' == scanner.next().charAt(0)) {
            System.out.println("Enter id of ProgramType");
            int id = Integer.parseInt(scanner.nextLine());
            programType = progTypeDao.getById(id).get();
        }
        else {
            programType = getProgTypeDetails();
        }
        return programType;
    }

    private ProgramType getProgTypeDetails() {
        System.out.println("Program Type name: ");
        String name = scanner.nextLine();

        System.out.println("How many credits is the program type? ");
        int length = Integer.parseInt(scanner.nextLine());

        System.out.println("Is the program type accredited? ");
        boolean accredited = scanner.nextBoolean();

        ProgramType programType = new ProgramType(name, length, accredited);
        progTypeDao.create(programType);

        return programType;
    }




}