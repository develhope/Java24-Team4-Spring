package com.develhope.spring.entities;

import jakarta.persistence.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;


// TODO CONTROLLARE RELAZIONI

@Entity
@Table(name = "subscriptions")
public class Subscription {

    public enum SubscrType {

        MONTHLY(20f),
        HALF_YEAR(100f),
        ANNUAL(180f);

        private final float price;

        SubscrType(float price) {
            this.price = price;
        }

        public float getPrice() {
            return price;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(mappedBy = "subscription")
    private User listener;
    private SubscrType type;
    private Float price;

    //TODO IMPOSTARE START E END IN BASE AL TIPO DI SOTTOSCRIZIONE E DATA INIZIALE
    private LocalDateTime start;
    private LocalDateTime end;

    public Subscription() {
    }

    public Subscription(Long id, User listener, SubscrType type, Float price, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.listener = listener;
        this.type = type;
        this.price = getType().getPrice();
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getListener() {
        return listener;
    }

    public void setListener(User listener) {
        this.listener = listener;
    }

    public SubscrType getType() {
        return type;
    }

    public void setType(SubscrType type) {
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
