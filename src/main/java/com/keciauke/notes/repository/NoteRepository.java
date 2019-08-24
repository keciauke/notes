package com.keciauke.notes.repository;

import com.keciauke.notes.entity.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {
    public List<Note> findAllByRootNoteId(Long id);
}
