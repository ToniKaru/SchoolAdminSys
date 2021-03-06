package se.iths.vimton.dao;

import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Student;
import java.util.List;
import java.util.Optional;

public interface StudentDao {

    void create(Student student);
    void update(Student student);
    void delete(Student student);
    Optional<Student> getById(int id);
    Optional<Student> getBySsn(String ssn);
    List<Student> getByName(String name);
    List<Student> getByProgram(int programId);
    List<Student> getByProgram(Program program);
    List<Student> getAll();
    Optional<Long> studentsByProgram(Program program);

}
