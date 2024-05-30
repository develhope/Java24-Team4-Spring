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

    //TODO - manca foreign key album

    //TODO - manca foreign key genre

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

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", artist_id=" + artist_id +
                ", year_release=" + year_release +
                ", duration_time=" + duration_time +
                ", number_of_plays=" + number_of_plays +
                ", link_audio='" + link_audio + '\'' +
                '}';
    }
}