package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.hogwarts.school.model.Student;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentServiceImpl underTest; //new StudentServiceImpl(studentService)

    private Student student1 = new Student(1L, "Harry", 10);
    private Student student2 = new Student(2L, "Hermiona", 11);

    @Test
    void create_shouldReturnStudentIfItNotExistYetInRepository() {

        when(studentService.create(student1)).thenReturn(student1);

        Student result = studentService.create(student1);

        assertEquals(studentService.create(student1), result);
    }

    @Test
    void read() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void readByAge() {
    }
}