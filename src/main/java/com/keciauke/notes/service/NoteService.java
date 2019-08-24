package com.keciauke.notes.service;

import com.keciauke.notes.Dto.NoteDto;
import com.keciauke.notes.Exception.InvalidDataException;
import com.keciauke.notes.Exception.NoteNotFoundException;
import com.keciauke.notes.Exception.NotesNotFoundException;
import com.keciauke.notes.entity.Note;
import com.keciauke.notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    @Autowired
    NoteService(final NoteRepository repository) {
        this.noteRepository = repository;
    }

    public Note getNoteById(final Long id) {
        final List<Note> notes = this.noteRepository.findAllByRootNoteId(id);
        final Optional<Note> note = notes.stream().filter(Note::getUpToDate).findAny();
        if (note.isPresent() && !note.get().getDeleted()) {
            return note.get();
        } else {
            throw new NoteNotFoundException(id);
        }
    }

    public List<Note> getAllNotes() {
        final List<Note> list = ((List<Note>) this.noteRepository.findAll()).stream()
                .filter(Note::getUpToDate).filter(note -> !note.getDeleted()).collect(Collectors.toList());
        if (!list.isEmpty()) {
            list.sort(Comparator.comparing(Note::getModifiedDate));
            return list;
        } else {
            throw new NotesNotFoundException();
        }
    }

    @Transactional
    public Note addNote(NoteDto noteDto) {
        if (noteDto.getContent() == null ||
                noteDto.getContent().isEmpty() ||
                noteDto.getTitle() == null ||
                noteDto.getTitle().isEmpty()) {
            throw new InvalidDataException();
        }
        final Note note = new Note();
        note.setContent(noteDto.getContent());
        note.setTitle(noteDto.getTitle());
        note.setCreatedDate(LocalDateTime.now());
        note.setModifiedDate(note.getCreatedDate());
        note.setCurrentVersion(0l);
        note.setDeleted(false);
        note.setUpToDate(true);
        final Note temp = this.noteRepository.save(note);
        this.noteRepository.findById(temp.getId()).get().setRootNoteId(temp.getId());
        return temp;
    }

    @Transactional
    public Note updateNote(final NoteDto dto, final Long id) {
        final Note note = this.getNoteById(id);
        this.checkData(dto);
        final Note newNote = new Note();
        newNote.setRootNoteId(note.getRootNoteId());
        note.setUpToDate(false);
        newNote.setUpToDate(true);
        newNote.setContent(dto.getContent());
        newNote.setTitle(dto.getTitle());
        newNote.setCreatedDate(note.getCreatedDate());
        newNote.setModifiedDate(LocalDateTime.now());
        newNote.setCurrentVersion(note.getCurrentVersion() + 1);
        newNote.setDeleted(false);
        return this.noteRepository.save(newNote);

    }

    private void checkData(final NoteDto dto) {
        if (dto.getContent() == null ||
                dto.getContent().isEmpty() ||
                dto.getTitle() == null ||
                dto.getTitle().isEmpty()) {
            throw new InvalidDataException();
        }
    }

    public Boolean deleteNote(final Long id) {
        final Optional<Note> note = this.noteRepository.findById(id);
        if (!note.isPresent()) {
            throw new NoteNotFoundException(id);
        }
        final List<Note> list = this.noteRepository.findAllByRootNoteId(id);
        list.forEach(n -> n.setDeleted(true));
        return true;
    }
}
