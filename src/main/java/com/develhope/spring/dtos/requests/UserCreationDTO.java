package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.UserEntity;

public class UserCreationDTO {

    private String nickName;
    private String name;
    private String lastName;
    private String numPhone;
    private String email;
    private String password;
    private String urlPhoto;
    private String urlSocial;
    private String userCountry;
    private UserEntity.Role role;

    private String artistName;
    private String description;
    private String artistCountry;

    private String companyName;
    private String advertiserCountry;
    private String advertiserDescription;

    public UserCreationDTO() {
    }

    public UserCreationDTO(String nickName, String name, String lastName, String numPhone, String email, String password, String urlPhoto, String urlSocial, String userCountry, UserEntity.Role role, String artistName, String description, String artistCountry, String companyName, String advertiserCountry, String advertiserDescription) {
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.numPhone = numPhone;
        this.email = email;
        this.password = password;
        this.urlPhoto = urlPhoto;
        this.urlSocial = urlSocial;
        this.userCountry = userCountry;
        this.role = role;
        this.artistName = artistName;
        this.description = description;
        this.artistCountry = artistCountry;
        this.companyName = companyName;
        this.advertiserCountry = advertiserCountry;
        this.advertiserDescription = advertiserDescription;
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

//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
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

    public UserEntity.Role getRole() {
        return role;
    }

    public void setRole(UserEntity.Role role) {
        this.role = role;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtistCountry() {
        return artistCountry;
    }

    public void setArtistCountry(String artistCountry) {
        this.artistCountry = artistCountry;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAdvertiserCountry() {
        return advertiserCountry;
    }

    public void setAdvertiserCountry(String advertiserCountry) {
        this.advertiserCountry = advertiserCountry;
    }

    public String getAdvertiserDescription() {
        return advertiserDescription;
    }

    public void setAdvertiserDescription(String advertiserDescription) {
        this.advertiserDescription = advertiserDescription;
    }
}

