package com.keciauke.notes.Exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(final Long id) {
        super("Couldn't find a note with id = " + id);
    }
}
