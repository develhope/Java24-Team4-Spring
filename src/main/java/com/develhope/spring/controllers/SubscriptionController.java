package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.SubscriptionRequestDTO;
import com.develhope.spring.dtos.requests.SubscriptionUpdateDTO;
import com.develhope.spring.dtos.responses.SubscriptionResponseDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.models.Response;
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
    public ResponseEntity<Response> getSubscriptionById(@PathVariable Long id) {
        SubscriptionResponseDTO subscription = subscriptionService.getSubscriptionById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscription found.", subscription)
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllSubscriptions() {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getAllSubscriptions();

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscriptions found: " + subscriptions.size(), subscriptions)
        );
    }

    @GetMapping("/findActive")
    public ResponseEntity<Response> getAllByActive(@RequestParam Boolean active) {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getAllByActive(active);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscriptions(active = " + active +
                        ") found: " + subscriptions.size(), subscriptions)
        );
    }

    @PostMapping
    public ResponseEntity<Response> createSubscription(@RequestBody SubscriptionRequestDTO requestDTO) {
        SubscriptionResponseDTO subscription = subscriptionService.createSubscription(requestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscription created successfully.", subscription)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> updateSubscription(
            @PathVariable Long id,
            @RequestBody SubscriptionUpdateDTO updateDTO
    ) {
        SubscriptionResponseDTO subscription = subscriptionService.updateSubscription(id, updateDTO);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscription updated successfully.", subscription)
        );
    }

    @PatchMapping("/enableSubscr/{id}")
    public ResponseEntity<Response> enableSubscription(@PathVariable Long id) {
        SubscriptionResponseDTO subscription = subscriptionService.enableSubscription(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscription enabled successfully.", subscription)
        );
    }


    @PatchMapping("/disableSubscr/{id}")
    public ResponseEntity<Response> disableSubscription(@PathVariable Long id) {
        SubscriptionResponseDTO subscription = subscriptionService.disableSubscription(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscription disabled successfully.", subscription)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteSubscription(@PathVariable Long id) {
        SubscriptionResponseDTO subscription = subscriptionService.deleteSubscriptionById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Subscription deleted successfully.", subscription)
        );
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteAllSubscriptions() {
        subscriptionService.deleteAllSubscriptions();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new Response(HttpStatus.NO_CONTENT.value(), "All subscriptions deleted.")
        );
    }

}
