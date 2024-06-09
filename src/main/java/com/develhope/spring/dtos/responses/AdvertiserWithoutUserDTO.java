package com.develhope.spring.dtos.responses;

public class AdvertiserWithoutUserDTO {

    private String companyName;
    private String advertiserDescription;
    private String advertiserCountry;

    public AdvertiserWithoutUserDTO(String companyName, String advertiserDescription, String advertiserCountry) {
        this.companyName = companyName;
        this.advertiserDescription = advertiserDescription;
        this.advertiserCountry = advertiserCountry;
    }

    public AdvertiserWithoutUserDTO() {
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
