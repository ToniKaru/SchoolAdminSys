package se.iths.vimton.dao;

import se.iths.vimton.entities.Course;
import se.iths.vimton.entities.Student;

import java.util.List;
import java.util.Optional;

public interface CourseDao {

    void create(Course course);
    void update(Course course);
    void delete(Course course);
    Optional<Course> getById(int id);
    List<Course> getByName(String name);
    List<Course> getByCreditRange(int min, int max);
    List<Course> getAll();

}
