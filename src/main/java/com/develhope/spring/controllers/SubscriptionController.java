package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody SubscriptionCreateUpdateDTO subscriptionCreateDTO, @RequestParam Long userID) {
        Subscription subscription = subscriptionService.createSubscription(subscriptionCreateDTO, userID);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable Long id, @RequestBody SubscriptionCreateUpdateDTO subscriptionUpdateDTO) {
        Subscription subscription = subscriptionService.updateSubscription(id, subscriptionUpdateDTO);
        return subscription != null ? ResponseEntity.ok(subscription) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        Subscription subscription = subscriptionService.deleteSubscriptionById(id);
        return subscription != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllSubscriptions() {
        subscriptionService.deleteAllSubscriptions();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable Long id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        return subscription != null ? ResponseEntity.ok(subscription) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }
}
