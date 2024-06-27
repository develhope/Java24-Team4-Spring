package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Advertisement;
import com.develhope.spring.entities.User;

import java.util.List;

public class AdvertiserResponseDTO {

    private User user;
    private String companyName;
    private String advertiserDescription;
    private String advertiserCountry;
    private List<Advertisement> advertisements;

    public AdvertiserResponseDTO() {
    }

    public AdvertiserResponseDTO(User user, String companyName, String advertiserDescription, String advertiserCountry, List<Advertisement> advertisements) {

        this.user = user;
        this.companyName = companyName;
        this.advertiserDescription = advertiserDescription;
        this.advertiserCountry = advertiserCountry;
        this.advertisements = advertisements;
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

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}


