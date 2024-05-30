package com.develhope.spring.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "album_id")
    private int album_id;


    @OneToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private int artist_id;
    @Column(nullable = false)
    private int year_release;
    @Column
    private int duration_time;
    @Column
    private int number_of_plays;
    @Column
    private String link_audio;

    public Song() {
    }

    public Song(Long id, String title, int artist_id) {
        this.id = id;
        this.title = title;
        this.artist_id = artist_id;
    }

    public Song(Long id, String title, User user, int artist_id, int year_release, int duration_time, int number_of_plays, String link_audio) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.artist_id = artist_id;
        this.year_release = year_release;
        this.duration_time = duration_time;
        this.number_of_plays = number_of_plays;
        this.link_audio = link_audio;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
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