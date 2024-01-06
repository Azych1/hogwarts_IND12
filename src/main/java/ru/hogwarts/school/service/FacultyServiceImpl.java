package ru.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Logger logger = Logger.getLogger(FacultyServiceImpl.class.toString());

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Was invoked method to create faculty and return it");

        return repository.save(faculty);

    }
///////////////////////////////////////////////////////////
    @Override
    public Faculty read(long id) {
        logger.info("Was invoked method to find faculty and return it");

        return repository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException("Факультет с id " + id + " не найден в хранилище"));

    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Was invoked method to update faculty and return saved");

        read(faculty.getId());

        return repository.save(faculty);

    }
////////////////////////////////////////////////////////////////
    @Override
    public Faculty delete(long id) {
        logger.info("Was invoked method to delete faculty and return deleted");

        Faculty faculty = read(id);

        repository.delete(faculty);

        return faculty;

    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        logger.info("Was invoked method to find faculty by {color}");

        return repository.findAllByColor(color);
    }

    @Override
    public Collection<Faculty> readAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method to find all faculties by name and color and return as Collection");
        return repository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public ResponseEntity<String> getFacultyWithMaxLength() {
        logger.info("Was invoked method to find faculty name with max length");

        Optional<String> maxFacultyName = repository
                .findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparing(String::length));

        if (maxFacultyName.isEmpty()) {
            logger.info("There is no faculties at all");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Faculty name with max length: {}");
            return ResponseEntity.ok(maxFacultyName.get());
        }
    }



}
