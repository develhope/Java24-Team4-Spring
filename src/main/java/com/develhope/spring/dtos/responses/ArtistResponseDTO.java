package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Album;
import com.develhope.spring.entities.User;

import java.util.List;

public class ArtistResponseDTO {

    private User user;
    private String artistName;
    private String description;
    private String artistCountry;
    private List<Album> albums;

    public ArtistResponseDTO() {
    }

    public ArtistResponseDTO(User user, String artistName, String description, String artistCountry, List<Album> albums) {
        this.user = user;
        this.artistName = artistName;
        this.description = description;
        this.artistCountry = artistCountry;
        this.albums = albums;
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