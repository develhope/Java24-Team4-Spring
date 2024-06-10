package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Song;

public class CommentResponseDTO {
    private Long id;
    private Song song;
    private Listener listener;
    private String commentText;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}