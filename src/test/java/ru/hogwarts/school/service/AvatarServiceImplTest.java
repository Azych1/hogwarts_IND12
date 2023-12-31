package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AvatarServiceImplTest {

    StudentService studentService = mock(StudentService.class);

    AvatarRepository avatarRepository = mock(AvatarRepository.class);

    String avatarsDir = "./src/test/resources/avatar";

    AvatarServiceImpl avatarService = new AvatarServiceImpl(
            studentService, avatarRepository, avatarsDir);

    Student student = new Student(1L,"Harry", 10);

    @Test
    void uploadAvatar__avatarSavedToDbAndDirectory() throws IOException {
        String fileName = "9.pdf";
        MultipartFile file = new MockMultipartFile(
                fileName, fileName, "application/pdf", new byte[]{});
        when(studentService.read(student.getId())).thenReturn(student);
        when(avatarRepository.findByStudent_id(student.getId()))
                .thenReturn(Optional.empty());

        avatarService.uploadAvatar(student.getId(), file);

        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarsDir+"/"+student.getId()+
                "."+fileName.substring(fileName.lastIndexOf(".") + 1))));


    }

    @Test
    void readFromDB() throws IOException{
        //2 теста
        //1)если нашли, то будет when avatarRepository.findById.thenReturn(Optional.of(avatar)
        //либо вернуть Optional.empty
    }

    @Test
    void readFromFile() throws IOException {
        //нужно создать объект класса File, где будет avatar.getFilePath (когда будем возвращать аватар, передать объекту аватар)
        //проверить что метод вернул файл с new File(avatar.getFilePath())
    }
}