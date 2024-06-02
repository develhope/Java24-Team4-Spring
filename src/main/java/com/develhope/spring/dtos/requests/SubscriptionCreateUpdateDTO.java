package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.Subscription;

import java.time.LocalDateTime;

public class SubscriptionCreateUpdateDTO {

    private Subscription.SubscrType type;

    public SubscriptionCreateUpdateDTO() {
    }

    public SubscriptionCreateUpdateDTO(Subscription.SubscrType type) {
        this.type = type;
    }

    public Subscription.SubscrType getType() {
        return type;
    }

    public void setType(Subscription.SubscrType type) {
        this.type = type;
    }
}
