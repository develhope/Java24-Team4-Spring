package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Subscription.SubscrType;
import java.time.LocalDateTime;

public class SubscriptionPremiumResponseDTO {
    private Long id;
    private SubscrType type;
    private Float totalPrice;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean active;
    private String premiumFeatures;
    private long daysLeft;
    private float pricePerMonth;

    public SubscrType getType() {
        return type;
    }

    public void setType(SubscrType type) {
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

    public long getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(long daysLeft) {
        this.daysLeft = daysLeft;
    }

    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
}

