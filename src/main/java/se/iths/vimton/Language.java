package se.iths.vimton;

import javax.persistence.*;
import java.util.List;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String language;

    @OneToMany
    private List<Course> courses;

    public Language() {}

    public Language(String language) {
        this.language = language;
    }


    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
                ", language='" + language + '\'' +
                '}';
    }
}
