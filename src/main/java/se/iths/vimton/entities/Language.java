package se.iths.vimton.entities;

import javax.persistence.*;
import java.util.*;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany
    private Set<Course> courses;

    public Language() {}

    public Language(String name) {
        Guard.Against.Empty(name);

        this.name = name;
        this.courses = new HashSet<>();
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

    public Set<Course> getCourses() {
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
        return Objects.equals(name, language.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Language{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", courses=" + courseNames() +
               '}';
    }

    private List<String> courseNames() {
        return courses.stream().map(Course::getName).toList();
    }
}
