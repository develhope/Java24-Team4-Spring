package com.develhope.spring.dtos.responses;

public class ListenerDetailsDTO {

    private SubscriptionWithoutListenerDTO subscription;

    public ListenerDetailsDTO(SubscriptionWithoutListenerDTO subscription) {
        this.subscription = subscription;
    }

    public ListenerDetailsDTO() {
    }

    public SubscriptionWithoutListenerDTO getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionWithoutListenerDTO subscription) {
        this.subscription = subscription;
    }
}
