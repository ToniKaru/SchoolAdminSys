package se.iths.vimton.entities;

import se.iths.vimton.entities.ProgramType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyLevel that = (StudyLevel) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(programTypes, that.programTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, programTypes);
    }

    @Override
    public String toString() {
        return "StudyLevel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

}
