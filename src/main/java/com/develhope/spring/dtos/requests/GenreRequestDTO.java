package com.develhope.spring.dtos.requests;

public class GenreRequestDTO {
    private String title;

    public GenreRequestDTO(String title) {
        this.title = title;
    }

    public GenreRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
