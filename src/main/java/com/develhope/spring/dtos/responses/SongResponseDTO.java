package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Album;
import com.develhope.spring.entities.Genre;

import java.time.Duration;

public class SongResponseDTO {
    private int id;
    private String title;
    private Album album;
    private Genre genre;
    private Duration duration_time;
    private int year_release;
    private int number_of_plays;
    private String link_audio;

    public SongResponseDTO() {
    }

    public SongResponseDTO(int id, String title, Album album, Genre genre, Duration duration_time, int yearRelease, int number_of_plays, String linkAudio) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.genre = genre;
        this.duration_time = duration_time;
        this.year_release = yearRelease;
        this.number_of_plays = number_of_plays;
        this.link_audio = linkAudio;
    }

    public String getLink_audio() {
        return link_audio;
    }

    public void setLink_audio(String link_audio) {
        this.link_audio = link_audio;
    }

    public int getYear_release() {
        return year_release;
    }

    public void setYear_release(int year_release) {
        this.year_release = year_release;
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
