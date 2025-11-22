package pl.edu.agh.to.school.grade;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.notification.NotificationService;
import pl.edu.agh.to.school.student.Student;

@Service
public class GradeService {
    private final GradeBook gradeBook;
    private final NotificationService notificationService;

    public GradeService(GradeBook gradeBook, NotificationService notificationService) {
        this.gradeBook = gradeBook;
        this.notificationService = notificationService;
    }

    public void assignGrade(Student student, Course course, double gradeValue){
        gradeBook.assignGrade(student, course, gradeValue);
        notificationService.notify(student, course);
    }

    @PostConstruct
    public void onStart(){
        System.out.println("GradeService is up");
    }
    @PreDestroy
    public void onEnd(){
        System.out.println("GradeService is down");
    }
}
