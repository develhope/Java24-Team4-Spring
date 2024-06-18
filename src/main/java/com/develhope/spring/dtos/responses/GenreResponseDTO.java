package com.develhope.spring.dtos.responses;

public class GenreResponseDTO {
    private Long id;
    private String title;

    public GenreResponseDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public GenreResponseDTO() {
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
}
