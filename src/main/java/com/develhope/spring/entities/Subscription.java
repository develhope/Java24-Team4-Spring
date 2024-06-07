package com.develhope.spring.entities;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    public enum SubscrType {
        MONTHLY(20f, Duration.ofDays(30)),
        HALF_YEAR(100f, Duration.ofDays(180)),
        ANNUAL(180f, Duration.ofDays(365));

        private final float totalPrice;
        private final Duration duration;

        SubscrType(float totalPrice, Duration duration) {
            this.totalPrice = totalPrice;
            this.duration = duration;
        }

        public float getTotalPrice() {
            return totalPrice;
        }

        public Duration getDuration() {
            return duration;
        }
    }

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", nullable = false)
    private Listener listener;

    @Enumerated(EnumType.STRING)
    private SubscrType type;
    private Float totalPrice;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean active;

    public Subscription() {
    }

    public Subscription(Long id, Listener listener, SubscrType type, Float totalPrice, LocalDateTime start, LocalDateTime end, Boolean active) {
        this.id = id;
        this.listener = listener;
        this.type = type;
        this.totalPrice = totalPrice;
        this.start = start;
        this.end = end;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public SubscrType getType() {
        return type;
    }

    public void setType(SubscrType type) {
        this.type = type;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
