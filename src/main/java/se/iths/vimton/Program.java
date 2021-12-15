package se.iths.vimton;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int length;

    @OneToMany
    private List<Student> studentList;

    @ManyToOne
    private ProgramType programType;

    //@ManyToMany - course


    public Program() {
    }

    public Program(String name, int length, ProgramType programType) {
        this(name, "", length, programType);
    }

    public Program(String name, String description, int length, ProgramType programType) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.programType = programType;
        this.studentList= new ArrayList<>();
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void addStudents(List<Student> studentList) {
        this.studentList.addAll(studentList);
    }

    public void addStudent(Student student) {
        this.studentList.add(student);
    }

    public ProgramType getProgramType() {
        return programType;
    }

    public void setProgramType(ProgramType programType) {
        this.programType = programType;
    }

    @Override
    public String toString() {
        return "Program{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", length=" + length +
               ", studentList=" + studentList +
               ", programType=" + programType +
               '}';
    }
}
