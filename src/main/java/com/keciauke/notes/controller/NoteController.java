package com.keciauke.notes.controller;

import com.keciauke.notes.Dto.NoteDto;
import com.keciauke.notes.Exception.InvalidDataException;
import com.keciauke.notes.Exception.NoteNotFoundException;
import com.keciauke.notes.entity.Note;
import com.keciauke.notes.service.NoteHistoryService;
import com.keciauke.notes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final NoteHistoryService noteHistoryService;

    @Autowired
    NoteController(final NoteService service, final NoteHistoryService historyService) {
        this.noteService = service;
        this.noteHistoryService = historyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable final Long id) {
        try {
            Note note = this.noteService.getNoteById(id);
            return new ResponseEntity<Note>(note, HttpStatus.OK);
        } catch (NoteNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Note>> getAllNotes() {
        try {
            List<Note> list = this.noteService.getAllNotes();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoteNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<Note>> getNoteHistory(@PathVariable final Long id) {
        try {
            List<Note> history = this.noteHistoryService.getNoteHistory(id);
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (NoteNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity<Note> addNewNote(@RequestBody final NoteDto note) {
        try {
            Note n = this.noteService.addNote(note);
            return new ResponseEntity<>(n, HttpStatus.OK);
        } catch (InvalidDataException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNoteById(@RequestBody final NoteDto dto, @PathVariable final Long id) {
        try {
            Note n = this.noteService.updateNote(dto, id);
            return new ResponseEntity<>(n, HttpStatus.OK);
        } catch (InvalidDataException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (NoteNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNoteById(@PathVariable final Long id) {
        try {
            this.noteService.deleteNote(id);
        } catch (NoteNotFoundException e) {
            return new ResponseEntity(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(true, HttpStatus.OK);
    }
}
