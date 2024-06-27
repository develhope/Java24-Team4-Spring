package com.develhope.spring.entities;

import jakarta.persistence.*;


import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    public enum Role {
        ARTIST,
        LISTENER,
        ADVERTISER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String numPhone;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate registrationDate;


    @Column(length = 65535, columnDefinition="TEXT")
    private String urlPhoto;
    private String photoObjectStorageName;

    @Column(nullable = true)
    private String urlSocial;

    @Column(nullable = false)
    private String userCountry;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // Ruolo dell'utente


    public User(Long id, String nickName, String name, String lastName, String numPhone, String email, String password, LocalDate registrationDate, String urlPhoto, String photoObjectStorageName, String urlSocial, String userCountry, Role role) {
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.numPhone = numPhone;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.urlPhoto = urlPhoto;
        this.photoObjectStorageName = photoObjectStorageName;
        this.urlSocial = urlSocial;
        this.userCountry = userCountry;
        this.role = role;
    }

    public User() {
    }

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

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getPhotoObjectStorageName() {
        return photoObjectStorageName;
    }

    public void setPhotoObjectStorageName(String photoObjectStorageName) {
        this.photoObjectStorageName = photoObjectStorageName;
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

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
}
