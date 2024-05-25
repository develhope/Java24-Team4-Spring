package com.develhope.spring.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificatore univoco dell'utente

    @Column(name = "nickName", unique = true, nullable = false)
    private String nickName; // Nickname dell'utente, deve essere unico e non nullo

    @Column(nullable = false)
    private String name; // Nome dell'utente, non può essere nullo

    @Column(nullable = false)
    private String lastName; // Cognome dell'utente, non può essere nullo

    @Column(unique = true)
    private String numPhone; // Numero di telefono dell'utente, deve essere unico

    @Column(nullable = false, unique = true)
    private String email; // Email dell'utente, deve essere unica e non nulla

    @Column(nullable = false)
    private String password; // Password dell'utente, non può essere nullo

    @Column(nullable = false)
    private LocalDate registrationDate; // Data di registrazione dell'utente, non può essere nullo

    @Column(name = "url_profile", nullable = true)
    private String urlProfile; // URL del profilo dell'utente

    @Column(name = "url_social", nullable = true)
    private String urlSocial; // URL dei profili social dell'utente

    public enum Role {
        ARTIST, // Artista
        LISTENER, // Ascoltatore
        ADVERTISER // Pubblicitario
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // Ruolo dell'utente

    // Costruttore con parametri per inizializzare gli attributi della classe
    public User(
            Long id,
            String nickName,
            String name,
            String lastName,
            String numPhone,
            String email,
            String password,
            LocalDate registrationDate,
            String urlProfile,
            String urlSocial,
            Role role) {
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.numPhone = numPhone;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.urlProfile = urlProfile;
        this.urlSocial = urlSocial;
        this.role = role;
    }

    // Costruttore alternativo per un rapido accesso a id, nickName ed email dell'utente
    public User(Long id, String nickName, String email) {
        this.id = id;
        this.nickName = nickName;
        this.email = email;
    }

    // Costruttore vuoto
    public User() {
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

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }

    public String getUrlSocial() {
        return urlSocial;
    }

    public void setUrlSocial(String urlSocial) {
        this.urlSocial = urlSocial;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Metodo toString per rappresentare l'oggetto User come stringa
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", numPhone='" + numPhone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", urlProfile='" + urlProfile + '\'' +
                ", urlSocial='" + urlSocial + '\'' +
                ", role=" + role +
                '}';
    }

}
