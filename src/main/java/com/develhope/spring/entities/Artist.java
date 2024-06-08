package com.develhope.spring.entities;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "artists")
public class Artist {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String artistName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String artistCountry;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    List<Album> albums;

    public Artist() {
    }

    public Artist(Long id, User user, String artistName, String description, String artistCountry, List<Album> albums) {
        this.id = id;
        this.user = user;
        this.artistName = artistName;
        this.description = description;
        this.artistCountry = artistCountry;
        this.albums = albums;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtistCountry() {
        return artistCountry;
    }

    public void setArtistCountry(String artistCountry) {
        this.artistCountry = artistCountry;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}