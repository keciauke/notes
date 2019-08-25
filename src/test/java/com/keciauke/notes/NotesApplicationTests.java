package com.keciauke.notes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.keciauke.notes.Dto.NoteDto;
import com.keciauke.notes.entity.Note;
import com.keciauke.notes.repository.NoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteRepository noteRepository;

    List<Note> list = new ArrayList<>();
    List<Note> list2 = new ArrayList<>();
    Note noteA = new Note();
    Note noteB = new Note();
    Note noteDeleted = new Note();
    @Before
    public void init() {
        noteA.setRootNoteId(1l);
        noteB.setRootNoteId(1l);
        noteA.setId(1l);
        noteB.setId(2l);
        noteA.setCurrentVersion(0l);
        noteB.setCurrentVersion(1l);
        noteA.setUpToDate(false);
        noteB.setUpToDate(true);
        noteA.setDeleted(false);
        noteB.setDeleted(false);
        noteDeleted.setId(2l);
        list.add(noteA);
        list.add(noteB);
    }

    @Test
    public void shouldReturnNoteWithGivenRootId() {
        when(noteRepository.findAllByRootNoteId(1l)).thenReturn(list);
        try {
            mockMvc.perform(get("/notes/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("rootNoteId",is(1)))
                    .andExpect(jsonPath("upToDate",is(true)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteNoteByIdAndThrowException() throws Exception {
        when(noteRepository.findAllByRootNoteId(2l)).thenReturn(list2);
        when(noteRepository.findById(2l)).thenReturn(Optional.of(noteDeleted));
        mockMvc.perform(delete("/notes/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
        mockMvc.perform(get("/notes/2"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void shouldAddNewNote() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        NoteDto dto = new NoteDto();
        dto.setTitle("Title");
        dto.setContent("Content");
        when(noteRepository.save(Mockito.any(Note.class))).thenReturn(noteA);
        when(noteRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(noteA));
        mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON_UTF8).content(objectWriter.writeValueAsString(dto)))
                .andExpect(status().isOk());
        dto.setContent("");
        mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON_UTF8).content(objectWriter.writeValueAsString(dto)))
                .andExpect(status().isNotAcceptable());
    }
}
