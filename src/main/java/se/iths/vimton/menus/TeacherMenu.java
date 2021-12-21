package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.CourseDao;
import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Teacher;
import se.iths.vimton.impl.CourseDaoImpl;
import se.iths.vimton.impl.TeacherDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.*;

public class TeacherMenu {

    TeacherDao teacherDao;
    CourseDao courseDao;
    List<Teacher> teachers;

    public TeacherMenu(EntityManagerFactory emf) {
        this.teacherDao = new TeacherDaoImpl(emf);
        this.courseDao = new CourseDaoImpl(emf);
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
                                
                --- Teacher Options ---
                1. Add a teacher
                2. List all teachers
                3. Update a teacher
                4. Show a teacher's details
                5. Delete a teacher
                6. Add a teacher to a course
                7. Remove a course from a teacher
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
            case 6 -> addTeacherToCourse();
            case 7 -> removeTeacherFromCourse();
            default -> System.out.println("Invalid choice");
        }
    }

    private void removeTeacherFromCourse() {
        Optional<Teacher> teacher = getTeacher();

        if(teacher.isEmpty()) {
            System.out.println("Selected teacher id not found.");
            return;
        }
        System.out.println(teacher.get().getFirstName() + " " + teacher.get().getLastName() + " selected.");

        List<Course> teachersCourses = teacher.get().getTeacherCourses().stream().toList();
        if(teachersCourses.isEmpty()) {
            System.out.println(teacher.get().getFirstName() + " " + teacher.get().getLastName() + " has no courses.");
            return;
        }

        List<Course> allCourses = courseDao.getAll();

        printMany(teachersCourses, teacher.get().getFirstName() + " " + teacher.get().getLastName() + "'s courses");
        int courseId = getUserInput("course id", allCourses.get(0).getId(), allCourses.get(allCourses.size() - 1).getId());
        Optional<Course> course = courseDao.getById(courseId);

        if (course.isEmpty()) {
            System.out.println("Selected course id not found.");
            return;
        }

        course.get().removeTeacher(teacher.get());

        courseDao.update(course.get());
        teacherDao.update(teacher.get());

        System.out.println("Teacher successfully removed from " + course.get().getName() + " course");

        refreshTeachers();
    }

    private void addTeacherToCourse() {
        Optional<Teacher> teacher = getTeacher();

        if(teacher.isEmpty()) {
            System.out.println("Selected teacher id not found.");
            return;
        }

        System.out.println(teacher.get().getFirstName() + " " + teacher.get().getLastName() + " selected.");

        Optional<Course> course = getCourse(courseDao.getAll());

        if (course.isEmpty()) {
            System.out.println("Selected course id not found.");
            return;
        }

        System.out.println(course.get().getName() + " selected.");

        course.get().addTeacher(teacher.get());
        courseDao.update(course.get());
        teacherDao.update(teacher.get());

        System.out.println("Teacher successfully added to " + course.get().getName() + " course");

        refreshTeachers();
    }

    private Optional<Course> getCourse(List<Course> courses) {
        printMany(courses, "Courses");
        int courseId = getUserInput("course id", courses.get(0).getId(), courses.get(courses.size() - 1).getId());
        return courseDao.getById(courseId);
    }

    private Optional<Teacher> getTeacher() {
        showAll();
        System.out.print("Select a teacher from the list above. ");
        int id = getUserInput("teacher id", teachers.get(0).getId(), teachers.get(teachers.size() - 1).getId());
        return teacherDao.getById(id);
    }

    private void add() {
        String firstName = getNewDetails("teacher", "firstname");
        String lastName = getNewDetails("teacher", "lastname");
        String ssn = getNewDetails("teacher", "social security number");
        String phoneNumber = getNewDetails("teacher", "phone number");
        String email = getNewDetails("teacher", "email address");

        Teacher teacher;
        try {
             teacher = new Teacher(firstName, lastName, ssn, phoneNumber, email);
        } catch (IllegalArgumentException e) {
            System.out.print("New teacher could not be created because ");
            e.printStackTrace();
            return;
        }

        teacherDao.create(teacher);
        refreshTeachers();
    }

    private void delete() {
        Optional<Teacher> teacher = getTeacher();

        teacher.ifPresentOrElse(
                this::teacherDeletion,
                () -> System.out.println("Selected teacher id not found.")
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
        showAll();
        int id = getUserInput("teacher id", teachers.get(0).getId(), teachers.get(teachers.size() - 1).getId());
        Optional<Teacher> teacher = teacherDao.getById(id);

        teacher.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Teacher id " + id + " not found.")
        );
    }

    private void update() {
        showAll();
        int id = getUserInput("teacher id", teachers.get(0).getId(), teachers.get(teachers.size() - 1).getId());
        Optional<Teacher> teacher = teacherDao.getById(id);

        if(teacher.isEmpty()) {
            System.out.println("Teacher id " + id + " not found.");
            return;
        }

        String firstName = getUpdateDetails("new firstname");
        String lastName = getUpdateDetails("new lastname");
        String phoneNumber = getUpdateDetails("new phone number");
        String email = getUpdateDetails("new email address");

        if (propertyIsUpdated(firstName))
            teacher.get().setFirstName(firstName);
        if (propertyIsUpdated(lastName))
            teacher.get().setLastName(lastName);
        if (propertyIsUpdated(phoneNumber))
            teacher.get().setPhoneNumber(phoneNumber);
        if (propertyIsUpdated(email))
            teacher.get().setEmail(email);

        teacherDao.update(teacher.get());

        System.out.println("\n" +  teacher.get().getFirstName() + " " + teacher.get().getLastName() +
                           " successfully " + "updated.");
        System.out.println(teacher.get());

        refreshTeachers();
    }

    private String getUpdateDetails(String property) {
        System.out.println("Enter teacher's " + property + " or 'x' to skip:");
        return scanner.nextLine().trim();
    }

    private void refreshTeachers() {
        teachers = teacherDao.getAll();
    }

    private void showAll() {
        List<Teacher> teachers = teacherDao.getAll();
        printMany(teachers, "All teachers");
    }

}
