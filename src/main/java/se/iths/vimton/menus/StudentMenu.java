package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.dao.StudentDao;
import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Guard;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Student;
import se.iths.vimton.impl.ProgramDaoImpl;
import se.iths.vimton.impl.StudentDaoImpl;

import javax.persistence.EntityManagerFactory;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.*;

public class StudentMenu {

    StudentDao studentDao;
    ProgramDao programDao;
    List<Student> students;

    public StudentMenu(EntityManagerFactory emf) {
        this.studentDao = new StudentDaoImpl(emf);
        this.programDao = new ProgramDaoImpl(emf);
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
                4. Show a student's details
                5. Delete a student
                0. Return to main menu"""
        );
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> cancel();
//            case 1 -> add();
            case 2 -> showAll();
            case 3 -> update();
            case 4 -> showDetails();
//            case 5 -> delete();
            default -> System.out.println("Invalid choice");
        }
    }

    private void update() {
        showAll();
        int id = getUserInput("student id", students.get(0).getId(), students.get(students.size() - 1).getId());
        Optional<Student> student = studentDao.getById(id);

        if(student.isEmpty()) {
            System.out.println("Student id " + id + " not found");
            return;
        }

        System.out.println("Updating " + student.get().getFirstName() + " " + student.get().getLastName());

        System.out.println("Enter student's new first name or 'x' to skip:");
        String firstName = scanner.nextLine().trim();

        System.out.println("Enter student's new last name or 'x' to skip:");
        String lastName = scanner.nextLine().trim();

        System.out.println("Enter student's new email address or 'x' to skip:");
        String email = scanner.nextLine().trim();

        Optional<Program> program = getNewProgram();

        if(propertyIsUpdated(firstName))
            student.get().setFirstName(firstName);
        if(propertyIsUpdated(lastName))
            student.get().setLastName(lastName);
        if(propertyIsUpdated(email))
            student.get().setEmail(email);

        if(program.isPresent()) {
            Optional<Program> currentProgram = programDao.getById(student.get().getProgram().getId());
            currentProgram.ifPresent(program1 -> {
                program1.getStudents().remove(student.get());
                programDao.update(program1);
            });
            student.get().setProgram(program.get());
        }

        studentDao.update(student.get());

        System.out.println("Student successfully updated.");
        System.out.println(student.get());
        refreshStudents();
    }

    private Optional<Program> getNewProgram() {
        List<Program> programs = programDao.getAll();

        printMany(programs, "Available programs");
        System.out.println("Select a program id from the list above");

        int id = getUserInput("program id", programs.get(0).getId(), programs.get(programs.size() - 1).getId());
        return programDao.getById(id);
    }
//
//    private String getNewSsn() {
//        String ssn;
//        while (true) {
//            ssn = scanner.nextLine().trim();
//            try {
//                Guard.Against.ssnInvalid(ssn);
//                break;
//            } catch (IllegalArgumentException e) {
//                System.out.println("Please enter a valid social security number");
//            }
//        }
//        return ssn;
//    }

    private void showDetails() {
        showAll();
        int id = getUserInput("student id", students.get(0).getId(), students.get(students.size() - 1).getId());
        Optional<Student> student = studentDao.getById(id);

        student.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Student not found.")
        );
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
