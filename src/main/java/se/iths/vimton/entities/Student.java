package se.iths.vimton.entities;


import se.iths.vimton.entities.Program;

import javax.persistence.*;
import java.io.BufferedReader;

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





