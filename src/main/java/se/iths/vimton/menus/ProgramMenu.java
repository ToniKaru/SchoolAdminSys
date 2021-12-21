package se.iths.vimton.menus;

import se.iths.vimton.Menu;
import se.iths.vimton.dao.CourseDao;
import se.iths.vimton.dao.ProgTypeDao;
import se.iths.vimton.dao.ProgramDao;
import se.iths.vimton.dao.StudentDao;
import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.ProgramType;
import se.iths.vimton.entities.Student;
import se.iths.vimton.impl.CourseDaoImpl;
import se.iths.vimton.impl.ProgTypeDaoImpl;
import se.iths.vimton.impl.ProgramDaoImpl;
import se.iths.vimton.impl.StudentDaoImpl;
import se.iths.vimton.utils.Print;

import javax.persistence.EntityManagerFactory;
import java.util.*;

import static se.iths.vimton.Menu.*;
import static se.iths.vimton.Menu.scanner;

public class ProgramMenu {

    private ProgramDao programDao;
    private ProgTypeDao progTypeDao;
    private List<Program> programs;
    private List<ProgramType> progTypes;
    private ProgTypeMenu progTypeMenu;
    private CourseMenu courseMenu;
    private StudentMenu studentMenu;
    private StudentDao studentDao;
    private CourseDao courseDao;

    public ProgramMenu(EntityManagerFactory emf) {
        this.programDao = new ProgramDaoImpl(emf);
        this.progTypeDao = new ProgTypeDaoImpl(emf);
        this.programs = programDao.getAll();
        this.progTypes = progTypeDao.getAll();
        this.progTypeMenu = new ProgTypeMenu(emf);
        this.courseMenu = new CourseMenu(emf);
        this.studentMenu = new StudentMenu(emf);
        this.courseDao = new CourseDaoImpl(emf);
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
                                
                --- Program Options ---
                1. Add Program
                2. List all programs
                3. Update a program
                4. Show a program's details
                5. Delete a program
                6. Show programs by pace
                7. Show programs by course
                
                8. Add a course to a program
                9. Show all courses in a program
                10. Remove a course from a program
                11. Add a student to a program
                12. Remove a student from a program
                13. Show all students in a program
                14. Program Type Options
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
            case 6 -> programsByPace();
            case 7 -> programsByCourse();

            case 8 -> addCourseToProgram();
            case 9 -> listProgramCourses();
            case 10 -> removeCourseFromProgram();
            case 11 -> addStudentToProgram();
            case 12 -> removeStudentFromProgram();
            case 13 -> listStudentsInProgram();

            case 14 -> progTypeOptions();
            default -> System.out.println("invalid choice");
        }
    }

    private void listStudentsInProgram() {
        Optional<Program> program = getProgramFromUser();
        if (program.isPresent())
            Print.allStudents(program.get().getStudents());
    }


    private void removeStudentFromProgram() {
        Optional<Program> program = getProgramFromUser();
        Optional<Student> student = studentMenu.getExistingStudentFromUser();
        if (student.isPresent() && program.isPresent()) {
            program.get().removeStudent(student.get());
            programDao.update(program.get());
            studentDao.update(student.get());
            refreshPrograms();
        }
    }

    private void addStudentToProgram() {
        Optional<Program> program = getProgramFromUser();
        Optional<Student> student = studentMenu.getExistingStudentFromUser();
        if (student.isPresent() && program.isPresent()) {
            program.get().addStudent(student.get());
            programDao.update(program.get());
            studentDao.update(student.get());
            refreshPrograms();
        }
    }

    private void removeCourseFromProgram() {
        Optional<Program> program = getProgramFromUser();
        Optional<Course> course = courseMenu.getCourse();
        if (course.isPresent() && program.isPresent()) {
            program.get().addCourse(course.get());
            programDao.update(program.get());
            courseDao.update(course.get());
            refreshPrograms();
        }
    }

    private void listProgramCourses() {
        Optional<Program> program = getProgramFromUser();
        if (program.isPresent())
            Print.allCourses(program.get().getCourses());
    }

    private void addCourseToProgram() {
        Optional<Program> program = getProgramFromUser();
        Optional<Course> course = courseMenu.getCourse();
        if (course.isPresent() && program.isPresent()) {
            programDao.addCourse(program.get(), course.get());
            programDao.update(program.get());
            courseDao.update(course.get());
            refreshPrograms();
        }
    }




    private void add() {
        Optional<ProgramType> progTypeOpt = progTypeMenu.getTypeFromUser();
        progTypeOpt.ifPresentOrElse(
                this::addNew,
                () -> System.out.println("Program type not found. Cannot create program.")
        );
    }

    private void addNew(ProgramType programType) {
        String name = getNewDetails("program", "name");
        String description = getNewDetails("program", "description");
        int pace = getUserInput("pace", 1,200);

        Program program;
        try {
            program = new Program(name, description, pace, programType);
        } catch (IllegalArgumentException e) {
            System.out.print("New program could not be created because ");
            e.printStackTrace();
            return;
        }

        programDao.create(program);
        refreshPrograms();
    }

    private void showAll() {
        Print.allPrograms(programs);
    }

    private void update() {
        Optional<Program> program = getProgramFromUser();
        program.ifPresentOrElse(
                this::updateProgram,
                () ->  System.out.println("Program not found. Cannot update.")
            );
        }

    private void updateProgram(Program program) {
        Optional<ProgramType> progTypeOpt = progTypeMenu.getTypeFromUser();
        String name = getUpdateDetails("program", "name");
        String description = getUpdateDetails("program", "description");
        int pace = getUserInput("pace", 1,200);

        progTypeOpt.ifPresent(program::setProgramType);
        if (propertyIsUpdated(name))
            program.setName(name);
        if (propertyIsUpdated(description))
            program.setDescription(description);
        if (pace != program.getPace())
            program.setPace(pace);

        programDao.update(program);
        refreshPrograms();
        printDetails(program);
    }

    private void delete() {
        Optional<Program> program = getProgramFromUser();
        program.ifPresentOrElse(
                this::deleteProgram,
                () -> System.out.println("Program id not found. No program deleted.")
        );
    }

    private void deleteProgram(Program program) {
        System.out.println("Are you sure you want to delete " + program.getName() + "? Enter Y/N:");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("y")) {
            try {
                removeStudents(program);
                removeCourses(program);
                programDao.delete(program);
                System.out.println(program.getName() + " successfully deleted.");
                refreshPrograms();
            }
            catch (Exception e){
                System.out.println(program.getName() + " is connected to other entities & cannot be deleted.");
            }
        } else {
            System.out.println("Cancelling...");
        }
    }

    private void removeCourses(Program program) {
        Set<Course> courses = new HashSet<>();

        program.getCourses().forEach(course -> courseDao.getById(course.getId()).ifPresent(courses::add));
        courses.forEach(course -> course.getPrograms().remove(course));
        program.getCourses().clear();

        courses.forEach(course -> courseDao.update(course));
    }

    private void removeStudents(Program program) {
        List<Student> students = new ArrayList<>();

        program.getStudents().forEach(student -> studentDao.getById(student.getId()).ifPresent(students::add));
        students.forEach(student -> student.getProgram().removeStudent(student));
        program.getStudents().clear();

        students.forEach(student -> studentDao.update(student));
    }

    private void showDetails() {
        Optional<Program> program = getProgramFromUser();
        program.ifPresentOrElse(
                this::printDetails,
                () -> System.out.println("Program not found.")
        );
    }

    private void printDetails(Program program) {
        Print.programDetails(List.of(program));
    }


    private void programsByPace() {
        List<Integer> list = programs.stream()
                .map(Program::getPace)
                .distinct()
                .toList();
        int pace = 0;
        if(!list.isEmpty())
            pace = getPace(list);
        Print.allPrograms(programDao.getByPace(pace));
    }

    private int getPace(List<Integer> list) {
        System.out.println("-- Paces currently in database --");
        list.forEach(System.out::println);
        int max = list.stream()
                .mapToInt(l -> l)
                .max()
                .orElseThrow(NoSuchElementException::new);
        int min = list.stream()
                .mapToInt(l -> l)
                .min()
                .orElseThrow(NoSuchElementException::new);

        return getUserInput("pace", min, max);
    }

    private void programsByCourse() {
        Optional<Course> course = courseMenu.getCourse();
        course.ifPresentOrElse(
            this::printList,
            () -> System.out.println("No courses found.")
        );
    }

    private void printList(Course course) {
        Print.allPrograms(programDao.getByCourse(course));
    }

    private void progTypeOptions() {
        progTypeMenu.run();
    }

    private Optional<Program> getProgramFromUser() {
        showAll();
        int id = 0;
        if(!programs.isEmpty())
            id = getUserInput("program id", programs.get(0).getId(), programs.get(programs.size() - 1).getId());
        return programDao.getById(id);
    }




    private void refreshPrograms() {
        programs = programDao.getAll();
        progTypes = progTypeDao.getAll();
    }



}