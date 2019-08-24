package com.keciauke.notes.service;

import com.keciauke.notes.Exception.NoteNotFoundException;
import com.keciauke.notes.entity.Note;
import com.keciauke.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteHistoryService {

    private final NoteRepository noteRepository;

    @Autowired
    NoteHistoryService(final NoteRepository repository) {
        this.noteRepository = repository;
    }

    public List<Note> getNoteHistory(final Long id) {
        List<Note> list = this.noteRepository.findAllByRootNoteId(id);
        if (!list.isEmpty()) {
            return list;
        } else {
            throw new NoteNotFoundException(id);
        }
    }
}
