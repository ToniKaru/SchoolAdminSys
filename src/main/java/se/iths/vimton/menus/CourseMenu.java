package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.CourseDao;
import se.iths.vimton.dao.LanguageDao;
import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Language;
import se.iths.vimton.impl.CourseDaoImpl;
import se.iths.vimton.impl.LanguageDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.scanner;

public class CourseMenu {

    CourseDao courseDao;
    LanguageDao languageDao;
    List<Course> courses;

    public CourseMenu(EntityManagerFactory emf) {
        this.courseDao = new CourseDaoImpl(emf);
        this.languageDao = new LanguageDaoImpl(emf);
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
                4. Show course details
                5. Delete a course
                0. Return to main menu"""
        );
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 0 -> Menu.cancel();
            case 1 -> addCourse();
            case 2 -> showAll();
            case 3 -> updateCourse();
            case 4 -> showCourseDetails();
            case 5 -> deleteCourse();
            default -> System.out.println("Invalid choice");
        }
    }

    private void addCourse() {
        List<Language> languages = languageDao.getAll();

        if(languages.isEmpty()) {
            System.out.println("Please add a new language first.");
            System.out.println("Returning to main menu...");
            return;
        }

        String name = getDetails("name");
        String description = getDetails("description");
        int credits = getUserInput("credits", 5, 500);

        System.out.println("\nLanguages: ");
        languages.forEach(System.out::println);
        int languageId = getUserInput("language id from the list above", languages.get(0).getId(), languages.size());
        Optional<Language> language = languageDao.getById(languageId);

        Course course = new Course(name, description, credits, language.get());
        courseDao.create(course);

        refreshCourses();
    }

    private String getDetails(String property) {
        String input;
        while(true){
            System.out.println("Enter new course " + property);
            input = scanner.nextLine();
            if(!input.trim().isEmpty())
               break;

            System.out.println("Please enter a " +  property + " for the new course.");
        }
        return input;
    }

    private void deleteCourse() {
        int id = getUserInput("course id", 1, courses.size());
        Optional<Course> course = courseDao.getById(id);

        course.ifPresentOrElse(
                this::courseDeletion,
                () -> System.out.println("Course id " + id + " not found.")
        );
    }

    private void courseDeletion(Course course) {
        courseDao.delete(course);
        System.out.println("Course id " + course.getId() + " successfully deleted.");
        refreshCourses();
    }

    private void refreshCourses() {
        courses = courseDao.getAll();
    }

    private void showCourseDetails() {
        int id = getUserInput("course id", 1, courses.size());
        Optional<Course> course = courseDao.getById(id);

        course.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Course id " + id + " not found.")
        );
    }

    private void updateCourse() {
        int id = getUserInput("course id", 1, courses.size());
        Optional<Course> course = courseDao.getById(id);

        if (course.isEmpty()) {
            System.out.println("Course id " + id + " not found.");
            return;
        }

        System.out.println("Enter course's new name or 'x' to skip:");
        String name = scanner.nextLine().trim();

        System.out.println("Enter course's new description or 'x' to skip:");
        String description = scanner.nextLine().trim();

        System.out.println("Enter course's credits or 0 to skip:");
        int credits = getUserInput("new credits", 0, 500);

        if (name.length() != 0 && !name.equalsIgnoreCase("x") )
            course.get().setName(name);
        if (description.length() != 0 && !description.equalsIgnoreCase("x"))
            course.get().setDescription(description);
        if (credits > 0)
            course.get().setCredits(credits);

        courseDao.update(course.get());

        refreshCourses();
    }

    public int getUserInput(String property, int min, int max) {
        int selection;
        while(true) {
            System.out.println("Enter" + property + ":");
            try {
                selection = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if(selection < min || selection > max) {
                System.out.println("Please enter valid " + property);
                continue;
            }
            break;
        }
        return selection;
    }

    private void showAll() {
        List<Course> courses = courseDao.getAll();
        Menu.printMany(courses, "All courses");
    }

}
