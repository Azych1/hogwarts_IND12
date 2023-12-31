package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student create(Student student);

    Student read(long id);

    Student update(Student student);

    Student delete(long id);

    Collection<Student> readByAge(int age);

    Collection<Student> readByAgeBetween(int minAge, int maxAge);

    Faculty readStudentFaculty(long studentId);

    Collection<Student> readByFacultyId(long facultyId);

    Integer getCountOfAllStudents();

    Double getAverageAgeOfStudents();

    Collection<Student> getLastFiveStudents();
}
