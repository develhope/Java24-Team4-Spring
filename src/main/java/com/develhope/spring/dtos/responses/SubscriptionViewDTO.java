package com.develhope.spring.dtos.responses;

import com.develhope.spring.entities.Subscription;
import com.develhope.spring.entities.User;

import java.time.LocalDateTime;

public class SubscriptionViewDTO {
    private Long id;
    private User listener;
    private Subscription.SubscrType type;
    private LocalDateTime start;
    private LocalDateTime end;
    private long daysLeft;
    private Float totalPrice;
    private float pricePerMonth;

    public SubscriptionViewDTO() {
    }

    public SubscriptionViewDTO(Long id, User listener, Subscription.SubscrType type, LocalDateTime start, LocalDateTime end, long daysLeft, Float totalPrice, float pricePerMonth) {
        this.id = id;
        this.listener = listener;
        this.type = type;
        this.start = start;
        this.end = end;
        this.daysLeft = daysLeft;
        this.totalPrice = totalPrice;
        this.pricePerMonth = pricePerMonth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(long daysLeft) {
        this.daysLeft = daysLeft;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public Subscription.SubscrType getType() {
        return type;
    }

    public void setType(Subscription.SubscrType type) {
        this.type = type;
    }

    public User getListener() {
        return listener;
    }

    public void setListener(User listener) {
        this.listener = listener;
    }
}