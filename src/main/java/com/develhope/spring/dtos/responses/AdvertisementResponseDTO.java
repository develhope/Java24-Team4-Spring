package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.User;

import java.time.LocalDateTime;

public class AdvertisementResponseDTO {


    private Long id;
    private User user;
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
    private Integer actualDuration;
    private Integer totalDuration;
    private Boolean active;


    public AdvertisementResponseDTO(
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
            Integer actualDuration,
            Integer totalDuration,
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
        this.actualDuration = actualDuration;
        this.totalDuration = totalDuration;
        this.active = active;
    }

    public AdvertisementResponseDTO() {
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

    public String getAdvText() {
        return advText;
    }

    public void setAdvText(String advText) {
        this.advText = advText;
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

    public Integer getActualDuration() {
        return actualDuration;
    }

    public void setActualDuration(Integer actualDuration) {
        this.actualDuration = actualDuration;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}