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
import java.util.Locale;

import static se.iths.vimton.Menu.printMany;
import static se.iths.vimton.Menu.scanner;

public class ProgramMenu {

    private ProgramDao programDao;
    private ProgTypeDao progTypeDao;
    private List<Program> programs;

    public ProgramMenu(EntityManagerFactory emf) {
        this.programDao = new ProgramDaoImpl(emf);
        this.progTypeDao = new ProgTypeDaoImpl(emf);
        this.programs = programDao.getAll();
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
                3. Delete a program
                4. Show program details by id 
                5. List all programs
                6. List programs by pace
                7. List programs by course
                8. Add/Edit Program Types
                0. Return to main menu""");
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
            case 1 -> addProgram();
            case 2 -> updateProgram();
            case 3 -> removeProgram();
            case 4 -> programDetails();
            case 5 -> printSummary(programDao.getAll());
            //case 6 -> programsByPace();
            //case 7 -> programsByCourse();
            //case 8 -> progTypeOptions();
            default -> System.out.println("invalid choice");
        }
    }


    private void removeProgram() {
        Program program = getProgramFromUser();
        programDao.delete(program);
    }

    private void programDetails() {
        Program program = getProgramFromUser();
        printDetails(List.of(program));
    }

    private Program getProgramFromUser() {
        printSummary(programs);
        System.out.println("Enter program id: ");
        int id = Integer.parseInt(scanner.nextLine());
        return programDao.getById(id).get();
    }


    private void printDetails(List<Program> programs) {
        System.out.println("\n -- Program Details --");
        String detailedFormat = "%-5s %-25s %-10s %-18s %-40s \n";
        System.out.printf(detailedFormat,
                "Id", "Name", "Pace", "Program Type", "Description");
        System.out.printf(detailedFormat,
                "--", "----", "------", "------------", "-----------");
        for (Program program : programs) {
            System.out.printf(detailedFormat,
                    program.getId(), program.getName(), program.getPace(),
                    program.getProgramType().getName(), program.getDescription());
        }
    }

    private void printSummary(List<Program> programs) {
        System.out.println("\n -- Summary of Programs --");
        String summaryFormat = "%-5s %-25s %-10s %-18s \n";
        System.out.printf(summaryFormat,
                "Id", "Name", "Pace", "Program Type");
        System.out.printf(summaryFormat,
                "--", "----", "------", "------------");
        for (Program program : programs) {
            System.out.printf(summaryFormat,
                    program.getId(), program.getName(), program.getPace(),
                    program.getProgramType().getName());
        }
    }

    private void updateProgram() {
        Program program = getProgramFromUser();

        System.out.println("Please enter the new details.");

        System.out.println("Program name: ");
        String name = scanner.nextLine();
        if (!name.isEmpty() || !name.equals(program.getName()))
            program.setName(name);

        System.out.println("Program description: ");
        String description = scanner.nextLine();
        if (!description.isEmpty() || !description.equals(program.getDescription()))
            program.setDescription(description);

        System.out.println("How many months is the program? ");
        int pace = Integer.parseInt(scanner.nextLine());
        if (pace != program.getPace())
            program.setPace(pace);

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
        int pace = Integer.parseInt(scanner.nextLine());

        return new Program(name, description, pace, programType);
    }

    private ProgramType getProgramTypeFromUser() {
        ProgramType programType = new ProgramType();
        printMany(progTypeDao.getAll(), "-- All Program Types --");
        System.out.println("Is the program type listed? (y/n)");
        String response = scanner.nextLine().toLowerCase(Locale.ROOT);
        if ("y".equals(response)) {
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
        int pace = Integer.parseInt(scanner.nextLine());

        System.out.println("Is the program type accredited? ");
        boolean accredited = scanner.nextBoolean();

        ProgramType programType = new ProgramType(name, pace, accredited);
        progTypeDao.create(programType);

        return programType;
    }




}