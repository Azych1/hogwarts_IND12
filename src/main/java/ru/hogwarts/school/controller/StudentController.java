package ru.hogwarts.school.controller;


import liquibase.pro.packaged.D;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student") //localhost:8080/student -> Collection<Student>
//localhost:8080/student/{id} -> Student
//localhost:8080/student/{id}/name -> String name
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {

        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping
    public Collection<Student> readByAge(@RequestParam int age) {

        return studentService.readByAge(age);
    }

    @GetMapping("/age")
    public Collection<Student> readByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.readByAgeBetween(minAge, maxAge);
    }

    @GetMapping("/get_student_faculty")
    public Faculty getStudentFaculty(@RequestParam long student_id) {
        return studentService.readStudentFaculty(student_id);
    }

    @GetMapping("/faculty_id")
    public Collection<Student> getAllByFaculty_id(@RequestParam long faculty_id) {
        return studentService.readByFacultyId(faculty_id);
    }

    @GetMapping("/count")
    public Integer getCountOfAllStudents() {
        return studentService.getCountOfAllStudents();
    }

    @GetMapping("/average-age")
    public Double getAverageAgeOfStudents() {
        return studentService.getAverageAgeOfStudents();
    }

    @GetMapping("/last-five")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }

    @GetMapping("/filteredbyname")
    public ResponseEntity<Collection<String>> getAllStudentsWithAName() {
        Collection<String> stringCollection = studentService.getFilteredByName();
        if (stringCollection.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(stringCollection);
    }

    @GetMapping("/getallstudentsavgagewithstream")
    public Double getAllStudentsAvgAgeWithStream() {
        return studentService.getAllStudentsAvgAge();
    }
}
