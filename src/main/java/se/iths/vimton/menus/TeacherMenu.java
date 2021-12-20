package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.Teacher;
import se.iths.vimton.impl.TeacherDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.getUserInput;
import static se.iths.vimton.Menu.scanner;

public class TeacherMenu {

    TeacherDao teacherDao;
    List<Teacher> teachers;

    public TeacherMenu(EntityManagerFactory emf) {
        this.teacherDao = new TeacherDaoImpl(emf);
        teachers = teacherDao.getAll();
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
                                
                --- Course Options ---
                1. Add a teacher
                2. List all teachers
                3. Update a teacher
                4. Show a teacher's details
                5. Delete a teacher
                6. Add a course to a teacher
                7. Remove a course from a teacher
                0. Return to main menu"""
        );
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
//            case 1 -> add();
            case 2 -> showAll();
            case 3 -> update();
            case 4 -> showDetails();
            case 5 -> delete();
            default -> System.out.println("Invalid choice");
        }
    }

    private void delete() {
        int id = getUserInput("teacher id", teachers.get(0).getId(), teachers.get(teachers.size() - 1).getId());
        Optional<Teacher> teacher = teacherDao.getById(id);

        teacher.ifPresentOrElse(
                this::teacherDeletion,
                () -> System.out.println("Teacher id " + id + " not found.")
        );
    }

    private void teacherDeletion(Teacher teacher) {
        System.out.println("Are you sure you want to delete " + teacher.getFirstName() + " " + teacher.getLastName()
                           + "? Enter Y/N:");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("y")) {
            teacherDao.delete(teacher);
            System.out.println(teacher.getFirstName() + " " + teacher.getLastName() + " successfully deleted.");
            refreshTeachers();
        } else {
            System.out.println("Cancelling...");
        }
    }

    private void showDetails() {
        int id = getUserInput("teacher id", teachers.get(0).getId(), teachers.get(teachers.size() - 1).getId());
        Optional<Teacher> teacher = teacherDao.getById(id);

        teacher.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Teacher id " + id + " not found.")
        );
    }

    private void update() {
        int id = getUserInput("teacher id", teachers.get(0).getId(), teachers.get(teachers.size() - 1).getId());
        Optional<Teacher> teacher = teacherDao.getById(id);

        if(teacher.isEmpty()) {
            System.out.println("Teacher id " + id + " not found.");
            return;
        }

        System.out.println("Enter teacher's new firstname or 'x' to skip:");
        String firstName = scanner.nextLine().trim();

        System.out.println("Enter teacher's new lastname or 'x' to skip:");
        String lastName = scanner.nextLine().trim();

        System.out.println("Enter teacher's new phone number or 'x' to skip:");
        String phoneNumber = scanner.nextLine().trim();

        System.out.println("Enter teacher's new email address or 'x' to skip:");
        String email = scanner.nextLine().trim();

        if (!firstName.equalsIgnoreCase("x"))
            teacher.get().setFirstName(firstName);
        if (!lastName.equalsIgnoreCase("x"))
            teacher.get().setLastName(firstName);
        if (!phoneNumber.equalsIgnoreCase("x"))
            teacher.get().setPhoneNumber(firstName);
        if (!email.equalsIgnoreCase("x"))
            teacher.get().setEmail(firstName);

        teacherDao.update(teacher.get());

        refreshTeachers();
    }

    private void refreshTeachers() {
        teachers = teacherDao.getAll();
    }

    private void showAll() {
        List<Teacher> teachers = teacherDao.getAll();
        Menu.printMany(teachers, "All teachers");
    }

}
