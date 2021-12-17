package se.iths.vimton.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class ProgramType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int creditsRequired;
    private boolean accredited;

    @OneToMany
    List<Program> programs;

    public ProgramType() {
    }

    public ProgramType(String name, int creditsRequired, boolean accredited) {
        Guard.Against.zeroOrLess(creditsRequired);
        Guard.Against.Empty(name);

        this.name = name;
        this.creditsRequired = creditsRequired;
        this.accredited = accredited;

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

    public int getCreditsRequired() {
        return creditsRequired;
    }

    public void setCreditsRequired(int creditsRequired) {
        this.creditsRequired = creditsRequired;
    }

    public boolean isAccredited() {
        return accredited;
    }

    public void setAccredited(boolean accredited) {
        this.accredited = accredited;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    public List<Program> getProgramList() {
        return programs;
    }

    public void addProgram(List<Program> programList) {
        this.programs.addAll(programList);
    }

    public void addProgram(Program program) {
        this.programs.add(program);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgramType that = (ProgramType) o;
        return creditsRequired == that.creditsRequired && accredited == that.accredited && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creditsRequired, accredited);
    }

    @Override
    public String toString() {
        return "ProgramType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creditsRequired=" + creditsRequired +
                ", accredited=" + accredited +
                '}';
    }
}
