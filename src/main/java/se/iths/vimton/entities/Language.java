package se.iths.vimton.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        this.courses = new ArrayList<>();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return id == language.id && Objects.equals(name, language.name) && Objects.equals(courses, language.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, courses);
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", language='" + name + '\'' +
                '}';
    }
}
