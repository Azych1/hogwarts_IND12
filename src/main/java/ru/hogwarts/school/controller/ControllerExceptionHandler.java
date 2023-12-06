package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;

import java.rmi.StubNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({StudentAlreadyExistException.class, FacultyAlreadyExistException.class})
    public ResponseEntity<String> handleException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler({StudentNotFoundException.class, FacultyNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());

    }
}


