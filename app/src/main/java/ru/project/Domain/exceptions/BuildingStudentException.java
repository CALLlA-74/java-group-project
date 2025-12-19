package ru.project.Domain.exceptions;

public class BuildingStudentException extends Exception {
    String message;

    public BuildingStudentException(String message) {
        super();
        this.message = message;
    }
}
