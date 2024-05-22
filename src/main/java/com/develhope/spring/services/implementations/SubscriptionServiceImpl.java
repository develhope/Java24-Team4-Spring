package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.repositories.SubscriptionRepositoory;
import com.develhope.spring.repositories.UserRepository;
import com.develhope.spring.services.SubscriptionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {


   private final SubscriptionRepositoory subscrRepository;
   private final UserRepository userRepository;
   private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepositoory subscrRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.subscrRepository = subscrRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Subscription createSubscription(SubscriptionCreateUpdateDTO subscriptionCreateUpdateDTO, Long UserID) {

        Subscription subscription = modelMapper.map(subscriptionCreateUpdateDTO, Subscription.class);
        subscription.setListener(userRepository.findById(UserID).get());

        return subscrRepository.save(subscription);

    }

    @Override
    public Subscription updateSubscription(Long subscrID, SubscriptionCreateUpdateDTO createUpdateDTO) {

        Subscription existingSubscription = subscrRepository.findById(subscrID)
                .orElse(null);

        if(existingSubscription == null) {
            return null;
        }

        Subscription updateSubscr = modelMapper.map(createUpdateDTO, Subscription.class);

        if (updateSubscr.getType() != null) {
            existingSubscription.setType(updateSubscr.getType());
        }
        if (updateSubscr.getPrice() != null) {
            existingSubscription.setPrice(updateSubscr.getPrice());
        }
        if (updateSubscr.getStart() != null) {
            existingSubscription.setStart(updateSubscr.getStart());
        }
        if (updateSubscr.getEnd() != null) {
            existingSubscription.setEnd(updateSubscr.getEnd());
        }

        return subscrRepository.saveAndFlush(existingSubscription);
    }

    @Override
    public Subscription deleteSubscriptionById(Long id) {
        Subscription subscription = subscrRepository.findById(id).orElse(null);
        subscrRepository.delete(subscription);

        return subscription;
    }

    @Override
    public void deleteAllSubscriptions() {
        subscrRepository.deleteAll();
    }


    @Override
    public Subscription getSubscriptionById(Long id) {
        return subscrRepository.findById(id).orElse(null);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscrRepository.findAll();
    }
}


