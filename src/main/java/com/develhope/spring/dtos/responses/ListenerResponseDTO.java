package com.develhope.spring.dtos.responses;

public class ListenerResponseDTO {

    private Long id; // Identificatore univoco dell'ascoltatore
    private String listenerName; // Nome dell'ascoltatore

    // Costruttore vuoto
    public ListenerResponseDTO() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe
    public ListenerResponseDTO(Long id, String listenerName) {
        this.id = id;
        this.listenerName = listenerName;
    }

    // Metodi getter e setter per gli attributi della classe
    // Ogni metodo restituisce o imposta il valore corrispondente dell'attributo


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }
}
