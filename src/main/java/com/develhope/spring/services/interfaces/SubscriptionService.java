package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.dtos.responses.SubscriptionViewDTO;
import com.develhope.spring.entities.Subscription;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public interface SubscriptionService {

    SubscriptionViewDTO createSubscription(SubscriptionCreateUpdateDTO subscriptionCreateDTO, Long UserID);

    Optional<SubscriptionViewDTO> updateSubscription(Long subscrID, SubscriptionCreateUpdateDTO createUpdateDTO);

    Optional<Subscription> deleteSubscriptionById(Long id);

    void deleteAllSubscriptions();

    Optional<SubscriptionViewDTO> getSubscriptionById(Long id);

    List<SubscriptionViewDTO> getAllSubscriptions();

    LocalDateTime setInitialDate();

    LocalDateTime setFinalDate(Subscription subscription);

    long daysPassedSinceStart(LocalDateTime start);

    long daysUntilTheEnd(LocalDateTime end);

    float calculatePricePerMonth(SubscriptionViewDTO subscriptionViewDTO);

    void setCalculableFieldsViewDTO(SubscriptionViewDTO dtoView);
}