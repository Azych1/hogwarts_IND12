package ru.hogwarts.school.controller;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    FacultyRepository facultyRepository;

    String baseUrl;

    Faculty faculty = new Faculty(1L, "Gryffindor", "red");

    @BeforeEach
    void beforeEach() {
        baseUrl = "http://localhost:" + port + "/faculty";
        facultyRepository.deleteAll();
    }

    @Test
    void create_shouldReturnFacultyAndStatus200() {

        ResponseEntity<Faculty> result = restTemplate.postForEntity(
                baseUrl,
                faculty,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(faculty, result.getBody());


    }

    @Test
    void read_shouldReturnStatus404() {
        ResponseEntity<String> result = restTemplate.getForEntity(
                baseUrl + "/" + faculty.getId(),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Факультет с id " + faculty.getId() + " не найден в хранилище",
                result.getBody());
    }

    @Test
    void update_shouldReturnFacultyAndStatus200() {
        Faculty saveFaculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> result = restTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(saveFaculty),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveFaculty, result.getBody());
    }

    @Test
    void delete_shouldReturnFacultyAndStatus200() {
        Faculty saveFaculty1 = facultyRepository.save(faculty);

        ResponseEntity<Faculty> result = restTemplate.exchange(
                baseUrl+ "/" + saveFaculty1.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(saveFaculty1),
                Faculty.class
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveFaculty1, result.getBody());
    }

    @Test
    void readByColor_shouldReturnFacultiesCollectionAndStatus200() {
        Faculty faculty10 = new Faculty(10L, "Gryffindor10", "red");
        Faculty faculty20 = new Faculty(20L, "Gryffindor20", "red");
        Faculty saveFaculty10 = facultyRepository.save(faculty10);
        Faculty saveFaculty20 = facultyRepository.save(faculty20);

        ResponseEntity<List<Faculty>> result = restTemplate.exchange(
                baseUrl + "?color=" + faculty10.getColor(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(faculty10, faculty20), result.getBody());
    }

    @Test
    void readAllByNameIgnoreCaseOrColorIgnoreCase_shouldReturnFacultiesCollectionAndStatus200() {
        Faculty faculty30 = new Faculty(30L, "Gryffindor30", "red");
        Faculty faculty40 = new Faculty(40L, "Gryffindor40", "red");
        Faculty saveFaculty30 = facultyRepository.save(faculty30);
        Faculty saveFaculty40 = facultyRepository.save(faculty40);

        ResponseEntity<List<Faculty>> result = restTemplate.exchange(
                baseUrl + "/name_and_color?name=" + faculty30.getName()+"&color="+faculty30.getColor(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(faculty30, faculty40), result.getBody());
    }


}
