package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @MockBean
    StudentRepository studentRepository;

    @SpyBean
    StudentServiceImpl studentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Student student = new Student(1L, "Player", 14);

    @Test
    void create_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(post("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(student));
    }

    @Test
    void read_shouldReturnStatus404() throws Exception {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Студент с id " + student.getId() + " не найден в хранилище"));

    }

    @Test
    void update_shouldReturnStudentAndStatus200() throws Exception {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(put("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(student));
    }

    @Test
    void delete_shouldReturnStatus404() throws Exception {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/student/" + student.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Студент с id " + student.getId() + " не найден в хранилище"));
    }

    @Test
    void readByAge_shouldReturnStudentsCollectionAndStatus200() throws Exception {
        Student student1 = new Student(2L, "Player1", 14);

        when(studentRepository.findAllByAge(student.getAge())).thenReturn(List.of(student, student1));

        mockMvc.perform(get("/student?age="+student.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(student))
                .andExpect(jsonPath("$.[1]").value(student1));


    }

    @Test
    void readByAgeBetween_shouldReturnStudentsCollectionAndStatus200() throws Exception {
        Student student1 = new Student(2L, "Player1", 15);

        when(studentRepository.findByAgeBetween(student.getAge(), student1.getAge())).thenReturn(List.of(student, student1));

        mockMvc.perform(get("/student/age?minAge="+student.getAge()+"&maxAge="+student1.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(student))
                .andExpect(jsonPath("$.[1]").value(student1));
    }

//    @Test
//    void findAllByFaculty_id_shouldReturnStudentsCollectionAndStatus200() throws Exception {
//
//        Student student1 = new Student(2L, "Player1", 15);
//        Student student2 = new Student(2L, "Player1", 15);
//
//        when(studentRepository.findAllByFaculty_id(student.getAge())).thenReturn(List.of(student, student1));
//
//        mockMvc.perform(get("/student?age="+student.getAge()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.[0]").value(student))
//                .andExpect(jsonPath("$.[1]").value(student1));
//    }

}
