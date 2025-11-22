package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.student.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GradeBook {
    private final Map<String, List<Grade>> studentGrades = new HashMap<>();

    public Grade assignGrade(Student student, Course course, double gradeValue){
        Grade grade = new Grade(course, gradeValue);
        List<Grade> grades = studentGrades.get(student.getIndexNumber());
        grades.add(grade);
        studentGrades.put(student.getIndexNumber(), grades);
        return grade;
    }
    @PostConstruct
    public void onStart(){
        System.out.println("GradeBook is up");
    }

    @PreDestroy
    public void onClose(){
        System.out.println("GradeBook is down");
    }
}
