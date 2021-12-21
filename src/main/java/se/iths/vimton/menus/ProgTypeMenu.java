package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.ProgTypeDao;
import se.iths.vimton.entities.ProgramType;
import se.iths.vimton.impl.ProgTypeDaoImpl;
import se.iths.vimton.utils.Print;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static se.iths.vimton.Menu.*;

public class ProgTypeMenu {

    private ProgTypeDao progTypeDao;
    private List<ProgramType> types;

    public ProgTypeMenu(EntityManagerFactory emf) {
        this.progTypeDao = new ProgTypeDaoImpl(emf);
        this.types = progTypeDao.getAll();
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
            case 2 -> showAll();
            case 3 -> update();
            case 4 -> showDetails();
            case 5 -> delete();
            default -> System.out.println("invalid choice");
        }
    }



    private Consumer<? super ProgramType> printDetails(ProgramType type) {
        Print.progTypeDetails(type);
        return null;
    }

    private void add() {
        String name = getNewDetails("program type", "name");
        int creditsRequired = getUserInput("credits required", 0,2000);
        boolean accredited = getUserInput("program type", "accreditation");

        ProgramType type;
        try {
            type = new ProgramType(name, creditsRequired, accredited);
        } catch (IllegalArgumentException e) {
            System.out.print("New program type could not be created because ");
            e.printStackTrace();
            return;
        }
        progTypeDao.create(type);
        refreshTypes();
    }

    private void showAll(){
        Print.allProgTypes(progTypeDao.getAll());
    }

    private void update(){
        Optional<ProgramType> type = getTypeFromUser();
        type.ifPresentOrElse(
                this::updateProgType,
                () ->  System.out.println("Program type not found. Cannot update.")

        );
    }

    private void updateProgType(ProgramType type) {
        String name = getUpdateDetails("program type", "name");
        int creditsRequired = getUserInput("credits required", 0,2000);
        boolean accredited = getUserInput("program type", "accreditation");

        if(propertyIsUpdated(name))
            type.setName(name);
        if(creditsRequired != type.getCreditsRequired())
            type.setCreditsRequired(creditsRequired);
        if(accredited != type.isAccredited())
            type.setAccredited(accredited);

        progTypeDao.update(type);
        refreshTypes();
        Print.progTypeDetails(type);
    }

    private void showDetails() {
        Optional<ProgramType> type = getTypeFromUser();
        type.ifPresentOrElse(
                printDetails(type.get()),
                () -> System.out.println("Program type not found.")
        );
    }

    private void delete(){
        Optional<ProgramType> type = getTypeFromUser();
        type.ifPresentOrElse(
                this::deleteType,
                () -> System.out.println("Program type not found. No type deleted.")
        );
    }

    private void deleteType(ProgramType type) {
        System.out.println("Are you sure you want to delete " + type.getName() + "? Enter Y/N:");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("y")) {
            progTypeDao.delete(type);
            System.out.println(type.getName() + " successfully deleted.");
            refreshTypes();
        } else {
            System.out.println("Cancelling...");
        }
    }

    Optional<ProgramType> getTypeFromUser() {
        Print.allProgTypes(progTypeDao.getAll());
        int id = 0;
        if(!types.isEmpty())
            id = getUserInput("id", types.get(0).getId(), types.get(types.size() - 1).getId());

        return progTypeDao.getById(id);
    }

    private void refreshTypes() {
        types = progTypeDao.getAll();

    }

}
