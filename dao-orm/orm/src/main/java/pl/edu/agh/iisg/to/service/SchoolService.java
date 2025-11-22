package pl.edu.agh.iisg.to.service;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.repository.StudentRepository;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.*;

public class SchoolService {

    private final TransactionService transactionService;

    private final StudentDao studentDao;

    private final CourseDao courseDao;

    private final GradeDao gradeDao;

    private final StudentRepository studentRepository;

    public SchoolService(TransactionService transactionService, StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao, StudentRepository studentRepository) {
        this.transactionService = transactionService;
        this.studentDao = studentDao;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
        this.studentRepository = studentRepository;
    }

    public boolean enrollStudent(final Course course, final Student student) {
        //dodaj do kursow studenta i na odwrot i tyle, if jak juz jest. Jest to ciałem lambdy transakcji
        //kazdy kolejny przykład w takim formacie
        if (course.studentSet().contains(student)) {
            return false;
        }
        try {
            return transactionService.doAsTransaction(() -> {
                course.studentSet().add(student);
                student.courseSet().add(course);

                courseDao.save(course);
                studentDao.save(student);

                return true;
            }).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removeStudent(int indexNumber) {
        try {
            return transactionService.doAsTransaction(() -> {
                var optionalStudent = studentDao.findByIndexNumber(indexNumber);
                if(optionalStudent.isEmpty()){
                    return false;
                }
                studentRepository.remove(optionalStudent.get());
                return true;
            }).orElse(false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean gradeStudent(final Student student, final Course course, final float gradeValue) {
        try {
            return transactionService.doAsTransaction(() -> {
                Grade grade = new Grade(student, course, gradeValue);
                course.gradeSet().add(grade);
                student.gradeSet().add(grade);

                courseDao.save(course);
                studentDao.save(student);
                gradeDao.save(grade);

                return true;
            }).orElse(false);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, List<Float>> getStudentGrades(String courseName) {
        try {
            return transactionService.<Map<String, List<Float>>>doAsTransaction(() -> {
                var optionalCourse = courseDao.findByName(courseName);
                if(optionalCourse.isEmpty()){
                    return Collections.emptyMap();
                }
                Course course = optionalCourse.get();

                Map<String, List<Float>> result = new HashMap<>();
                for(Student student : course.studentSet()) {
                    List<Float> grades = student.gradeSet().stream()
                            .filter(g -> g.course().equals(course))
                            .map(Grade::grade)
                            .sorted()
                            .toList();
                    result.put(student.fullName(), grades);
                }
                return result;
            }).orElse(Collections.emptyMap());
        }
        catch (Exception e){
            e.printStackTrace();
            return Collections.emptyMap();

        }
    }
}
