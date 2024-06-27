package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SubscriptionRequestDTO;
import com.develhope.spring.dtos.requests.SubscriptionUpdateDTO;
import com.develhope.spring.dtos.responses.SubscriptionResponseDTO;
import com.develhope.spring.entities.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubscriptionService {

    SubscriptionResponseDTO getSubscriptionById(Long id);

    List<SubscriptionResponseDTO> getAllSubscriptions();

    List<SubscriptionResponseDTO> getAllByActive(Boolean active);

    SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO request);

    SubscriptionResponseDTO updateSubscription(Long id, SubscriptionUpdateDTO request);

    SubscriptionResponseDTO deleteSubscriptionById(Long id);

    void deleteAllSubscriptions();

    SubscriptionResponseDTO enableSubscription(Long id);

    SubscriptionResponseDTO disableSubscription(Long id);

}
