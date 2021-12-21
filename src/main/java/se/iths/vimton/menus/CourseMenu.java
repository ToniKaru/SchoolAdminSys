package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.CourseDao;
import se.iths.vimton.dao.LanguageDao;
import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.dao.TeacherDao;
import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Language;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Teacher;
import se.iths.vimton.impl.CourseDaoImpl;
import se.iths.vimton.impl.LanguageDaoImpl;
import se.iths.vimton.impl.ProgramDaoImpl;
import se.iths.vimton.impl.TeacherDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.*;

public class CourseMenu {

    CourseDao courseDao;
    LanguageDao languageDao;
    TeacherDao teacherDao;
    ProgramDao programDao;
    List<Course> courses;

    public CourseMenu(EntityManagerFactory emf) {
        this.courseDao = new CourseDaoImpl(emf);
        this.languageDao = new LanguageDaoImpl(emf);
        this.teacherDao = new TeacherDaoImpl(emf);
        this.programDao = new ProgramDaoImpl(emf);
        courses = courseDao.getAll();
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
                1. Add a course
                2. List all courses
                3. Update a course
                4. Show a course's details
                5. Delete a course
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
            default -> System.out.println("Invalid choice");
        }
    }

    private void add() {
        List<Language> languages = languageDao.getAll();

        if(languages.isEmpty()) {
            System.out.println("Please add a new language first.");
            System.out.println("Returning to main menu...");
            return;
        }

        String name = getNewDetails("course","name");
        String description = getNewDetails("course","description");
        int credits = getUserInput("credits", 5, 500);

        System.out.println("\nLanguages: ");
        languages.forEach(System.out::println);
        int languageId = getUserInput(
                "language id from the list above",
                languages.get(0).getId(),
                languages.get(languages.size() - 1).getId()
        );
        Optional<Language> language = languageDao.getById(languageId);

        Course course = new Course(name, description, credits, language.get());
        courseDao.create(course);

        refreshCourses();
    }

    private void delete() {
        showAll();
        int id = getUserInput("course id", courses.get(0).getId(), courses.get(courses.size() - 1).getId());
        Optional<Course> course = courseDao.getById(id);

        course.ifPresentOrElse(
                this::courseDeletion,
                () -> System.out.println("Course id " + id + " not found.")
        );
    }

    private void courseDeletion(Course course) {
        System.out.println("Are you sure you want to delete " + course.getName() + "? Enter Y/N:");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("y")) {
            try {
                removeLanguage(course);
                removeTeachers(course);
                removePrograms(course);
                courseDao.delete(course);
                System.out.println(course.getName() + " successfully deleted.");
                refreshCourses();
            } catch (Exception e) {
                System.out.println(course.getName() + " is connected to other entities & cannot be deleted.");
            }
        } else {
            System.out.println("Cancelling...");
        }
    }

    private void removePrograms(Course course) {
        List<Program> programs = new ArrayList<>();

        course.getPrograms().forEach(program -> programDao.getById(program.getId()).ifPresent(programs::add));
        programs.forEach(program -> program.getCourses().remove(course));
        course.getPrograms().clear();

        programs.forEach(program -> programDao.update(program));
    }

    private void removeTeachers(Course course) {
        List<Teacher> teachers = new ArrayList<>();

        course.getTeachers().forEach(teacher -> teacherDao.getBySsn(teacher.getSsn()).ifPresent(teachers::add));
        teachers.forEach(teacher -> teacher.getTeacherCourses().remove(course));
        course.getTeachers().clear();

        teachers.forEach(teacher -> teacherDao.update(teacher));
    }

    private void removeLanguage(Course course) {
        Optional<Language> language = languageDao.getById(course.getLanguage().getId());

        if (language.isEmpty())
            throw new RuntimeException();

        language.get().getCourses().remove(course);
        course.setLanguage(new Language());
        languageDao.update(language.get());
    }

    private void refreshCourses() {
        courses = courseDao.getAll();
    }

    private void showDetails() {
        showAll();
        int id = getUserInput("course id", courses.get(0).getId(), courses.get(courses.size() - 1).getId());
        Optional<Course> course = courseDao.getById(id);

        course.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Course id " + id + " not found.")
        );
    }

    private void update() {

        Optional<Course> course = getCourse();
        if (course.isEmpty())
            return;

        System.out.println("Enter course's new name or 'x' to skip:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter course's new description or 'x' to skip:");
        String description = scanner.nextLine().trim();

        System.out.println("Enter course's credits or 0 to skip:");
        int credits = getUserInput("new credits", 0, 500);

        if (propertyIsUpdated(name))
            course.get().setName(name);
        if (propertyIsUpdated(description))
            course.get().setDescription(description);
        if (credits > 0)
            course.get().setCredits(credits);

        courseDao.update(course.get());

        System.out.println(course.get().getName() + " successfully updated.");
        System.out.println(course.get());

        refreshCourses();
    }

    public Optional<Course> getCourse() {
        showAll();
        int id = getUserInput("course id", 1, courses.size());
        Optional<Course> course = courseDao.getById(id);

        if (course.isEmpty())
            System.out.println("Course id " + id + " not found.");

        return course;
    }


    private void showAll() {
        List<Course> courses = courseDao.getAll();
        Menu.printMany(courses, "All courses");
    }

}
