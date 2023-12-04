package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    @Test
    void create_shouldReturnStudentIfItNotExistYetInRepository() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        Student student1 = new Student("Harry", 10);

        Student result = studentServiceTest.create(student1);

        assertEquals("Harry", result.getName());
        assertEquals(10, result.getAge());
        assertNotNull(result.getId());

        Student readById = studentServiceTest.read(result.getId());
        assertNotNull(readById);
        assertEquals("Harry", readById.getName());
        assertEquals(10, readById.getAge());

    }

    @Test
    void create_shouldReturnExceptionIfStudentExistInRepository() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        Student student1 = new Student("Harry", 10);

        studentServiceTest.create(student1);

        assertThrows(StudentAlreadyExistException.class, () -> studentServiceTest.create(student1));
    }

    @Test
    void read_shouldReturnStudentIfItExistInRepository() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        Student student1 = new Student("Harry", 10);

        Student result = studentServiceTest.create(student1);

        Student readById = studentServiceTest.read(result.getId());
        assertNotNull(readById);
        assertEquals("Harry", readById.getName());
        assertEquals(10, readById.getAge());
    }

    @Test
    void read_shouldReturnExceptionIfStudentNotExistInRepository() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        assertThrows(StudentNotFoundException.class, () -> studentServiceTest.read(1));
    }

    @Test
    void update_shouldUpdateStudentIfItExistInRepository() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        Student student1 = new Student("Harry", 10);

        Student result = studentServiceTest.create(student1);
        assertEquals("Harry", result.getName());
        assertEquals(10, result.getAge());

        result.setName("Ron");
        result.setAge(11);

        studentServiceTest.update(result);

        Student readById = studentServiceTest.read(result.getId());
        assertNotNull(readById);
        assertEquals("Ron", readById.getName());
        assertEquals(11, readById.getAge());
    }

    @Test
    void update_shouldReturnExceptionIfStudentNotExistInRepository() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        Student student1 = new Student("Harry", 10);

        assertThrows(StudentNotFoundException.class, () -> studentServiceTest.update(student1));
    }

    @Test
    void delete_shouldDeleteStudentIfItExist() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        Student student1 = new Student("Harry", 10);

        Student result = studentServiceTest.create(student1);

        Student deletedStudent = studentServiceTest.delete(result.getId());

        assertEquals("Harry", deletedStudent.getName());
        assertEquals(10, deletedStudent.getAge());

        assertThrows(StudentNotFoundException.class, () -> studentServiceTest.read(result.getId()));
    }

    @Test
    void delete_shouldReturnExceptionIfStudentNotExistInRepository() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();

        assertThrows(StudentNotFoundException.class, () -> studentServiceTest.delete(1));
    }

    @Test
    void readByAge_shouldReturnStudentsWithAge() {
        StudentServiceImpl studentServiceTest = new StudentServiceImpl();
        Student student1 = new Student("Harry", 10);
        Student student2 = new Student("Ron", 11);
        Student student3 = new Student("Hermione", 10);

        studentServiceTest.create(student1);
        studentServiceTest.create(student2);
        studentServiceTest.create(student3);

        Collection<Student> result = studentServiceTest.readByAge(10);

        assertEquals(2, result.size());
        assertTrue(result.contains(student1));
        assertTrue(result.contains(student3));

        result = studentServiceTest.readByAge(15);
        assertTrue(result.isEmpty());
    }
}