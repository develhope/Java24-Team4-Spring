package com.develhope.spring.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "advertisements")
public class Advertisement {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)  //TODO TROVARE UN MODO PER FARLA FUNZIONARE CON FETCH TYPE "LAZY"
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;   //TODO CHANGE USER TO LISTENER
    private String advText;
    private String audioLink;
    private String imageLink;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer orderedViews;
    private Float costPerDay;
    private Float costPerView;
    private Float finalCost;
    private int actualViews;
    private Boolean active;

    public Advertisement() {
    }

    public Advertisement(
            Long id,
            User user,
            String advText,
            String audioLink,
            String imageLink,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer orderedViews,
            Float costPerDay,
            Float costPerView,
            Float finalCost,
            int actualViews,
            Boolean active

    ) {

        this.id = id;
        this.user = user;
        this.advText = advText;
        this.audioLink = audioLink;
        this.imageLink = imageLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderedViews = orderedViews;
        this.costPerDay = costPerDay;
        this.costPerView = costPerView;
        this.finalCost = finalCost;
        this.actualViews = actualViews;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdvText() {
        return advText;
    }

    public void setAdvText(String advText) {
        this.advText = advText;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAudioLink() {
        return audioLink;
    }

    public void setAudioLink(String audioLink) {
        this.audioLink = audioLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getOrderedViews() {
        return orderedViews;
    }

    public void setOrderedViews(Integer orderedViews) {
        this.orderedViews = orderedViews;
    }

    public Float getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(Float costPerDay) {
        this.costPerDay = costPerDay;
    }

    public Float getCostPerView() {
        return costPerView;
    }

    public void setCostPerView(Float costPerView) {
        this.costPerView = costPerView;
    }

    public Float getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(Float finalCost) {
        this.finalCost = finalCost;
    }

    public int getActualViews() {
        return actualViews;
    }

    public void setActualViews(int actualViews) {
        this.actualViews = actualViews;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}