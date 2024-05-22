package com.develhope.spring.entities;

import jakarta.persistence.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

//TODO come gestire subscription

@Entity
@Table(name = "subscriptions")
public class Subscription {

    public enum SubscrType {
        MONTHLY, HALF_YEAR, ANNUAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(mappedBy = "subscription")
    private User listener;
    private SubscrType type;
    private Float price;
    private LocalDateTime start;
    private LocalDateTime end;

    public Subscription() {
    }

    public Subscription(Long id, User listener, SubscrType type, Float price, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.listener = listener;
        this.type = type;
        this.price = price;
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
