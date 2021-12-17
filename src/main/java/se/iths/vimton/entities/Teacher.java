package se.iths.vimton.entities;

import se.iths.vimton.entities.Course;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String ssn;
    private String phoneNumber;
    private String email;

    @ManyToMany
    private Set<Course> courses;

    public Teacher(){}

    public Teacher(String firstName, String lastName, String ssn, String phoneNumber) {
        this(firstName,lastName,ssn,phoneNumber,"");
    }

    public Teacher(String firstName, String lastName, String ssn, String phoneNumber, String email) {
        Guard.Against.Empty(firstName);
        Guard.Against.Empty(lastName);
        Guard.Against.ssnInvalid(ssn);
        Guard.Against.emailInvalid(email);

        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.courses = new HashSet<>();;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName) &&
               Objects.equals(ssn, teacher.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, ssn);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn=" + ssn +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                '}';
    }
}
