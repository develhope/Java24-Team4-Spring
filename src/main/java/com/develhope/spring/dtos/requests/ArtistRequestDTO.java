package com.develhope.spring.dtos.requests;

public class ArtistRequestDTO {

    private String artistName; // Nome dell'artista
    private String description; // Descrizione dell'artista
    private String country; // Paese dell'artista
    private Long userId; // Id dell'utente associato all'artista

    // Costruttore vuoto richiesto da alcuni framework
    public ArtistRequestDTO() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe
    public ArtistRequestDTO(String artistName, String description, String country, Long userId) {
        this.artistName = artistName;
        this.description = description;
        this.country = country;
        this.userId = userId;
    }

    // Metodi getter e setter per gli attributi della classe
    // Ogni metodo restituisce o imposta il valore corrispondente dell'attributo

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
