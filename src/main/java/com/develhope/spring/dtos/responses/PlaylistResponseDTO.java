package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Song;

import java.time.LocalDate;
import java.util.List;

public class PlaylistResponseDTO {
    private Long id;
    private String title;
    private Listener listener;
    private List<Song> songs;
    private LocalDate creationDate;
    private LocalDate updateDate;

    public PlaylistResponseDTO() {

    }

    public PlaylistResponseDTO(Long id, String title, Listener listener, List<Song> songs, LocalDate creationDate, LocalDate updateDate) {
        this.id = id;
        this.title = title;
        this.listener = listener;
        this.songs = songs;
        this.creationDate = creationDate;
        this.updateDate = updateDate;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
}


