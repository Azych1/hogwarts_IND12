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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @MockBean
    FacultyRepository facultyRepository;

    @SpyBean
    FacultyServiceImpl facultyService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Faculty faculty = new Faculty(1L, "Gryffindor", "red");

    @Test
    void create_shouldReturnFacultyAndStatus200() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty));
    }

    @Test
    void read_shouldReturnStatus404() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Факультет с id " + faculty.getId() + " не найден в хранилище"));
    }

    @Test
    void update_shouldReturnFacultyAndStatus200() throws Exception {
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty));
    }

    @Test
    void delete_shouldReturnStatus404() throws Exception {
        when(facultyRepository.findById(faculty.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/faculty/" + faculty.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Факультет с id " + faculty.getId() + " не найден в хранилище"));
    }

    @Test
    void readByColor_shouldReturnFacultiesCollectionAndStatus200() throws Exception {
        Faculty faculty1 = new Faculty(2L, "MockGryffindor", "red");

        when(facultyRepository.findAllByColor(faculty.getColor())).thenReturn(List.of(faculty, faculty1));

        mockMvc.perform(get("/faculty?color=" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(faculty))
                .andExpect(jsonPath("$.[1]").value(faculty1));

    }

    @Test
    void readAllByNameIgnoreCaseOrColorIgnoreCase_shouldReturnFacultiesCollectionAndStatus200() throws Exception {
        Faculty faculty1 = new Faculty(2L, "Gryffindor1", "red");

        when(facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(faculty.getName(), faculty.getColor())).thenReturn(List.of(faculty, faculty1));

        mockMvc.perform(get("/faculty/name_and_color?name="+faculty.getName()+"&color="+faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(faculty))
                .andExpect(jsonPath("$.[1]").value(faculty1));
    }

}
