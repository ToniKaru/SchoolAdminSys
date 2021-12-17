package se.iths.vimton.dao;

import se.iths.vimton.entities.Program;
import se.iths.vimton.entities.Course;

import java.util.List;

public interface ProgramDao {

    void create(Program program);
    void update(Program program);
    void delete(Program program);
    void addCourse(Program program, Course course);
    Program getById(int id);
    List<Program> getByName(String name);
    List<Program> getByLength(int length);
    List<Program> getByCourse(Course course);
    //List<Program> getStudents(Program program);
    List<Program> getAll();

}
