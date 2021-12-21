package se.iths.vimton.menus;

import net.bytebuddy.dynamic.DynamicType;
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
import java.util.Optional;

import static se.iths.vimton.Menu.*;

public class ProgTypeMenu {

    private ProgTypeDao progTypeDao;
    private List<ProgramType> progTypes;

    public ProgTypeMenu(EntityManagerFactory emf) {
        this.progTypeDao = new ProgTypeDaoImpl(emf);
        this.progTypes = progTypeDao.getAll();
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
                                
                --- Program Type Options ---
                1. Add program type
                2. List all program types
                3. Update program type
                4. Show program type details by id
                5. Delete a program type
                0. Return to main menu"""
        );
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
            case 1 -> add();
            //case 2 -> showAll();
            //case 3 -> update();
            //case 4 -> showDetails();
            //case 5 -> delete();
            default -> System.out.println("invalid choice");
        }
    }

    private void add() {
        String name = getNewDetails("program type", "name");
        int pace = getUserInput("credits required", 0,2000);
        boolean accredited = getUserInput("program type", "accreditation");

        ProgramType programType;
        try {
            programType = new ProgramType(name, pace, accredited);
        } catch (IllegalArgumentException e) {
            System.out.print("New program type could not be created because ");
            e.printStackTrace();
            return;
        }
        progTypeDao.create(programType);
        refreshProgramTypes();
    }

    Optional<ProgramType> getTypeFromUser() {
        Main.printMany(progTypeDao.getAll(), "-- All Program Types --");
        int id = 0;
        if(!progTypes.isEmpty())
            id = getUserInput("id", progTypes.get(0).getId(), progTypes.get(progTypes.size() - 1).getId());

        return progTypeDao.getById(id);
    }

    private void refreshProgramTypes() {
        progTypes = progTypeDao.getAll();

    }

}
