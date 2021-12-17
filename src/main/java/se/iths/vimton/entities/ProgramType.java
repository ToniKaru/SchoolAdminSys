package se.iths.vimton.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class ProgramType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int creditsRequired;
    private boolean accredited;

    @ManyToOne
    StudyLevel studyLevel;

    @OneToMany
    List<Program> programs;

    public ProgramType() {
    }

    public ProgramType(int creditsRequired, boolean accredited, StudyLevel studyLevel) {
        Guard.Against.zeroOrLess(creditsRequired);
        Guard.Against.Null(studyLevel);

        this.creditsRequired = creditsRequired;
        this.accredited = accredited;
        this.studyLevel = studyLevel;
    }

    public int getId() {
        return id;
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
        return creditsRequired == that.creditsRequired && accredited == that.accredited &&
               Objects.equals(studyLevel, that.studyLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creditsRequired, accredited, studyLevel);
    }

    @Override
    public String toString() {
        return "ProgramType{" +
               "id=" + id +
               ", creditsRequired=" + creditsRequired +
               ", accredited=" + accredited +
               ", studyLevel=" + studyLevel +
               '}';
    }
}
