package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Artist;


public class AlbumResponseDTO {
    private Long id;

    private Artist artist;

    private String title;

    private int year_release;

    private String description;

    private String cover_link;

    public AlbumResponseDTO() {
    }

    public AlbumResponseDTO(Long id, Artist artist, String title, int year_release, String description, String cover_link) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.year_release = year_release;
        this.description = description;
        this.cover_link = cover_link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear_release() {
        return year_release;
    }

    public void setYear_release(int year_release) {
        this.year_release = year_release;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover_link() {
        return cover_link;
    }

    public void setCover_link(String cover_link) {
        this.cover_link = cover_link;
    }
}
