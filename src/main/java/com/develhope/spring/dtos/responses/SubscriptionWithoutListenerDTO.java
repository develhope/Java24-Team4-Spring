package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Subscription;

import java.time.LocalDateTime;

public class SubscriptionWithoutListenerDTO {

    private Long id;
    private Subscription.SubscrType type;
    private LocalDateTime start;
    private LocalDateTime end;
    private long daysLeft;
    private Float totalPrice;
    private float pricePerMonth;
    private Boolean active;

    public SubscriptionWithoutListenerDTO() {
    }

    public SubscriptionWithoutListenerDTO(Long id, Subscription.SubscrType type, LocalDateTime start, LocalDateTime end, long daysLeft, Float totalPrice, float pricePerMonth, Boolean active) {
        this.id = id;
        this.type = type;
        this.start = start;
        this.end = end;
        this.daysLeft = daysLeft;
        this.totalPrice = totalPrice;
        this.pricePerMonth = pricePerMonth;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subscription.SubscrType getType() {
        return type;
    }

    public void setType(Subscription.SubscrType type) {
        this.type = type;
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

    public long getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(long daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
