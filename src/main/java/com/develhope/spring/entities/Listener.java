package com.develhope.spring.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "listeners")
public class Listener {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificatore univoco dell'artista

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String listenerName;

    // Costruttore vuoto che non fa mai male
    public Listener() {
    }

    // Costruttore con parametri per inizializzare gli attributi della classe
    public Listener(Long id, User user, String listenerName) {
        this.id = id;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getListenerName() {
        return listenerName;
    }

    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }

}
