package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Advertisement;

import java.util.List;

public class AdvertiserWithoutUserDTO {

    private String companyName;
    private String advertiserDescription;
    private String advertiserCountry;
    private List<Advertisement> advertisements;

    public AdvertiserWithoutUserDTO(String companyName, String advertiserDescription, String advertiserCountry, List<Advertisement> advertisements) {
        this.companyName = companyName;
        this.advertiserDescription = advertiserDescription;
        this.advertiserCountry = advertiserCountry;
        this.advertisements = advertisements;
    }

    public AdvertiserWithoutUserDTO() {
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
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
