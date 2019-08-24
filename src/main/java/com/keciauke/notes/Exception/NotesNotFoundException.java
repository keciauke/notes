package com.keciauke.notes.Exception;

public class NotesNotFoundException extends RuntimeException {
    public NotesNotFoundException() {
        super("Nie znaleziono żadnych notatek.");
    }
}
