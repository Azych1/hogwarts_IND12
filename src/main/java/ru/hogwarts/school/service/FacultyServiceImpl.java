package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty create(Faculty faculty) {

        return repository.save(faculty);

    }
///////////////////////////////////////////////////////////
    @Override
    public Faculty read(long id) {

        return repository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException("Факультет с id " + id + " не найден в хранилище"));

    }

    @Override
    public Faculty update(Faculty faculty) {

        read(faculty.getId());

        return repository.save(faculty);

    }
////////////////////////////////////////////////////////////////
    @Override
    public Faculty delete(long id) {

        Faculty faculty = read(id);

        repository.delete(faculty);

        return faculty;

    }

    @Override
    public Collection<Faculty> getByColor(String color) {

        return repository.findAllByColor(color);
    }

    @Override
    public Collection<Faculty> readAllByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        return repository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }



}
