package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = Logger.getLogger(StudentServiceImpl.class.toString());

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Was invoked method to create student and return it");

        return repository.save(student);
    }

    @Override
    public Student read(long id) {
        logger.info("Was invoked method to find student and return it");

        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент с id " + id + " не найден в хранилище"));
    }

    @Override
    public Student update(Student student) {
        logger.info("Was invoked method to update student and return saved");

        read(student.getId());

        return repository.save(student);
    }

    @Override
    public Student delete(long id) {
        logger.info("Was invoked method to delete student and return deleted");

        Student student = read(id);

        repository.delete(student);

        return student;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        logger.info("Was invoked method to find student by {age}");
        return repository.findAllByAge(age);
    }

    @Override
    public Collection<Student> readByAgeBetween(int minAge, int maxAge) {
        logger.info("Was invoked method to find students between {minAge} and {maxAge} and return as Collection");
        return repository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty readStudentFaculty(long studentId) {
        logger.info("Was invoked method to find faculty by studentId");
        return read(studentId).getFaculty();
    }

    @Override
    public Collection<Student> readByFacultyId(long facultyId) {
        logger.info("Was invoked method to find all faculties by facultyId and return as Collection");
        return repository.findAllByFaculty_id(facultyId);
    }

    @Override
    public Integer getCountOfAllStudents() {
        logger.info("Was invoked method to get count of all students in repository");
        return repository.getCountOfAllStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        logger.info("Was invoked method to get average of all students in repository");
        return repository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method to get last five students in repository, filtered by name before");
        return repository.getLastFiveStudents();
    }

    public Collection<String> getFilteredByName() {
        logger.info("Was invoked method to find all students filtered by name to String Collection");
        return repository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAllStudentsAvgAge() {
        logger.info("Was invoked method to find average of ages of all students in repository");
        return repository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }


}
