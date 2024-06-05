package com.develhope.spring.dtos.responses;

import java.time.Duration;

public class SongResponseDTO {
    private int id;
    private String title;
    private Album album;
    private Duration duration_time;
     private int number_of_plays;

    public SongResponseDTO(int id, String title, Album album, Duration duration_time, int number_of_plays) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.duration_time = duration_time;
        this.number_of_plays = number_of_plays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Duration getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(Duration duration_time) {
        this.duration_time = duration_time;
    }

    public int getNumber_of_plays() {
        return number_of_plays;
    }

    public void setNumber_of_plays(int number_of_plays) {
        this.number_of_plays = number_of_plays;
    }
}
