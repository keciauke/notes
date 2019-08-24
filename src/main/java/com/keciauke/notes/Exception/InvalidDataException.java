package com.keciauke.notes.Exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException() {
        super("Podano nieprawidłowe dane.");
    }
}
