package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Album;
import com.develhope.spring.entities.UserEntity;

import java.util.List;

public class ArtistResponseDTO {

    private UserEntity userEntity;
    private String artistName;
    private String description;
    private String artistCountry;
    private List<Album> albums;

    public ArtistResponseDTO() {
    }

    public ArtistResponseDTO(UserEntity userEntity, String artistName, String description, String artistCountry, List<Album> albums) {
        this.userEntity = userEntity;
        this.artistName = artistName;
        this.description = description;
        this.artistCountry = artistCountry;
        this.albums = albums;
    }



    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
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