package com.develhope.spring.dtos.requests;

public class ListenerRequestDTO {

    private String listenerName; // Nome dell'ascoltatore

    // Costruttore vuoto che serve sempre
    public ListenerRequestDTO() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe
    public ListenerRequestDTO(String listenerName) {
        this.listenerName = listenerName;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }
}
