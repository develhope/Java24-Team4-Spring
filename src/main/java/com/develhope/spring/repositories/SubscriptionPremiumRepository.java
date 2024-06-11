package com.develhope.spring.repositories;

import com.develhope.spring.entities.Subscription;
import com.develhope.spring.entities.SubscriptionPremium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionPremiumRepository extends JpaRepository<SubscriptionPremium, Long> {
    List<Subscription> findByActiveTrue();

    List<Subscription> findByActiveFalse();
}
