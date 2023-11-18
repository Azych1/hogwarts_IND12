package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> repository = new HashMap<>();

    private Long idCounter = 0L;

    @Override
    public Faculty create(Faculty faculty) {

        if (repository.containsValue(faculty)) {
            throw new FacultyAlreadyExistException("Факультет " + faculty + " уже есть в хранилище");
        }

        long id = ++idCounter;
        faculty.setId(id);

        repository.put(id, faculty);

        return faculty;
    }

    @Override
    public Faculty read(long id) {

        Faculty faculty = repository.get(id);

        if (faculty == null) {
            throw new FacultyNotFoundException("Факультет с id " + id + " не найден в хранилище");
        }

        return faculty;

    }

    @Override
    public Faculty update(Faculty faculty) {

        if (!repository.containsKey(faculty.getId())) {
            throw new FacultyNotFoundException("Факультет с id " + faculty.getId() + " не найден в хранилище");
        }

        repository.put(faculty.getId(), faculty);
        return faculty;

    }

    @Override
    public Faculty delete(long id) {
        Faculty faculty = repository.remove(id);

        if (faculty == null) {
            throw new FacultyNotFoundException("Факультет с id " + id + " не найден в хранилище");
        }

        return faculty;
    }

    @Override
    public Collection<Faculty> getByColor(String color) {
        return repository.values().stream()
                .filter(f -> Objects.equals(f.getColor(), color))
                .collect(Collectors.toUnmodifiableList());
    }



}
