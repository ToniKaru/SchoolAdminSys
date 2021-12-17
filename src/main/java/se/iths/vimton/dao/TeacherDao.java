package se.iths.vimton.dao;

import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherDao {

    void create(Teacher teacher);
    void update(Teacher teacher);
    void delete(Teacher teacher);
    Optional<Teacher> getById(int id);
    Optional<Teacher> getBySsn(String ssn);
    List<Teacher> getByName(String name);
    List<Teacher> getAll();
}
