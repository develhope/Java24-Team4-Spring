package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.dtos.responses.SubscriptionViewDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.services.interfaces.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubscriptionById(@PathVariable Long id) {
        Optional<SubscriptionViewDTO> subscription = subscriptionService.getSubscriptionById(id);

        return subscription.isPresent() ? ResponseEntity.ok(subscription) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");
    }

    @GetMapping
    public ResponseEntity<?> getAllSubscriptions() {
        List<SubscriptionViewDTO> subscrList = subscriptionService.getAllSubscriptions();

        return ! subscrList.isEmpty() ? ResponseEntity.ok(subscrList) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Subscriptions list is empty!");
    }

    @GetMapping
    public ResponseEntity<?> getAllByActive(@RequestParam Boolean active) {
        List<SubscriptionViewDTO> advList = subscriptionService.getAllByActive(active);

        return ! advList.isEmpty() ? ResponseEntity.ok(advList) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Subscriptions list" + "(Active = " + active +
                        ") is empty");
    }

    @PostMapping
    public ResponseEntity<?> createSubscription(
            @RequestBody SubscriptionCreateUpdateDTO subscriptionCreateDTO,
            @RequestParam Long userID
    ) {
        SubscriptionViewDTO subscription = subscriptionService.createSubscription(subscriptionCreateDTO, userID);

        return subscription == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(subscription);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscription(
            @PathVariable Long id,
            @RequestBody SubscriptionCreateUpdateDTO subscriptionUpdateDTO
    ) {
        Optional<SubscriptionViewDTO> subscription = subscriptionService.updateSubscription(id, subscriptionUpdateDTO);

        return subscription.isPresent() ? ResponseEntity.ok(subscription) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");
    }

    @PatchMapping("/enable_subscr/{id}")
    public ResponseEntity<?> enableSubscription(@PathVariable Long id){
        Optional<SubscriptionViewDTO> subscription = subscriptionService.enableSubscription(id);

        return subscription.isPresent() ? ResponseEntity.ok(subscription) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");

    }


    @PatchMapping("/disable_subscr/{id}")
    public ResponseEntity<?> disableSubscription(@PathVariable Long id){
        Optional<SubscriptionViewDTO> subscription = subscriptionService.enableSubscription(id);

        return subscription.isPresent() ? ResponseEntity.ok(subscription) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable Long id) {
        Optional<Subscription> subscription = subscriptionService.deleteSubscriptionById(id);

        return subscription.isPresent() ? ResponseEntity.noContent().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllSubscriptions() {
        subscriptionService.deleteAllSubscriptions();

        return ResponseEntity.noContent().build();
    }

}
