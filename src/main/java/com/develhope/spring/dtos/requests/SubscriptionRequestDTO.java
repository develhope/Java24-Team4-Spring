package com.develhope.spring.dtos.requests;

import com.develhope.spring.entities.Subscription;

public class SubscriptionRequestDTO {

    private Subscription.SubscrType type;
    private Long listenerId;

    public SubscriptionRequestDTO() {
    }

    public SubscriptionRequestDTO(Subscription.SubscrType type, Long listenerId) {
        this.type = type;
        this.listenerId = listenerId;
    }

    public Subscription.SubscrType getType() {
        return type;
    }

    public void setType(Subscription.SubscrType type) {
        this.type = type;
    }

    public Long getListenerId() {
        return listenerId;
    }

    public void setListenerId(Long listenerId) {
        this.listenerId = listenerId;
    }
}
