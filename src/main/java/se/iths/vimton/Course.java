package se.iths.vimton;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private int credits;
    private String language;

    @ManyToOne
    private Program program;

    @ManyToMany(mappedBy = "courses")
    private Set<Teacher> teachers;


    public Course(){}

    public Course(String name, int credits, String language, Program program) {
        this(name,"",credits,language,program);
    }

    public Course(String name, String description, int credits, String language, Program program) {
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.language = language;
        this.program = program;
        this.teachers = new HashSet<>();
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", credits=" + credits +
                ", language='" + language + '\'' +
                ", program=" + program +
                '}';
    }
}
