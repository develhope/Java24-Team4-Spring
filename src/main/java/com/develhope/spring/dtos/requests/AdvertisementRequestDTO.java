package com.develhope.spring.dtos.requests;

import java.time.LocalDateTime;

public class AdvertisementRequestDTO {

    private Long advertiserUserId;
    private String advText;
    private String audioLink;
    private String imageLink;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer orderedViews;

    public AdvertisementRequestDTO(
            Long advertiserUserId, String advText,
            String audioLink,
            String imageLink,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Integer orderedViews
    ) {
        this.advertiserUserId = advertiserUserId;
        this.advText = advText;
        this.audioLink = audioLink;
        this.imageLink = imageLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.orderedViews = orderedViews;
    }

    public Long getAdvertiserUserId() {
        return advertiserUserId;
    }

    public void setAdvertiserUserId(Long advertiserUserId) {
        this.advertiserUserId = advertiserUserId;
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
}
