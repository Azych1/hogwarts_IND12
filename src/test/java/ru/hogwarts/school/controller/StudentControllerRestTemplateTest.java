package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    StudentRepository studentRepository;

    String baseUrl;

    Student student = new Student(1L, "Harry", 10);

    @BeforeEach
    void beforeEach() {
        baseUrl = "http://localhost:" + port + "/student";
        studentRepository.deleteAll();
    }

    @Test
    void create_shouldReturnStudentAndStatus200() {
        ResponseEntity<Student> result = restTemplate.postForEntity(
                baseUrl,
                student,
                Student.class
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(student, result.getBody());

    }

    @Test
    void read_shouldReturnStatus404() {
        ResponseEntity<String> result = restTemplate.getForEntity(
                baseUrl + "/" + student.getId(),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Студент с id " + student.getId() + " не найден в хранилище",
                result.getBody());

    }

    @Test
    void update_shouldReturnStudentAndStatus200() {
        Student saveStudent = studentRepository.save(student);

        ResponseEntity<Student> result = restTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(saveStudent),
                Student.class
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveStudent, result.getBody());
    }

    @Test
    void delete_shouldReturnStudentAndStatus200() {
        Student saveStudent1 = studentRepository.save(student);

        ResponseEntity<Student> result = restTemplate.exchange(
                baseUrl+ "/" + saveStudent1.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(saveStudent1.getId()),
                Student.class
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveStudent1, result.getBody());
    }

    @Test
    void readByAge_shouldReturnStudentsCollectionAndStatus200() {
        Student ron10 = new Student(10L, "Ron", 10);
        Student ron20 = new Student(20L, "Ron", 10);
        Student saveStudent2 = studentRepository.save(ron10);
        Student saveStudent3 = studentRepository.save(ron20);

        ResponseEntity<List<Student>> result = restTemplate.exchange(
                baseUrl + "?age=" + saveStudent2.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(saveStudent2, saveStudent3), result.getBody());
    }

    @Test
    void readByAgeBetween_shouldReturnStudentsCollectionAndStatus200() {
        Student ron30 = new Student(30L, "Ron", 10);
        Student ron40 = new Student(40L, "Ron", 10);
        Student saveStudent4 = studentRepository.save(ron30);
        Student saveStudent5 = studentRepository.save(ron40);

        ResponseEntity<List<Student>> result = restTemplate.exchange(
                baseUrl + "/age?minAge=" + saveStudent4.getAge()+"&maxAge="+saveStudent5.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(saveStudent4, saveStudent5), result.getBody());
    }
}
