package com.develhope.spring.models;

public class Response {
    private int status; // Codice di stato della risposta
    private String message; // Messaggio della risposta
    private Object data; // Dati della risposta


    public Response(int status, String message, Object data) {
        this.status = status; // Inizializza il codice di stato con il valore fornito
        this.message = message; // Inizializza il messaggio con il valore fornito
        this.data = data; // Inizializza i dati con il valore fornito
    }


    public Response(int status, String message) {
        this.status = status; // Inizializza il codice di stato con il valore fornito
        this.message = message; // Inizializza il messaggio con il valore fornito
    }


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
