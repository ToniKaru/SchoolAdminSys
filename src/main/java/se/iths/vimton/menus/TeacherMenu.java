package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.Teacher;
import se.iths.vimton.impl.TeacherDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TeacherMenu {

    TeacherDao teacherDao;

    public TeacherMenu(EntityManagerFactory emf) {
        this.teacherDao = new TeacherDaoImpl(emf);
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
//            case 1 -> addTeacher();
            case 2 -> showAll();
//            case 3 -> updateCourse();
//            case 4 -> showCourseDetails();
//            case 5 -> deleteCourse();
            default -> System.out.println("Invalid choice");
        }
    }

    private void showAll() {
        List<Teacher> teachers = teacherDao.getAll();
        Menu.printMany(teachers, "All teachers");
    }

    private void addTeacher() {

    }
}
