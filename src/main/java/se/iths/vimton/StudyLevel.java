package se.iths.vimton;

import javax.persistence.*;
import java.util.List;

@Entity
public class StudyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany
    List<ProgramType> programTypes;

    public StudyLevel() {
    }

    public StudyLevel(String name) {
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

    public List<ProgramType> getProgramTypes() {
        return programTypes;
    }

    public void setProgramTypes(List<ProgramType> programTypes) {
        this.programTypes = programTypes;
    }

    public void addProgramType(ProgramType programType) {
        this.programTypes.add((programType));
    }

    public void addProgramTypes(List<ProgramType> programTypes) {
        this.programTypes.addAll(programTypes);
    }

    @Override
    public String toString() {
        return "StudyLevel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

}
