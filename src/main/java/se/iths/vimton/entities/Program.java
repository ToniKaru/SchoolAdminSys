package se.iths.vimton.entities;

import javax.persistence.*;
import java.util.*;


@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int pace;

    @ManyToOne
    private ProgramType programType;

    @OneToMany
    private List<Student> students;

    @ManyToMany
    private Set<Course> courses;

    public Program() {
    }

    public Program(String name, int pace, ProgramType programType) {
        this(name, "", pace, programType);
    }

    public Program(String name, String description, int pace, ProgramType programType) {
        Guard.Against.Empty(name);
        Guard.Against.zeroOrLess(pace);
        Guard.Against.Null(programType);

        this.name = name;
        this.description = description;
        this.pace = pace;
        this.programType = programType;
        this.students= new ArrayList<>();
        this.courses = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPace() {
        return pace;
    }

    public void setPace(int pace) {
        this.pace = pace;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudents(List<Student> studentList) {
        this.students.addAll(studentList);
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public ProgramType getProgramType() {
        return programType;
    }

    public void setProgramType(ProgramType programType) {
        this.programType = programType;
    }


    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
        courses.forEach(course -> course.getPrograms().add(this));
    }

    public void addCourse(Course course) {
        this.courses.add(course);
        course.getPrograms().add(this);
    }

    public void addAllCourses(List<Course> courses){
        this.courses.addAll(courses);
        courses.forEach(course -> course.getPrograms().add(this));
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getPrograms().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return pace == program.pace && Objects.equals(name, program.name) && Objects.equals(description, program.description) && Objects.equals(programType, program.programType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, pace, programType);
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pace=" + pace +
                ", programType=" + programType +
                '}';
    }
}
