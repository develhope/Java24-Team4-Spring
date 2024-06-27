package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Song;

public class CommentResponseDTO {
    private Long id;
    private SongResponseDTO song;
    private ListenerResponseDTO listener;
    private String commentText;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, SongResponseDTO song, ListenerResponseDTO listener, String commentText) {
        this.id = id;
        this.song = song;
        this.listener = listener;
        this.commentText = commentText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SongResponseDTO getSong() {
        return song;
    }

    public void setSong(SongResponseDTO song) {
        this.song = song;
    }

    public ListenerResponseDTO getListener() {
        return listener;
    }

    public void setListener(ListenerResponseDTO listener) {
        this.listener = listener;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}


