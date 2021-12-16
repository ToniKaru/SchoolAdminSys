package se.iths.vimton.entities;


import se.iths.vimton.entities.Program;

import javax.persistence.*;
import java.io.BufferedReader;
import java.util.Objects;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String enrollmentDate;

    @ManyToOne
    private Program program;

    public Student() {
    }

    public Student(String firstName, String lastName, String birthDate, String email, String enrollmentDate,
                   Program program) {
        Guard.Against.Empty(firstName);
        Guard.Against.Empty(lastName);
        Guard.Against.dateInvalid(birthDate);
        Guard.Against.emailInvalid(email);
        Guard.Against.dateInvalid(enrollmentDate);
        Guard.Against.Null(program);

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
        this.program = program;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(firstName, student.firstName) && Objects.equals(lastName,
                student.lastName) && Objects.equals(birthDate, student.birthDate) && Objects.equals(email,
                student.email) && Objects.equals(enrollmentDate, student.enrollmentDate) && Objects.equals(program,
                student.program);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, email, enrollmentDate, program);
    }

    @Override
    public String toString() {
        return "Student{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthDate='" + birthDate + '\'' +
               ", email='" + email + '\'' +
               ", enrollmentDate='" + enrollmentDate + '\'' +
               ", program=" + program +
               '}';
    }
}





