package com.develhope.spring.dtos.requests;

import java.time.Duration;

public class SongRequestDTO {

    private String title;
    private Long albumId;
    private Long genreId;
    private int year_release;
    private Integer duration_time;
    private String link_audio;

    public SongRequestDTO() {
    }

    public SongRequestDTO(String title, Long albumId, Long genreId, int year_release, Integer duration_time, String link_audio) {
        this.title = title;
        this.albumId = albumId;
        this.genreId = genreId;
        this.year_release = year_release;
        this.duration_time = duration_time;
        this.link_audio = link_audio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public int getYear_release() {
        return year_release;
    }

    public void setYear_release(int year_release) {
        this.year_release = year_release;
    }

    public Integer getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(Integer duration_time) {
        this.duration_time = duration_time;
    }

    public String getLink_audio() {
        return link_audio;
    }

    public void setLink_audio(String link_audio) {
        this.link_audio = link_audio;
    }
}