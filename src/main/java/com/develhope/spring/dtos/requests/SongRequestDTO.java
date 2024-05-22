package com.develhope.spring.dtos.requests;

public class SongRequestDTO {
    private String title;
    private int user_id;
    private String artist;
    private int year_release;
    private int duration_time;
    private int number_of_plays;
    private String link_audio;

    public SongRequestDTO() {
    }

    public SongRequestDTO(String title, int user_id, String artist, int year_release, int duration_time, int number_of_plays, String link_audio) {
        this.title = title;
        this.user_id = user_id;
        this.artist = artist;
        this.year_release = year_release;
        this.duration_time = duration_time;
        this.number_of_plays = number_of_plays;
        this.link_audio = link_audio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist_id(String artist) {
        this.artist = artist;
    }

    public int getYear_release() {
        return year_release;
    }

    public void setYear_release(int year_release) {
        this.year_release = year_release;
    }

    public int getDuration_time() {
        return duration_time;
    }

    public void setDuration_time(int duration_time) {
        this.duration_time = duration_time;
    }

    public int getNumber_of_plays() {
        return number_of_plays;
    }

    public void setNumber_of_plays(int number_of_plays) {
        this.number_of_plays = number_of_plays;
    }

    public String getLink_audio() {
        return link_audio;
    }

    public void setLink_audio(String link_audio) {
        this.link_audio = link_audio;
    }
}
