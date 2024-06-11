package com.develhope.spring.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions_premium")
public class SubscriptionPremium extends Subscription {

    private String premiumFeatures;

    public SubscriptionPremium() {
        super();
    }

    public SubscriptionPremium(Long id,
                               Listener listener,
                               SubscrType type,
                               Float totalPrice,
                               LocalDateTime start,
                               LocalDateTime end,
                               Boolean active,
                               String premiumFeatures,
                               Duration duration) {
        super(id, listener, type, totalPrice, start, end, active);
        this.premiumFeatures = premiumFeatures;
    }


    public String getPremiumFeatures() {
        return premiumFeatures;
    }

    public void setPremiumFeatures(String premiumFeatures) {
        this.premiumFeatures = premiumFeatures;
    }
}


// Questa soluzione crea una relazione di ereditarietà tra Subscription e SubscriptionPremium.
// La classe SubscriptionPremium estende Subscription e aggiunge un campo specifico per le funzionalità premium (premiumFeatures).
// Abbiamo anche cambiato la strategia di ereditarietà a JOINED nella classe Subscription per gestire l'ereditarietà nel database.
