package com.develhope.spring.models;

public class Response {
    private int status; // Codice di stato della risposta
    private String message; // Messaggio della risposta
    private Object data; // Dati della risposta

    // Costruttore con parametri per inizializzare gli attributi della classe

    public Response(int status, String message, Object data) {
        this.status = status; // Inizializza il codice di stato con il valore fornito
        this.message = message; // Inizializza il messaggio con il valore fornito
        this.data = data; // Inizializza i dati con il valore fornito
    }

    // Costruttore con parametri per inizializzare gli attributi della classe
    // Senza i dati della risposta

    public Response(int status, String message) {
        this.status = status; // Inizializza il codice di stato con il valore fornito
        this.message = message; // Inizializza il messaggio con il valore fornito
    } 


    // Metodo getter per ottenere il codice di stato della risposta
    public int getStatus() {
        return status;
    }
    // Metodo getter per ottenere il messaggio della risposta
    public String getMessage() {
        return message;
    }
    // Metodo getter per ottenere i dati della risposta

    public Object getData() {
        return data;
    }
}
