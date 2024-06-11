package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SubscriptionPremiumRequestDTO;
import com.develhope.spring.dtos.responses.SubscriptionPremiumResponseDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.entities.SubscriptionPremium;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.SubscriptionPremiumRepository;
import com.develhope.spring.services.interfaces.SubscriptionPremiumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionPremiumServiceImpl implements SubscriptionPremiumService {

    private final SubscriptionPremiumRepository subscrPremiumRepository;
    private final ListenerRepository listenerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionPremiumServiceImpl(
            SubscriptionPremiumRepository subscrPremiumRepository,
            ListenerRepository listenerRepository,
            ModelMapper modelMapper
    ) {
        this.subscrPremiumRepository = subscrPremiumRepository;
        this.listenerRepository = listenerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<SubscriptionPremiumResponseDTO> getSubscriptionPremiumById(Long id) {
        return subscrPremiumRepository.findById(id).map(subscription -> {
            SubscriptionPremiumResponseDTO response = modelMapper.map(subscription, SubscriptionPremiumResponseDTO.class);
            setCalculableFieldsViewDTO(response);
            return response;
        });
    }

    @Override
    public List<SubscriptionPremiumResponseDTO> getAllSubscriptionsPremium() {
        return subscrPremiumRepository.findAll().stream()
                .map(subscription -> {
                    SubscriptionPremiumResponseDTO found = modelMapper.map(subscription, SubscriptionPremiumResponseDTO.class);
                    setCalculableFieldsViewDTO(found);
                    return found;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionPremiumResponseDTO> getAllByActivePremium(Boolean active) {
        if (active) {
            return subscrPremiumRepository.findByActiveTrue().stream()
                    .map(subscription -> {
                        SubscriptionPremiumResponseDTO found = modelMapper.map(subscription, SubscriptionPremiumResponseDTO.class);
                        setCalculableFieldsViewDTO(found);
                        return found;
                    })
                    .collect(Collectors.toList());
        } else {
            return subscrPremiumRepository.findByActiveFalse().stream()
                    .map(subscription -> {
                        SubscriptionPremiumResponseDTO found = modelMapper.map(subscription, SubscriptionPremiumResponseDTO.class);
                        setCalculableFieldsViewDTO(found);
                        return found;
                    })
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Optional<SubscriptionPremiumResponseDTO> createSubscriptionPremium(SubscriptionPremiumRequestDTO request, Long listenerID) {
        return listenerRepository.findById(listenerID).map(listener -> {
            SubscriptionPremium toSave = modelMapper.map(request, SubscriptionPremium.class);
            toSave.setListener(listener);
            toSave.setTotalPrice(toSave.getType().getTotalPrice());
            setStartSetEnd(toSave);
            controlAndEnableSubscription(toSave);

            SubscriptionPremium saved = subscrPremiumRepository.saveAndFlush(toSave);
            SubscriptionPremiumResponseDTO response = modelMapper.map(saved, SubscriptionPremiumResponseDTO.class);
            setCalculableFieldsViewDTO(response);
            return response;
        });
    }

    @Override
    public Optional<SubscriptionPremiumResponseDTO> updateSubscriptionPremium(Long id, SubscriptionPremiumRequestDTO request) {
        return subscrPremiumRepository.findById(id).map(subscription -> {
            if (request.getType() != null && request.getType().getClass().equals(Subscription.SubscrType.class)) {
                modelMapper.map(request, subscription);
                setStartSetEnd(subscription);

                subscrPremiumRepository.saveAndFlush(subscription);
                SubscriptionPremiumResponseDTO response = modelMapper.map(subscription, SubscriptionPremiumResponseDTO.class);
                setCalculableFieldsViewDTO(response);
                return response;
            } else {
                throw new IllegalArgumentException("Invalid subscription type.");
            }
        });
    }




    @Override
    public Optional<SubscriptionPremium> deleteSubscriptionByIdPremium(Long id) {
        return subscrPremiumRepository.findById(id).map(subscription -> {
            subscrPremiumRepository.deleteById(id);
            return subscription;
        });
    }

    @Override
    public void deleteAllSubscriptionsPremium() {
        subscrPremiumRepository.deleteAll();
    }

    @Override
    public Optional<SubscriptionPremiumResponseDTO> enableSubscriptionPremium(Long id) {
        return subscrPremiumRepository.findById(id).map(subscr -> {
            subscr.setActive(true);
            subscr = subscrPremiumRepository.saveAndFlush(subscr);

            SubscriptionPremiumResponseDTO response = modelMapper.map(subscr, SubscriptionPremiumResponseDTO.class);
            setCalculableFieldsViewDTO(response);
            return response;
        });
    }

    @Override
    public Optional<SubscriptionPremiumResponseDTO> disableSubscriptionPremium(Long id) {
        return subscrPremiumRepository.findById(id).map(subscr -> {
            subscr.setActive(false);
            subscr = subscrPremiumRepository.saveAndFlush(subscr);

            SubscriptionPremiumResponseDTO response = modelMapper.map(subscr, SubscriptionPremiumResponseDTO.class);
            setCalculableFieldsViewDTO(response);
            return response;
        });
    }

    private LocalDateTime setInitialDate() {
        return LocalDateTime.now();
    }

    private void setStartSetEnd(SubscriptionPremium subscription) {
        subscription.setStart(setInitialDate());
        subscription.setEnd(setFinalDate(subscription));
    }

    private void controlAndEnableSubscription(SubscriptionPremium subscription) {
        subscription.setActive(!LocalDateTime.now().isBefore(subscription.getStart()));
    }

    private LocalDateTime setFinalDate(SubscriptionPremium subscription) {
        return subscription.getStart().plusDays((subscription.getType().getDuration().toDays()));
    }

    private long daysPassedSinceStart(LocalDateTime start) {
        return Duration.between(start, LocalDateTime.now().plusDays(1)).toDays();
    }

    private long daysUntilTheEnd(LocalDateTime end) {
        return Duration.between(LocalDateTime.now().minusDays(1), end).toDays();
    }

    private float calculatePricePerMonth(SubscriptionPremiumResponseDTO subscriptionResponseDTO) {
        float totalPrice = subscriptionResponseDTO.getTotalPrice();
        long durationInDays = subscriptionResponseDTO.getType().getDuration().toDays();
        float pricePerMonth = totalPrice / ((float) durationInDays / 30);
        return Math.round(pricePerMonth * 100.0f) / 100.0f;
    }

    private void setCalculableFieldsViewDTO(SubscriptionPremiumResponseDTO dtoView) {
        dtoView.setDaysLeft(daysUntilTheEnd(dtoView.getEnd()));
        dtoView.setPricePerMonth(calculatePricePerMonth(dtoView));
    }

}

