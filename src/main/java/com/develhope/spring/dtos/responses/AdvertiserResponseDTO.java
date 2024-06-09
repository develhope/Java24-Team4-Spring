package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.User;

public class AdvertiserResponseDTO {

    private User user;
    private String companyName;
    private String advertiserDescription;
    private String advertiserCountry;

    public AdvertiserResponseDTO() {
    }

    public AdvertiserResponseDTO(User user, String companyName, String advertiserDescription, String advertiserCountry) {

        this.user = user;
        this.companyName = companyName;
        this.advertiserDescription = advertiserDescription;
        this.advertiserCountry = advertiserCountry;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAdvertiserDescription() {
        return advertiserDescription;
    }

    public void setAdvertiserDescription(String advertiserDescription) {
        this.advertiserDescription = advertiserDescription;
    }

    public String getAdvertiserCountry() {
        return advertiserCountry;
    }

    public void setAdvertiserCountry(String advertiserCountry) {
        this.advertiserCountry = advertiserCountry;
    }
}

