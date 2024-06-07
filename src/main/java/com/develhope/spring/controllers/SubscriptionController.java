package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.SubscriptionRequestDTO;
import com.develhope.spring.dtos.responses.SubscriptionResponseDTO;
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
        Optional<SubscriptionResponseDTO> subscription = subscriptionService.getSubscriptionById(id);

        return subscription.isPresent() ? ResponseEntity.ok(subscription) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");
    }

    @GetMapping
    public ResponseEntity<?> getAllSubscriptions() {
        List<SubscriptionResponseDTO> subscrList = subscriptionService.getAllSubscriptions();

        return ! subscrList.isEmpty() ? ResponseEntity.ok(subscrList) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Subscriptions list is empty!");
    }

    @GetMapping("/findActive")
    public ResponseEntity<?> getAllByActive(@RequestParam Boolean active) {
        List<SubscriptionResponseDTO> advList = subscriptionService.getAllByActive(active);

        return ! advList.isEmpty() ? ResponseEntity.ok(advList) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Subscriptions list" + "(Active = " + active +
                        ") is empty");
    }

    @PostMapping
    public ResponseEntity<?> createSubscription(
            @RequestBody SubscriptionRequestDTO subscriptionCreateDTO,
            @RequestParam Long userID
    ) {
        Optional <SubscriptionResponseDTO> subscription = subscriptionService.createSubscription(subscriptionCreateDTO, userID);

        return subscription.isPresent() ?  ResponseEntity.ok(subscription):  ResponseEntity.badRequest().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubscription(
            @PathVariable Long id,
            @RequestBody SubscriptionRequestDTO subscriptionUpdateDTO
    ) {
        Optional<SubscriptionResponseDTO> subscription = subscriptionService.updateSubscription(id, subscriptionUpdateDTO);

        return subscription.isPresent() ? ResponseEntity.ok(subscription) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");
    }

    @PatchMapping("/enableSubscr/{id}")
    public ResponseEntity<?> enableSubscription(@PathVariable Long id){
        Optional<SubscriptionResponseDTO> subscription = subscriptionService.enableSubscription(id);

        return subscription.isPresent() ? ResponseEntity.ok(subscription) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found!");

    }

    // TODO METTERE SUBSCR. IN PAUSA SE NON ATTIVO
    @PatchMapping("/disableSubscr/{id}")
    public ResponseEntity<?> disableSubscription(@PathVariable Long id){
        Optional<SubscriptionResponseDTO> subscription = subscriptionService.disableSubscription(id);

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
