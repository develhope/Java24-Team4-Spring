package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.UserEntity;

import java.time.LocalDate;

public class UserWithRoleDetailsResponseDTO {

    private Long id;
    private String nickName;
    private String name;
    private String lastName;
    private String email;
    private String numPhone;
    private LocalDate registrationDate;
    private String urlPhoto;
    private String urlSocial;
    private String userCountry;
    private UserEntity.Role role;
    private Object roleDetails;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
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

    public Object getRoleDetails() {
        return roleDetails;
    }

    public void setRoleDetails(Object roleDetails) {
        this.roleDetails = roleDetails;
    }

    public UserWithRoleDetailsResponseDTO(Long id, String nickName, String name, String lastName, String email, String numPhone, LocalDate registrationDate, String urlPhoto, String urlSocial, String userCountry, UserEntity.Role role, Object roleDetails) {
        this.id = id;
        this.nickName = nickName;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.numPhone = numPhone;
        this.registrationDate = registrationDate;
        this.urlPhoto = urlPhoto;
        this.urlSocial = urlSocial;
        this.userCountry = userCountry;
        this.role = role;
        this.roleDetails = roleDetails;
    }

    public UserWithRoleDetailsResponseDTO() {
    }


}

