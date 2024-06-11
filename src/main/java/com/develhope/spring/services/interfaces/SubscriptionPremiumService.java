package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.SubscriptionPremiumRequestDTO;
import com.develhope.spring.dtos.responses.SubscriptionPremiumResponseDTO;
import com.develhope.spring.entities.SubscriptionPremium;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface SubscriptionPremiumService {

    Optional<SubscriptionPremiumResponseDTO> getSubscriptionPremiumById(Long id);

    List<SubscriptionPremiumResponseDTO> getAllSubscriptionsPremium();

    List<SubscriptionPremiumResponseDTO> getAllByActivePremium(Boolean active);

    Optional<SubscriptionPremiumResponseDTO> createSubscriptionPremium(SubscriptionPremiumRequestDTO request, Long listenerID);

    Optional<SubscriptionPremiumResponseDTO> updateSubscriptionPremium(Long id, SubscriptionPremiumRequestDTO request);

    Optional<SubscriptionPremium> deleteSubscriptionByIdPremium(Long id);

    void deleteAllSubscriptionsPremium();

    Optional<SubscriptionPremiumResponseDTO> enableSubscriptionPremium(Long id);

    Optional<SubscriptionPremiumResponseDTO> disableSubscriptionPremium(Long id);
}
