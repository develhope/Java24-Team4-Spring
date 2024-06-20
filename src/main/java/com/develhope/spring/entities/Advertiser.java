package com.develhope.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "advertisers")
public class Advertiser {

    @Id
    private Long id;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", nullable = false)
    private User user;
    private String companyName;
    private String advertiserCountry;
    private String advertiserDescription;


    @OneToMany(mappedBy = "advertiser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Advertisement> advertisements;

    public Advertiser() {
    }

    public Advertiser(Long id, User user, String companyName, String advertiserCountry, String advertiserDescription, List<Advertisement> advertisements) {
        this.id = id;
        this.user = user;
        this.companyName = companyName;
        this.advertiserCountry = advertiserCountry;
        this.advertiserDescription = advertiserDescription;
        this.advertisements = advertisements;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }
}
