package com.develhope.spring.repositories;

import com.develhope.spring.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepositoory extends JpaRepository <Subscription, Long> {
}
