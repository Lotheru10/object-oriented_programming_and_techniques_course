package pl.edu.agh.to.school.student;

import java.time.LocalDate;
import java.util.Objects;

public final class Student {
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
    private final String indexNumber;
    private final String email;

    public Student(
            String firstName,
            String lastName,
            LocalDate birthDate,
            String indexNumber,
            String email
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.indexNumber = indexNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(indexNumber, student.indexNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexNumber);
    }

    @Override
    public String toString() {
        return "Student[" +
                "fullName=" + getFullName() + ", " +
                "indexNumber=" + getIndexNumber() + ']';
    }
}
