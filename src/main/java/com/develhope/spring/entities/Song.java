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

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @OneToMany
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;
    @Column(nullable = false)
    private int year_release;
    @Column(nullable = false)
    private Integer duration_time;
    @Column(nullable = false)
    private int number_of_plays;
    @Column(nullable = false)
    private String link_audio;

    public Song() {
    }

    public Song(Long id, String title, Album album, Genre genre, int year_release, Integer duration_time,
                int number_of_plays, String link_audio) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.genre = genre;
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