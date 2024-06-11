package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.SubscriptionPremiumRequestDTO;
import com.develhope.spring.dtos.responses.SubscriptionPremiumResponseDTO;
import com.develhope.spring.entities.SubscriptionPremium;
import com.develhope.spring.services.interfaces.SubscriptionPremiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/subscription-premium")
public class SubscriptionPremiumController {

    private final SubscriptionPremiumService subscriptionPremiumService;

    @Autowired
    public SubscriptionPremiumController(SubscriptionPremiumService subscriptionPremiumService) {
        this.subscriptionPremiumService = subscriptionPremiumService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPremiumResponseDTO> getSubscriptionPremiumById(@PathVariable("id") Long id) {
        Optional<SubscriptionPremiumResponseDTO> subscription = subscriptionPremiumService.getSubscriptionPremiumById(id);
        return subscription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubscriptionPremiumResponseDTO>> getAllSubscriptionsPremium() {
        List<SubscriptionPremiumResponseDTO> subscriptions = subscriptionPremiumService.getAllSubscriptionsPremium();
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/by-active")
    public ResponseEntity<List<SubscriptionPremiumResponseDTO>> getAllByActivePremium(@RequestParam("active") Boolean active) {
        List<SubscriptionPremiumResponseDTO> subscriptions = subscriptionPremiumService.getAllByActivePremium(active);
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping("/create/{listenerId}")
    public ResponseEntity<SubscriptionPremiumResponseDTO> createSubscriptionPremium(@RequestBody SubscriptionPremiumRequestDTO request, @PathVariable("listenerId") Long listenerId) {
        Optional<SubscriptionPremiumResponseDTO> subscription = subscriptionPremiumService.createSubscriptionPremium(request, listenerId);
        return subscription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SubscriptionPremiumResponseDTO> updateSubscriptionPremium(@PathVariable("id") Long id, @RequestBody SubscriptionPremiumRequestDTO request) {
        Optional<SubscriptionPremiumResponseDTO> subscription = subscriptionPremiumService.updateSubscriptionPremium(id, request);
        return subscription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscriptionPremiumById(@PathVariable("id") Long id) {
        Optional<SubscriptionPremium> subscription = subscriptionPremiumService.deleteSubscriptionByIdPremium(id);
        if (subscription.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllSubscriptionsPremium() {
        subscriptionPremiumService.deleteAllSubscriptionsPremium();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/enable/{id}")
    public ResponseEntity<SubscriptionPremiumResponseDTO> enableSubscriptionPremium(@PathVariable("id") Long id) {
        Optional<SubscriptionPremiumResponseDTO> subscription = subscriptionPremiumService.enableSubscriptionPremium(id);
        return subscription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/disable/{id}")
    public ResponseEntity<SubscriptionPremiumResponseDTO> disableSubscriptionPremium(@PathVariable("id") Long id) {
        Optional<SubscriptionPremiumResponseDTO> subscription = subscriptionPremiumService.disableSubscriptionPremium(id);
        return subscription.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
