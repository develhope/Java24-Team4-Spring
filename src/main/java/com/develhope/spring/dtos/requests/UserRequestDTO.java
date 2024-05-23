package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.User;
import java.time.LocalDate;

public class UserRequestDTO {
    private String nickName; // Nickname dell'utente
    private String name; // Nome dell'utente
    private String lastName; // Cognome dell'utente
    private String numPhone; // Numero di telefono dell'utente
    private String email; // Email dell'utente
    private String password; // Password dell'utente
    private LocalDate registrationDate; // Data di registrazione dell'utente
    private String urlProfile; // URL del profilo dell'utente
    private String urlSocial; // URL dei profili social dell'utente
    private User.Role role; // Ruolo dell'utente

    public UserRequestDTO() {}

    public UserRequestDTO(String nickName, String name, String lastName, String numPhone, String email, String password, LocalDate registrationDate, String urlProfile, String urlSocial, User.Role role) {
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

    // Metodi getter e setter per i campi della classe
    // Ogni metodo restituisce o imposta il valore corrispondente del campo
    // Esempio: getNickName() restituisce il valore del nickname dell'utente

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

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}

