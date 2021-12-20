package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.dao.StudentDao;
import se.iths.vimton.entities.Program;
import se.iths.vimton.impl.ProgramDaoImpl;
import se.iths.vimton.impl.StudentDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.getUserInput;
import static se.iths.vimton.Menu.printMany;

public class StatisticsMenu {
    
    ProgramDao programDao;
    StudentDao studentDao;
    
    public StatisticsMenu(EntityManagerFactory emf) {
        this.programDao = new ProgramDaoImpl(emf);
        this.studentDao = new StudentDaoImpl(emf);
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
                                
                --- Statistics Options ---
                1. Get the number of students in a program
                2. Show number of students per program
                0. Return to main menu"""
        );
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
            case 1 -> showStudentsPerSelectedProgram();
//            case 2 -> showAll();
            default -> System.out.println("Invalid choice");
        }
    }

    private void showStudentsPerSelectedProgram() {
        List<Program> programs = programDao.getAll();
        printMany(programs, "All programs");

        int id = getUserInput("program", programs.get(0).getId(),programs.get(programs.size() - 1).getId());
        Optional<Program> program = programDao.getById(id);

        program.ifPresentOrElse(
                this::showResults,
                () ->  System.out.println("Program id not found.")
        );
    }

    private void showResults(Program program) {
        Optional<Long> amount = studentDao.studentsByProgram(program);
        amount.ifPresentOrElse(
                number -> System.out.println(program.getName() + " has " + number + " of students"),
                () -> System.out.println(program.getName() + " has no students.")
        );
    }

}
