package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.User;
import java.time.LocalDate;

public class UserRequestDTO {
    private String nickName;
    private String name;
    private String lastName;
    private String numPhone;
    private String email;
    private String password;
    private LocalDate registrationDate;
    private String urlProfile;
    private String urlSocial;
    private User.Role role;

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

