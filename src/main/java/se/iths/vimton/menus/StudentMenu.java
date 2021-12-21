package se.iths.vimton.menus;

import se.iths.vimton.dao.StudentDao;
import se.iths.vimton.entities.Student;
import se.iths.vimton.impl.StudentDaoImpl;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

import static se.iths.vimton.Menu.getUserInput;

public class StudentMenu {

    StudentDao studentDao;
    List<Student> students;

    public StudentMenu(EntityManagerFactory emf) {
        this.studentDao = new StudentDaoImpl(emf);
        this.students = studentDao.getAll();
    }

    public Optional<Student> getExistingStudentFromUser() {
        int id = getUserInput("student id", 1, students.size());
        Optional<Student> student = studentDao.getById(id);

        if (student.isEmpty()) {
            System.out.println("Course id " + id + " not found.");
            return null;
        }
        return student;
    }

    private void refreshStudents() {
        students = studentDao.getAll();
    }

    private void showAll() {
       //printStudentSummary(studentDao.getAll())
    }
}
