package com.develhope.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    public enum Role {
        ARTIST,
        LISTENER,
        ADVERTISER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String nickname;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String numPhone;

    @Column(nullable = false, unique = true)
    private String email;

//    @Column(nullable = false, unique = true)
//    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate registrationDate;

    private String urlPhoto;

    @JsonIgnore
    private String photoObjectStorageName;

    @Column(nullable = true)
    private String urlSocial;

    @Column(nullable = false)
    private String userCountry;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role; // Ruolo dell'utente


    public UserEntity() {
    }

    public UserEntity(Long id, String name, String nickname, String lastName, String numPhone, String email, String password, LocalDate registrationDate, String urlPhoto, String photoObjectStorageName, String urlSocial, String userCountry, Role role) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
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

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
