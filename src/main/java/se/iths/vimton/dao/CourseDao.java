package se.iths.vimton.dao;

import se.iths.vimton.entities.Course;

import java.util.List;

public interface CourseDao {

    void create(Course course);
    void update(Course course);
    void delete(Course course);
    Course getById(int id);
    List<Course> getByName(String name);
    List<Course> getByCreditRange(int min, int max);
    List<Course> getStudents(Course course);
    List<Course> getAll();

}