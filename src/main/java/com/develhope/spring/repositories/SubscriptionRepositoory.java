package com.develhope.spring.repositories;

import com.develhope.spring.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepositoory extends JpaRepository <Subscription, Long> {
}
