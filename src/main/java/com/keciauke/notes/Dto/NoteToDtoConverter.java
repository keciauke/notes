package com.keciauke.notes.Dto;

import com.keciauke.notes.entity.Note;

public class NoteToDtoConverter {
    public static NoteDto convert(final Note note) {
        NoteDto noteDto = new NoteDto();
        noteDto.setContent(note.getContent());
        noteDto.setTitle(note.getTitle());
        return noteDto;
    }
}
