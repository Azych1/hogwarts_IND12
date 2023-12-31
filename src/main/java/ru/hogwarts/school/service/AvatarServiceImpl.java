package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService{

    private final String avatarsDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;


    public AvatarServiceImpl(StudentService studentService,
                             AvatarRepository avatarRepository,
                             @Value("${path.to.avatars.folder}") String avatarsDir) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
        this.avatarsDir = avatarsDir;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        Student student = studentService.read(studentId);

        Path filePath = saveToFile(student, avatarFile);

        saveToDb(filePath, avatarFile, student);
    }

    private Path saveToFile(Student student, MultipartFile avatarFile) throws IOException {
        Path filePath = Path.of(avatarsDir,
                student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        return filePath;
    }

    private void saveToDb(Path filePath, MultipartFile avatarFile, Student student) throws IOException {
        Avatar avatar = avatarRepository.findByStudent_id(student.getId()).orElse(new Avatar());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());

        avatarRepository.save(avatar);
    }

    @Override
    public Avatar readFromDB(long id) {
        return avatarRepository.findById(id)
                .orElseThrow(() -> new AvatarNotFoundException("Аватар не найден"));
    }


    @Override
    public File readFromFile(long id) throws IOException {
        Avatar avatar = readFromDB(id);
        Path path = Path.of(avatar.getFilePath());

        return new File(avatar.getFilePath());
    }


    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}