package pl.edu.agh.to.school.course;

import pl.edu.agh.to.school.student.Student;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private final String name;

    private final List<Student> students = new ArrayList<>();

    public Course(String name) {
        this.name = name;
    }

    public void enrollStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Course [" +
                "name=" + name + ']';
    }
}
