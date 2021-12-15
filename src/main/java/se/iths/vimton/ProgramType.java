package se.iths.vimton;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProgramType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int creditsRequired;
    private boolean accredited;
    private StudyLevel studyLevel;

    @OneToMany
    List<Program> programList;

    public ProgramType() {
    }

    public ProgramType(int creditsRequired, boolean accredited, StudyLevel studyLevel) {
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
        return programList;
    }

    public void addProgram(List<Program> programList) {
        this.programList.addAll(programList);
    }

    public void addProgram(Program program) {
        this.programList.add(program);
    }

    @Override
    public String toString() {
        return "ProgramType{" +
               "id=" + id +
               ", creditsRequired=" + creditsRequired +
               ", accredited=" + accredited +
               ", studyLevel=" + studyLevel +
               ", programList=" + programList +
               '}';
    }
}
