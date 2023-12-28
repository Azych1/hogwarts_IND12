package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {

    @Test
    void create_shouldReturnFacultyIfItNotExistYetInRepository() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        Faculty faculty1 = new Faculty("Gryffindor", "red");
//
//        Faculty result = facultyServiceTest.create(faculty1);
//
//        assertEquals("Gryffindor", result.getName());
//        assertEquals("red", result.getColor());
//        assertNotNull(result.getId());
//
//        Faculty readById = facultyServiceTest.read(result.getId());
//        assertNotNull(readById);
//        assertEquals("Gryffindor", readById.getName());
//        assertEquals("red", readById.getColor());
    }

    @Test
    void create_shouldReturnExceptionIfFacultyExistInRepository() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        Faculty faculty1 = new Faculty("Gryffindor", "red");
//
//        facultyServiceTest.create(faculty1);
//
//        assertThrows(FacultyAlreadyExistException.class, () -> facultyServiceTest.create(faculty1));
    }

    @Test
    void read_shouldReturnFacultyIfItExistInRepository() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        Faculty faculty1 = new Faculty("Gryffindor", "red");
//
//        Faculty result = facultyServiceTest.create(faculty1);
//
//        Faculty readById = facultyServiceTest.read(result.getId());
//        assertNotNull(readById);
//        assertEquals("Gryffindor", readById.getName());
//        assertEquals("red", readById.getColor());
    }

    @Test
    void read_shouldReturnExceptionIfFacultyNotExistInRepository() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        assertThrows(FacultyNotFoundException.class, () -> facultyServiceTest.read(1));
    }

    @Test
    void update_shouldUpdateFacultyIfItExistInRepository() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        Faculty faculty1 = new Faculty("Gryffindor", "red");
//
//        Faculty result = facultyServiceTest.create(faculty1);
//        assertEquals("Gryffindor", result.getName());
//        assertEquals("red", result.getColor());
//
//        result.setName("Slytherin");
//        result.setColor("green");
//
//        facultyServiceTest.update(result);
//
//        Faculty readById = facultyServiceTest.read(result.getId());
//        assertNotNull(readById);
//        assertEquals("Slytherin", readById.getName());
//        assertEquals("green", readById.getColor());
    }

    @Test
    void update_shouldReturnExceptionIfFacultyNotExistInRepository() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        Faculty faculty1 = new Faculty("Gryffindor", "red");
//
//        assertThrows(FacultyNotFoundException.class, () -> facultyServiceTest.update(faculty1));
    }

    @Test
    void delete_shouldDeleteFacultyIfItExist() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        Faculty faculty1 = new Faculty("Gryffindor", "red");
//
//        Faculty result = facultyServiceTest.create(faculty1);
//
//        Faculty deletedFaculty = facultyServiceTest.delete(result.getId());
//
//        assertEquals("Gryffindor", deletedFaculty.getName());
//        assertEquals("red", deletedFaculty.getColor());
//
//        assertThrows(FacultyNotFoundException.class, () -> facultyServiceTest.read(result.getId()));
    }

    @Test
    void delete_shouldReturnExceptionIfFacultyNotExistInRepository() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//
//        assertThrows(FacultyNotFoundException.class, () -> facultyServiceTest.delete(1));
    }

    @Test
    void getByColor_shouldReturnFaculty_sWithColor() {
//        FacultyServiceImpl facultyServiceTest = new FacultyServiceImpl();
//        Faculty faculty1 = new Faculty("Gryffindor", "yellow");
//        Faculty faculty2 = new Faculty("Slytherin", "green");
//        Faculty faculty3 = new Faculty("Hufflepuff", "yellow");
//
//        facultyServiceTest.create(faculty1);
//        facultyServiceTest.create(faculty2);
//        facultyServiceTest.create(faculty3);
//
//        Collection<Faculty> result = facultyServiceTest.getByColor("yellow");
//
//        assertEquals(2, result.size());
//        assertTrue(result.contains(faculty1));
//        assertTrue(result.contains(faculty3));
//
//        result = facultyServiceTest.getByColor("red");
//        assertTrue(result.isEmpty());
    }
}