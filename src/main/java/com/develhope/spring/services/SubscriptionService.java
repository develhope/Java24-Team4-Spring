package com.develhope.spring.services;

import com.develhope.spring.dtos.requests.SubscriptionCreateDTO;
import com.develhope.spring.entities.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {

    public Subscription createSubscription(SubscriptionCreateDTO subscriptionCreateDTO, Long UserID);

    public Subscription updateSubscription(Subscription subscription);

    public void deleteSubscription(Subscription subscription);

    public void deleteAllSubscriptions();

    public Subscription getSubscriptionById(Long id);

    public List<Subscription> getAllSubscriptions();


}
