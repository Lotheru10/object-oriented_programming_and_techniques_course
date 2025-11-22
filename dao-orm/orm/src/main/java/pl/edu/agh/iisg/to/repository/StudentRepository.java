package pl.edu.agh.iisg.to.repository;

import pl.edu.agh.iisg.to.dao.CourseDao;
import pl.edu.agh.iisg.to.dao.GradeDao;
import pl.edu.agh.iisg.to.dao.StudentDao;
import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;
import pl.edu.agh.iisg.to.session.TransactionService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


public class StudentRepository implements Repository<Student> {
    private final StudentDao studentDao;
    private final TransactionService transactionService;
    private final CourseDao courseDao;
    private final GradeDao gradeDao;

    public StudentRepository(StudentDao studentDao, TransactionService transactionService, CourseDao courseDao, GradeDao gradeDao) {
        this.studentDao = studentDao;
        this.transactionService = transactionService;
        this.courseDao = courseDao;
        this.gradeDao = gradeDao;
    }

    @Override
    public Optional<Student> add(Student student) {
        return studentDao.save(student);
    }

    @Override
    public Optional<Student> getById(int id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void remove(Student student) {
            transactionService.doAsTransaction(() -> {
                if (student == null) return null;

                var coursesCopy = new HashSet<>(student.courseSet());
                for (Course c : coursesCopy) {
                    c.studentSet().remove(student);
                }
//                student.courseSet().clear();

                studentDao.remove(student);
                return null;
            });
        }
    public List<Student> findAllByCourseName(String courseName) {
        return studentDao.currentSession()
                .createQuery("""
                    select s
                    from Student s
                    join s.courseSet c
                    where c.name = :name
                    order by s.lastName, s.firstName
                """, Student.class)
                .setParameter("name", courseName)
                .getResultList();
    }
    }
