package com.develhope.spring.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificatore univoco dell'artista

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Chiave esterna per l'utente associato all'artista

    @Column(nullable = false)
    private String artistName; // Nome dell'artista

    @Column(nullable = false)
    private String description; // Descrizione dell'artista

    @Column(nullable = false)
    private String country; // Paese dell'artista

    // Costruttore vuoto richiesto da JPA
    public Artist() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe
    public Artist(User user, String artistName, String description, String country) {
        this.user = user;
        this.artistName = artistName;
        this.description = description;
        this.country = country;
    }

    // Metodi getter e setter per gli attributi della classe
    // Ogni metodo restituisce o imposta il valore corrispondente dell'attributo

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
