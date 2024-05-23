package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AdvertisementViewDTO {


    private Long id;
    private User user;
    private String advText;
    private String audioLink;
    private String imageLink;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer orderedViews;
    private BigDecimal costPerDay;
    private BigDecimal costPerView;
    private BigDecimal finalCost;
    private Integer actualViews;
    private Integer daysPassed;
    private boolean active;


    public AdvertisementViewDTO(
            Long id,
            User user,
            String advText,
            String audioLink,
            String imageLink,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer orderedViews,
            BigDecimal costPerDay,
            BigDecimal costPerView,
            BigDecimal finalCost,
            Integer actualViews,
            Integer daysPassed,
            boolean active
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
        this.daysPassed = daysPassed;
        this.active = active;
    }


    public AdvertisementViewDTO() {
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

    public BigDecimal getCostPerDay() {
        return costPerDay;
    }

    public void setCostPerDay(BigDecimal costPerDay) {
        this.costPerDay = costPerDay;
    }

    public BigDecimal getCostPerView() {
        return costPerView;
    }

    public void setCostPerView(BigDecimal costPerView) {
        this.costPerView = costPerView;
    }

    public BigDecimal getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(BigDecimal finalCost) {
        this.finalCost = finalCost;
    }

    public Integer getActualViews() {
        return actualViews;
    }

    public void setActualViews(Integer actualViews) {
        this.actualViews = actualViews;
    }

    public Integer getDaysPassed() {
        return daysPassed;
    }

    public void setDaysPassed(Integer daysPassed) {
        this.daysPassed = daysPassed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
