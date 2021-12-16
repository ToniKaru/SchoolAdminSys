package se.iths.vimton.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany
    private List<Course> courses;

    public Language() {}

    public Language(String name) {
        Guard.Against.Empty(name);

        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourses(List<Course> courseList) {
        this.courses.addAll(courseList);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", language='" + name + '\'' +
                '}';
    }
}
