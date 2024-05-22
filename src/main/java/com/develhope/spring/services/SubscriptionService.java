package com.develhope.spring.services;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.entities.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {

    public Subscription createSubscription(SubscriptionCreateUpdateDTO subscriptionCreateDTO, Long UserID);

    public Subscription updateSubscription(Long subscrID, SubscriptionCreateUpdateDTO createUpdateDTO);

    public Subscription deleteSubscriptionById(Long id);

    public void deleteAllSubscriptions();

    public Subscription getSubscriptionById(Long id);

    public List<Subscription> getAllSubscriptions();


}
