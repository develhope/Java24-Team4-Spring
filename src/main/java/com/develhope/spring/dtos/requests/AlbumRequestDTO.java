package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.Artist;

public class AlbumRequestDTO {
    private Long artistId;

    private String title;

    private int year_release;

    private String description;

    private String cover_link;

    public AlbumRequestDTO() {
    }

    public AlbumRequestDTO(Long artistId, String title, int year_release, String description, String cover_link) {
        this.artistId = artistId;
        this.title = title;
        this.year_release = year_release;
        this.description = description;
        this.cover_link = cover_link;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
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
