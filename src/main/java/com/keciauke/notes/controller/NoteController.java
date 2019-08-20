package com.keciauke.notes.controller;

import com.keciauke.notes.entity.Note;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @GetMapping("/{id}")
    public ResponseEntity<Long> getNoteById(@PathVariable final Long id)
    {
        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }
}
