package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.Subscription;

public class SubscriptionRequestDTO {

    private Subscription.SubscrType type;

    public SubscriptionRequestDTO() {
    }

    public SubscriptionRequestDTO(Subscription.SubscrType type) {
        this.type = type;
    }

    public Subscription.SubscrType getType() {
        return type;
    }

    public void setType(Subscription.SubscrType type) {
        this.type = type;
    }
}
