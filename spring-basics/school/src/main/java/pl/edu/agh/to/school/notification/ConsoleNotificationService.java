package pl.edu.agh.to.school.notification;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.student.Student;

@Service
public class ConsoleNotificationService implements NotificationService {

    @PostConstruct
    public void onStart(){
        System.out.println("ConsoleNotificationService is up");
    }

    @PreDestroy
    public void onClose(){
        System.out.println("ConsoleNotificationService is down");
    }

    @Override
    public void notify(Student student, Grade grade) {
        System.out.println(student.getFullName()+" otrzymał "+grade.value());
    }
}
