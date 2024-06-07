package com.develhope.spring.dtos.requests;

import java.time.Duration;

public class SongRequestDTO {
    private String title;
    private Album album;
    private int year_release;
    private Duration duration_time;
    private String link_audio;

    public SongRequestDTO() {
    }

    public SongRequestDTO(String title, Album album, int year_release, Duration duration_time, String link_audio) {
        this.title = title;
        this.album = album;
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

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getYear_release() {
        return year_release;
    }

    public void setYear_release(int year_release) {
        this.year_release = year_release;
    }

    public Duration getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(Duration duration_time) {
        this.duration_time = duration_time;
    }

    public String getLink_audio() {
        return link_audio;
    }

    public void setLink_audio(String link_audio) {
        this.link_audio = link_audio;
    }
}
