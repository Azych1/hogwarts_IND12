package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student create(Student student) {

        return repository.save(student);
    }

    @Override
    public Student read(long id) {

        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Студент с id " + id + " не найден в хранилище"));
    }

    @Override
    public Student update(Student student) {

        read(student.getId());

        return repository.save(student);
    }

    @Override
    public Student delete(long id) {

        Student student = read(id);

        repository.delete(student);

        return student;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        return repository.findAllByAge(age);
    }

    @Override
    public Collection<Student> readByAgeBetween(int minAge, int maxAge) {
        return repository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty readStudentFaculty(long studentId) {
        return read(studentId).getFaculty();
    }

    @Override
    public Collection<Student> readByFacultyId(long facultyId) {
        return repository.findAllByFaculty_id(facultyId);
    }

    @Override
    public Integer getCountOfAllStudents() {
        return repository.getCountOfAllStudents();
    }

    @Override
    public Double getAverageAgeOfStudents() {
        return repository.getAverageAgeOfStudents();
    }

    @Override
    public Collection<Student> getLastFiveStudents() {
        return repository.getLastFiveStudents();
    }


}
