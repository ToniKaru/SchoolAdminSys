package se.iths.vimton.defaults;

import se.iths.vimton.entities.*;

import java.util.List;

public class DefaultData {

    List<Language> languages;
    List<ProgramType> programTypes;
    List<Course> courses;
    List<Program> programs;
    List<Teacher> teachers;
    List<Student> students;

    public DefaultData() {
//        this.languages = Default.languages();
//        this.programTypes = programTypes;
//        this.courses = courses;
//        this.programs = programs;
//        this.teachers = teachers;
//        this.students = students;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public List<ProgramType> getProgramTypes() {
        return programTypes;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public String toString() {
        return "DefaultData{" +
               "languages=" + languages +
               ", programTypes=" + programTypes +
               ", courses=" + courses +
               ", programs=" + programs +
               ", teachers=" + teachers +
               ", students=" + students +
               '}';
    }
}
