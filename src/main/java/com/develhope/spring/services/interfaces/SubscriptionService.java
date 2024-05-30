package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.dtos.responses.SubscriptionViewDTO;
import com.develhope.spring.entities.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubscriptionService {

    Optional<SubscriptionViewDTO> getSubscriptionById(Long id);

    List<SubscriptionViewDTO> getAllSubscriptions();

    List<SubscriptionViewDTO> getAllByActive(Boolean active);

    Optional<SubscriptionViewDTO> createSubscription(SubscriptionCreateUpdateDTO subscriptionCreateDTO, Long UserID);

    Optional<SubscriptionViewDTO> updateSubscription(Long id, SubscriptionCreateUpdateDTO createUpdateDTO);

    Optional<Subscription> deleteSubscriptionById(Long id);

    void deleteAllSubscriptions();

    Optional<SubscriptionViewDTO> enableSubscription(Long id);

    Optional<SubscriptionViewDTO> disableSubscription(Long id);

}
