package pl.edu.agh.to.school.student;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.grade.GradeService;

@Service
public class StudentService {

    private final GradeService gradeService;

    public StudentService(GradeService gradeService){
        this.gradeService = gradeService;
        System.out.println("StudentService was created");
    }

    @PostConstruct
    public void onStart(){
        System.out.println("StudentService is up");
    }

    @PreDestroy
    public void onClose(){
        System.out.println("StudentService is down");
    }
}


