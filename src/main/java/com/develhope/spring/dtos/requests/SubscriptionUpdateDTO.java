package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.Subscription;

public class SubscriptionUpdateDTO {

    private Subscription.SubscrType type;

    public SubscriptionUpdateDTO(Subscription.SubscrType type) {
        this.type = type;
    }

    public SubscriptionUpdateDTO() {
    }

    public Subscription.SubscrType getType() {
        return type;
    }

    public void setType(Subscription.SubscrType type) {
        this.type = type;
    }
}
