package se.iths.vimton.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int credits;

    @ManyToOne
    private Language language;

    @ManyToMany(mappedBy = "teacherCourses")
    private Set<Teacher> teachers;

    @ManyToMany(mappedBy = "courses")
    private Set<Program> programs;

    public Course(){}

    public Course(String name, int credits, Language language) {
        this(name,"",credits,language);
    }

    public Course(String name, String description, int credits, Language language) {
        Guard.Against.zeroOrLess(credits);
        Guard.Against.Empty(name);

        this.name = name;
        this.description = description;
        this.credits = credits;
        this.language = language;
        this.teachers = new HashSet<>();
        this.programs = new HashSet<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
        teachers.forEach(teacher -> teacher.getTeacherCourses().add(this));
    }

    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.getTeacherCourses().add(this);
    }

    public void addTeachers(List<Teacher> teachers) {
        this.teachers.addAll(teachers);
        teachers.forEach(teacher -> teacher.getTeacherCourses().add(this));
    }

    public void removeTeacher(Teacher teacher){
        this.teachers.remove(teacher);
        teacher.getTeacherCourses().remove(teacher);
    }

    public Set<Program> getPrograms() {
        return this.programs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && credits == course.credits && Objects.equals(name, course.name) &&
               Objects.equals(description, course.description) && Objects.equals(language, course.language) &&
               Objects.equals(teachers, course.teachers) && Objects.equals(programs, course.programs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, credits, language, teachers, programs);
    }

    @Override
    public String toString() {
        return "Course{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", credits=" + credits +
               ", language=" + language +
               ", teachers=" + teachers +
               ", programs=" + programs +
               '}';
    }
}
