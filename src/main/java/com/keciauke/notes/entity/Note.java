package com.keciauke.notes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    @NotBlank
    private String title;

    @Column
    @NotNull
    @NotBlank
    private String content;

    @Column
    private Long rootNoteId;

    @Column
    @NotNull
    @JsonIgnore
    private LocalDateTime createdDate;

    @Column
    @NotNull
    @JsonIgnore
    private LocalDateTime modifiedDate;

    @Column
    @NotNull
    @JsonIgnore
    @ColumnDefault("false")
    private Boolean isDeleted;

    @Column
    @NotNull
    private Boolean isUpToDate;

    @Column
    private Long currentVersion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRootNoteId() {
        return rootNoteId;
    }

    public void setRootNoteId(Long rootNoteId) {
        this.rootNoteId = rootNoteId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Long currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Boolean getUpToDate() {
        return isUpToDate;
    }

    public void setUpToDate(Boolean upToDate) {
        isUpToDate = upToDate;
    }
}
