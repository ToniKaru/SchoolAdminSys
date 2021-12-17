package se.iths.vimton.dao;

import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Student;
import java.util.List;

public interface StudentDao {

    void create(Student student);
    void update(Student student);
    void delete(Student student);
    Student getById(int id);
    List<Student> getByName(String name);
    List<Student> getByProgram(int programId);
    List<Student> getByProgram(Program program);
    List<Student> getAll();
    int studentsByProgram(Program program);  //todo: refer to StudyLevelDaoImpl

}
