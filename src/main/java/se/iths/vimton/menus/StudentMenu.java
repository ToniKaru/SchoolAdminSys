package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.StudentDao;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Student;
import se.iths.vimton.impl.StudentDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.*;

public class StudentMenu {

    StudentDao studentDao;
    List<Student> students;

    public StudentMenu(EntityManagerFactory emf) {
        this.studentDao = new StudentDaoImpl(emf);
        this.students = studentDao.getAll();
    }




    //todo: Vimbayi

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
                                
                --- Student Options ---
                1. Add a student
                2. List all student
                3. Update a student
                4. Show one student's details
                5. Delete a student
                0. Return to main menu"""
        );
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> cancel();
//            case 1 -> add();
            case 2 -> showAll();
//            case 3 -> update();
//            case 4 -> showDetails();
//            case 5 -> delete();
            default -> System.out.println("Invalid choice");
        }
    }

    private void showAll() {
        printMany(students, "All students");
    }

    private void printStudents(List<Student> students, String heading) {
        System.out.println("\n -- " + heading + " --");

        if(students.isEmpty())
            System.out.println("No program(s) to display");

        System.out.println("Firstname" + "\t\t" + "Lastname" + "\t\t" + "SSN" + "\t\t" + "Email" + "\t\t" + "Program");
        students.forEach(student ->  System.out.println(student.getFirstName() + "\t\t" + student.getLastName() + "\t\t" +
                                student.getSsn() + "\t\t" +  student.getEmail() + "\t\t" +  student.getProgram().getName()));
    }

    private void refreshStudents() {
        students = studentDao.getAll();
    }

        public Optional<Student> getExistingStudentFromUser() {
        int id = getUserInput("student id", 1, students.size());
        Optional<Student> student = studentDao.getById(id);

        if (student.isEmpty()) {
            System.out.println("Course id " + id + " not found.");
            return Optional.empty();
        }
        return student;
    }
}
