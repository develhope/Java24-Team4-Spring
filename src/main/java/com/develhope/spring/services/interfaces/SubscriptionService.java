package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SubscriptionRequestDTO;
import com.develhope.spring.dtos.responses.SubscriptionResponseDTO;
import com.develhope.spring.entities.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubscriptionService {

    Optional<SubscriptionResponseDTO> getSubscriptionById(Long id);

    List<SubscriptionResponseDTO> getAllSubscriptions();

    List<SubscriptionResponseDTO> getAllByActive(Boolean active);

    Optional<SubscriptionResponseDTO> createSubscription(SubscriptionRequestDTO subscriptionCreateDTO, Long UserID);

    Optional<SubscriptionResponseDTO> updateSubscription(Long id, SubscriptionRequestDTO createUpdateDTO);

    Optional<Subscription> deleteSubscriptionById(Long id);

    void deleteAllSubscriptions();

    Optional<SubscriptionResponseDTO> enableSubscription(Long id);

    Optional<SubscriptionResponseDTO> disableSubscription(Long id);

}
