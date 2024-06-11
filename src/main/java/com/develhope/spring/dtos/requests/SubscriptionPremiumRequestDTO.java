package com.develhope.spring.dtos.requests;

import java.time.LocalDateTime;

public class SubscriptionPremiumRequestDTO {

    private Long listenerId;
    private String type;
    private Float totalPrice;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean active;
    private String premiumFeatures;

    public SubscriptionPremiumRequestDTO() {
    }

    public SubscriptionPremiumRequestDTO(Long listenerId,
                                         String type,
                                         Float totalPrice,
                                         LocalDateTime start,
                                         LocalDateTime end,
                                         Boolean active,
                                         String premiumFeatures) {
        this.listenerId = listenerId;
        this.type = type;
        this.totalPrice = totalPrice;
        this.start = start;
        this.end = end;
        this.active = active;
        this.premiumFeatures = premiumFeatures;
    }

    public Long getListenerId() {
        return listenerId;
    }

    public void setListenerId(Long listenerId) {
        this.listenerId = listenerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPremiumFeatures() {
        return premiumFeatures;
    }

    public void setPremiumFeatures(String premiumFeatures) {
        this.premiumFeatures = premiumFeatures;
    }
}

