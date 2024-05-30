package com.develhope.spring.dtos.responses;

public class GenreResponseDTO {
    private Long id;
    private String title;


    // Costruttore con parametri per inizializzare gli attributi della classe
    public GenreResponseDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    // Metodi getter e setter per gli attributi della classe
    // Ogni metodo restituisce o imposta il valore corrispondente dell'attributo
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
