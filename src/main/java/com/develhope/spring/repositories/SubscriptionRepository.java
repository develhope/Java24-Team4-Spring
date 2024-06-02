package com.develhope.spring.repositories;

import com.develhope.spring.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository <Subscription, Long> {

   List<Subscription> findByActiveTrue();

   List<Subscription> findByActiveFalse();
}
