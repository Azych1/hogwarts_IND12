package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    StudentRepository repository;

    @InjectMocks
    StudentServiceImpl service;

    Student student = new Student("Harry", 11);


    @Test
    void create() { //_shouldReturnStudentIfItNotExistYetInRepository

        when(repository.save(student)).thenReturn(student);

        Student result = service.create(student);

        assertEquals(student, result);
    }

    @Test
    void read_shouldReturnStudent() {

        when(repository.findById(student.getId())).thenReturn(Optional.of(student));

        Student result = service.read(student.getId());

        assertEquals(student, result);
    }

    @Test
    void read_shouldThrowExceptionWhenStudentNotInDB() {

        when(repository.findById(student.getId())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> service.read(student.getId()));
    }

    @Test
    void update_shouldUpdateStudentIfItExistInRepository() {
//        StudentServiceImpl studentServiceTest = new StudentServiceImpl();
//
//        Student student1 = new Student("Harry", 10);
//
//        Student result = studentServiceTest.create(student1);
//        assertEquals("Harry", result.getName());
//        assertEquals(10, result.getAge());
//
//        result.setName("Ron");
//        result.setAge(11);
//
//        studentServiceTest.update(result);
//
//        Student readById = studentServiceTest.read(result.getId());
//        assertNotNull(readById);
//        assertEquals("Ron", readById.getName());
//        assertEquals(11, readById.getAge());
    }

    @Test
    void update_shouldReturnExceptionIfStudentNotExistInRepository() {
//        StudentServiceImpl studentServiceTest = new StudentServiceImpl();
//
//        Student student1 = new Student("Harry", 10);
//
//        assertThrows(StudentNotFoundException.class, () -> studentServiceTest.update(student1));
    }

    @Test
    void delete_shouldDeleteStudentIfItExist() {
//        StudentServiceImpl studentServiceTest = new StudentServiceImpl();
//
//        Student student1 = new Student("Harry", 10);
//
//        Student result = studentServiceTest.create(student1);
//
//        Student deletedStudent = studentServiceTest.delete(result.getId());
//
//        assertEquals("Harry", deletedStudent.getName());
//        assertEquals(10, deletedStudent.getAge());
//
//        assertThrows(StudentNotFoundException.class, () -> studentServiceTest.read(result.getId()));
    }

    @Test
    void delete_shouldReturnExceptionIfStudentNotExistInRepository() {
//        StudentServiceImpl studentServiceTest = new StudentServiceImpl();
//
//        assertThrows(StudentNotFoundException.class, () -> studentServiceTest.delete(1));
    }

    @Test
    void readByAge_shouldReturnStudentsWithAge() {
//        StudentServiceImpl studentServiceTest = new StudentServiceImpl();
//        Student student1 = new Student("Harry", 10);
//        Student student2 = new Student("Ron", 11);
//        Student student3 = new Student("Hermione", 10);
//
//        studentServiceTest.create(student1);
//        studentServiceTest.create(student2);
//        studentServiceTest.create(student3);
//
//        Collection<Student> result = studentServiceTest.readByAge(10);
//
//        assertEquals(2, result.size());
//        assertTrue(result.contains(student1));
//        assertTrue(result.contains(student3));
//
//        result = studentServiceTest.readByAge(15);
//        assertTrue(result.isEmpty());
    }
}