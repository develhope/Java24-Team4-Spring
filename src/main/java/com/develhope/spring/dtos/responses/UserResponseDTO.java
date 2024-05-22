package com.develhope.spring.dtos.responses;

public class UserResponseDTO {
    private Long id; // Identificatore univoco dell'utente
    private String nickName; // Nickname dell'utente
    private String name; // Nome dell'utente
    private String lastName; // Cognome dell'utente
    private String email; // Email dell'utente

    // Costruttore con parametri per inizializzare gli attributi della classe
    public UserResponseDTO(Long id, String nickName, String name, String lastName, String email) {
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    // Metodi getter e setter per gli attributi della classe
    // Ogni metodo restituisce o imposta il valore corrispondente dell'attributo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
