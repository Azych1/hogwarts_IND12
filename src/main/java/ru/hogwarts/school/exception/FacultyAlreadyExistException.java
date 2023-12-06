package ru.hogwarts.school.exception;

public class FacultyAlreadyExistException extends RuntimeException {

    public FacultyAlreadyExistException(String message) {
        super(message);
    }
}
