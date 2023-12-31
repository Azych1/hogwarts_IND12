package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty") //localhost:8080/faculty -> Collection<Faculty>
//localhost:8080/faculty/{id} -> Faculty
//localhost:8080/faculty/{id}/name -> String name
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {

        this.facultyService = facultyService;
    }
/////////////////////////////////////////////////////////////////////
    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {

        return facultyService.create(faculty);
    }
///////////////////////////////////////////////////////////////////////
    @GetMapping("/{id}")
    public Faculty read(@PathVariable long id) {

        return facultyService.read(id);
    }
///////////////////////////////////////////////////////////////////////
    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {

        return facultyService.update(faculty);
    }

    /////////////////////////////////////////////////////////////
    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id) {

        return facultyService.delete(id);
    }

    @GetMapping
    public Collection<Faculty> readByColor(@RequestParam String color) {

        return facultyService.getByColor(color);
    }

    @GetMapping("/name_and_color")
    public Collection<Faculty> readAllByNameIgnoreCaseOrColorIgnoreCase(@RequestParam String name, @RequestParam String color) {
        return facultyService.readAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }


}
