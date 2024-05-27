package com.develhope.spring.repositories;

import com.develhope.spring.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository <Subscription, Long> {
}
