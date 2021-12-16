package se.iths.vimton.dao;

import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Teacher;

import java.util.List;

public interface TeacherDao {

    void create(Teacher teacher);
    void update(Teacher teacher);
    void delete(Teacher teacher);
    Teacher getById(int id);
    List<Teacher> getByName(String name);
    List<Teacher> getAll();
}
