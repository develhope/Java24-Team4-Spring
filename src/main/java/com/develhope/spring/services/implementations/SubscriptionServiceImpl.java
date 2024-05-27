package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.dtos.responses.SubscriptionViewDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.entities.User;
import com.develhope.spring.repositories.SubscriptionRepository;

import com.develhope.spring.repositories.UserRepository;
import com.develhope.spring.services.interfaces.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {


    private final SubscriptionRepository subscrRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscrRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.subscrRepository = subscrRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SubscriptionViewDTO createSubscription(SubscriptionCreateUpdateDTO request, Long userID) {
        Subscription toSave = modelMapper.map(request, Subscription.class);

        User listener = userRepository.findById(userID).orElse(null);
        if (listener == null) {
            return null;
        }

        toSave.setListener(listener);
        toSave.setTotalPrice(toSave.getType().getTotalPrice());
        toSave.setStart(setInitialDate());
        toSave.setEnd(setFinalDate(toSave));

        Subscription saved = subscrRepository.saveAndFlush(toSave);

        SubscriptionViewDTO response = modelMapper.map(saved, SubscriptionViewDTO.class);

        setCalculableFieldsViewDTO(response);

        return response;
    }

    @Override
    public Optional<SubscriptionViewDTO> updateSubscription(Long subscrID, SubscriptionCreateUpdateDTO request) {

        return subscrRepository.findById(subscrID).map(subscription -> {
            modelMapper.map(request, subscription);
            long daysPassed = daysPassedSinceStart(subscription.getStart());

            subscription.setStart(setInitialDate());
            subscription.setEnd(setFinalDate(subscription));
            subscrRepository.saveAndFlush(subscription);
            SubscriptionViewDTO response = modelMapper.map(subscription, SubscriptionViewDTO.class);
            setCalculableFieldsViewDTO(response);
            return response;
        });
    }

    @Override
    public Optional<Subscription> deleteSubscriptionById(Long id) {
        return subscrRepository.findById(id).map(subscription -> {
            subscrRepository.deleteById(id);
            return subscription;
        });
    }

    @Override
    public void deleteAllSubscriptions() {
        subscrRepository.deleteAll();
    }


    @Override
    public Optional<SubscriptionViewDTO> getSubscriptionById(Long id) {
        return subscrRepository.findById(id).map(subscription -> {
            SubscriptionViewDTO response = modelMapper.map(subscription, SubscriptionViewDTO.class);
            setCalculableFieldsViewDTO(response);
            return response;
        });
    }

    @Override
    public List<SubscriptionViewDTO> getAllSubscriptions() {
        return   subscrRepository.findAll().stream().map(subscription -> {
            SubscriptionViewDTO found = modelMapper.map(subscription, SubscriptionViewDTO.class);
            setCalculableFieldsViewDTO(found);
            return found;
        }).collect(Collectors.toList());
    }

    @Override
    public LocalDateTime setInitialDate() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDateTime setFinalDate(Subscription subscription) {
        return subscription.getStart().plusDays((subscription.getType().getDuration().toDays()));
    }

    @Override
    public long daysPassedSinceStart(LocalDateTime start) {
        return Duration.between(start, LocalDateTime.now().plusDays(1)).toDays();
    }

    @Override
    public long daysUntilTheEnd(LocalDateTime end) {
        return Duration.between(LocalDateTime.now().minusDays(1), end).toDays();
    }

    @Override
    public float calculatePricePerMonth(SubscriptionViewDTO subscriptionViewDTO) {
        float pricePerMonth = subscriptionViewDTO.getTotalPrice() /
                ((float) subscriptionViewDTO.getType().getDuration().toDays() / 30);
        DecimalFormat df = new DecimalFormat("#.##");
        return Float.parseFloat(df.format(pricePerMonth));
    }

    @Override
    public void setCalculableFieldsViewDTO(SubscriptionViewDTO dtoView) {
        dtoView.setDaysLeft(daysUntilTheEnd(dtoView.getEnd()));
        dtoView.setPricePerMonth(calculatePricePerMonth(dtoView));
    }

}


