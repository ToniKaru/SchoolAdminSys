package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.ProgTypeDao;
import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.ProgramType;
import se.iths.vimton.impl.ProgTypeDaoImpl;
import se.iths.vimton.impl.ProgramDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static se.iths.vimton.Menu.*;
import static se.iths.vimton.Menu.scanner;

public class ProgramMenu {

    private ProgramDao programDao;
    private ProgTypeDao progTypeDao;
    private List<Program> programs;
    private List<ProgramType> progTypes;
    private ProgTypeMenu progTypeMenu;

    public ProgramMenu(EntityManagerFactory emf) {
        this.programDao = new ProgramDaoImpl(emf);
        this.progTypeDao = new ProgTypeDaoImpl(emf);
        this.programs = programDao.getAll();
        this.progTypes = progTypeDao.getAll();
        this.progTypeMenu = new ProgTypeMenu(emf);

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
                2. List all programs
                3. Update Program
                4. Show program details by id
                5. Delete a program
                6. List programs by pace
                7. List programs by course
                8. Program Type Options
                0. Return to main menu"""
        );
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
            case 1 -> add();
            case 2 -> showAll();
            case 3 -> update();
            case 4 -> showDetails();
            case 5 -> delete();
            case 6 -> programsByPace();
            case 7 -> programsByCourse();
            case 8 -> progTypeOptions();
            default -> System.out.println("invalid choice");
        }
    }

    //todo: do we need to refreshPrograms? Test & see


    private void add() {
        Optional<ProgramType> progTypeOpt = progTypeMenu.getTypeFromUser();
        progTypeOpt.ifPresentOrElse(
                this::addNew,
                () -> System.out.println("Program type not found. Cannot create program.")
        );
    }

    private void addNew(ProgramType programType) {
        String name = getNewDetails("program", "name");
        String description = getNewDetails("program", "description");
        int pace = getUserInput("pace", 1,200);

        Program program;
        try {
            program = new Program(name, description, pace, programType);
        } catch (IllegalArgumentException e) {
            System.out.print("New program could not be created because ");
            e.printStackTrace();
            return;
        }

        programDao.create(program);
        refreshPrograms();
    }

    private void showAll() {
        printAll(programs);
    }

    private void update() {
        Optional<Program> program = getProgramFromUser();
        program.ifPresentOrElse(
                this::updateProgram,
                () ->  System.out.println("Program id not found. Cannot update program.")
            );
        }

    private void updateProgram(Program program) {
        Optional<ProgramType> progTypeOpt = progTypeMenu.getTypeFromUser();
        String name = getUpdateDetails("program", "name");
        String description = getUpdateDetails("program", "description");
        int pace = getUserInput("pace", 1,200);

        progTypeOpt.ifPresent(program::setProgramType);
        if (propertyIsUpdated(name))
            program.setName(name);
        if (propertyIsUpdated(description))
            program.setDescription(description);
        if (pace != program.getPace())
            program.setPace(pace);

        programDao.update(program);
        refreshPrograms();
        printDetails(program);
    }

    private void delete() {
        Optional<Program> program = getProgramFromUser();
        program.ifPresentOrElse(
                this::deleteProgram,
                () -> System.out.println("Program id not found. No program deleted.")
        );
    }

    private void deleteProgram(Program program) {
        System.out.println("Are you sure you want to delete " + program.getName() + "? Enter Y/N:");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("y")) {
            programDao.delete(program);
            System.out.println(program.getName() + " successfully deleted.");
            refreshPrograms();
        } else {
            System.out.println("Cancelling...");
        }
    }

    private void showDetails() {
        Optional<Program> program = getProgramFromUser();
        program.ifPresentOrElse(
                this::printDetails,
                () -> System.out.println("Program id not found.")
        );
    }

    private void printDetails(Program program) {
        printDetails(List.of(program));
    }

    private void programsByPace() {
        List<Integer> list = programs.stream()
                .map(Program::getPace)
                .distinct()
                .toList();
        int pace = 0;
        if(!list.isEmpty())
            pace = getPace(list);
        printAll(programDao.getByPace(pace));
    }

    private int getPace(List<Integer> list) {
        System.out.println("-- Paces currently in database --");
        list.forEach(System.out::println);
        int max = list.stream()
                .mapToInt(l -> l)
                .max()
                .orElseThrow(NoSuchElementException::new);
        int min = list.stream()
                .mapToInt(l -> l)
                .min()
                .orElseThrow(NoSuchElementException::new);

        return getUserInput("pace", min, max);
    }

    private void programsByCourse() {
        //Optional<Course> optCourse = getCourseFromUser();
        //optCourse.ifPresentOrElse(
            //printAll(programDao.getByCourse(course)
            //() -> System.out.println("Course not found.") );
    }

    private void progTypeOptions() {
        progTypeMenu.run();
    }

    private Optional<Program> getProgramFromUser() {
        printAll(programs);
        int id = 0;
        if(!programs.isEmpty())
            id = getUserInput("program id", programs.get(0).getId(), programs.get(programs.size() - 1).getId());
        return programDao.getById(id);
    }


    private void printDetails(List<Program> programs) {
        if(!programs.isEmpty()) {
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
                //todo: add % to pace
            }
        }
        else
            System.out.println("No program(s) to display");
    }

    private void printAll(List<Program> programs) {
        if(!programs.isEmpty()) {

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
        else
            System.out.println("No programs to display");

        //todo: add % to pace
    }

    private void refreshPrograms() {
        programs = programDao.getAll();
        progTypes = progTypeDao.getAll();
    }



}