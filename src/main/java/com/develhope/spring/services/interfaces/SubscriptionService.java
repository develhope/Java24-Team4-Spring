package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.entities.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubscriptionService {

    Subscription createSubscription(SubscriptionCreateUpdateDTO subscriptionCreateDTO, Long UserID);

    Subscription updateSubscription(Long subscrID, SubscriptionCreateUpdateDTO createUpdateDTO);

    Subscription deleteSubscriptionById(Long id);

    void deleteAllSubscriptions();

    Subscription getSubscriptionById(Long id);

    List<Subscription> getAllSubscriptions();

}
