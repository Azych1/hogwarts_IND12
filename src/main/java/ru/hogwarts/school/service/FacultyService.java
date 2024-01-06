package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(Faculty faculty);

    Faculty delete(long id);

    Collection<Faculty> getByColor(String color);

    Collection<Faculty> readAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    ResponseEntity<String> getFacultyWithMaxLength();
}
