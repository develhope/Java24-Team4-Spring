package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SubscriptionCreateDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.services.SubscriptionService;
import org.modelmapper.ModelMapper;

import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {

    private ModelMapper modelMapper;

    public SubscriptionServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public Subscription createSubscription(SubscriptionCreateDTO subscriptionCreateDTO, Long UserID) {

    }

    @Override
    public Subscription updateSubscription(Subscription subscription) {
        return null;
    }

    @Override
    public void deleteSubscription(Subscription subscription) {

    }

    @Override
    public void deleteAllSubscriptions() {

    }

    @Override
    public Subscription getSubscriptionById(Long id) {
        return null;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return List.of();
    }
}
