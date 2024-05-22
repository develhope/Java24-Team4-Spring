package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.Subscription;

import java.time.LocalDateTime;

public class SubscriptionCreateUpdateDTO {

    private Subscription.SubscrType type;
    private Float price;
    private LocalDateTime start;
    private LocalDateTime end;

    public SubscriptionCreateUpdateDTO() {
    }

    public SubscriptionCreateUpdateDTO(Long userId, Subscription.SubscrType type, Float price, LocalDateTime start, LocalDateTime end) {

        this.type = type;
        this.price = price;
        this.start = start;
        this.end = end;

    }

    public Subscription.SubscrType getType() {
        return type;
    }

    public void setType(Subscription.SubscrType type) {
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
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
}
