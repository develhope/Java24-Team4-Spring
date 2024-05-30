package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SubscriptionCreateUpdateDTO;
import com.develhope.spring.dtos.responses.SubscriptionViewDTO;
import com.develhope.spring.entities.Subscription;
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

    //TODO ADD VALIDATION(VALIDATION DIPEND. & EXCEPTION HANDLER)
    //TODO CHANGE USER TI LISTENER!

    private final SubscriptionRepository subscrRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionServiceImpl(
            SubscriptionRepository subscrRepository,
            UserRepository userRepository,
            ModelMapper modelMapper
    ) {
        this.subscrRepository = subscrRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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

        return subscrRepository.findAll().stream().map(subscription -> {
            SubscriptionViewDTO found = modelMapper.map(subscription, SubscriptionViewDTO.class);
            setCalculableFieldsViewDTO(found);

            return found;

        }).collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionViewDTO> getAllByActive(Boolean active) {

        if (active) {
            return subscrRepository.findByActiveTrue().stream().map(subscription -> {
                SubscriptionViewDTO found = modelMapper.map(subscription, SubscriptionViewDTO.class);
                setCalculableFieldsViewDTO(found);

                return found;

            }).collect(Collectors.toList());
        } else {
            return subscrRepository.findByActiveFalse().stream().map(subscription -> {
                SubscriptionViewDTO found = modelMapper.map(subscription, SubscriptionViewDTO.class);
                setCalculableFieldsViewDTO(found);

                return found;

            }).collect(Collectors.toList());
        }
    }

    @Override
    public Optional<SubscriptionViewDTO> createSubscription(SubscriptionCreateUpdateDTO request, Long userID) {


        return userRepository.findById(userID).map(user -> {
            Subscription toSave = modelMapper.map(request, Subscription.class);

            toSave.setListener(user);
            toSave.setTotalPrice(toSave.getType().getTotalPrice());
            setStartSetEnd(toSave);

            Subscription saved = subscrRepository.saveAndFlush(toSave);
            SubscriptionViewDTO response = modelMapper.map(saved, SubscriptionViewDTO.class);

            setCalculableFieldsViewDTO(response);
            return response;
        });

    }

    @Override
    public Optional<SubscriptionViewDTO> updateSubscription(Long id, SubscriptionCreateUpdateDTO request) {

        return subscrRepository.findById(id).map(subscription -> {
            modelMapper.map(request, subscription);

            //TODO FORSE BISOGNO CAMBIARE LA LOGICA DI CALCOLO START/END DATE DURANTE AGGIORNAMENTO ?
            setStartSetEnd(subscription);

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
    public Optional<SubscriptionViewDTO> enableSubscription(Long id) {

        return subscrRepository.findById(id).map(subscr -> {

            subscr.setActive(true);
            subscrRepository.saveAndFlush(subscr);

            SubscriptionViewDTO response = modelMapper.map(subscr, SubscriptionViewDTO.class);
            setCalculableFieldsViewDTO(response);

            return response;
        });
    }

    @Override
    public Optional<SubscriptionViewDTO> disableSubscription(Long id) {

        return subscrRepository.findById(id).map(subscr -> {

            subscr.setActive(false);
            subscrRepository.saveAndFlush(subscr);

            SubscriptionViewDTO response = modelMapper.map(subscr, SubscriptionViewDTO.class);
            setCalculableFieldsViewDTO(response);

            return response;
        });
    }


    private LocalDateTime setInitialDate() {
        return LocalDateTime.now();
    }


    private void setStartSetEnd(Subscription subscription) {
        subscription.setStart(setInitialDate());
        subscription.setEnd(setFinalDate(subscription));
    }


    private void controlAndEnableSubscription(Subscription subscription) {
        subscription.setActive(!LocalDateTime.now().isBefore(subscription.getStart()));
    }

    private LocalDateTime setFinalDate(Subscription subscription) {

        return subscription.getStart().plusDays((subscription.getType().getDuration().toDays()));
    }

    private long daysPassedSinceStart(LocalDateTime start) {

        return Duration.between(start, LocalDateTime.now().plusDays(1)).toDays();
    }

    private long daysUntilTheEnd(LocalDateTime end) {

        return Duration.between(LocalDateTime.now().minusDays(1), end).toDays();
    }

    private float calculatePricePerMonth(SubscriptionViewDTO subscriptionViewDTO) {

        float pricePerMonth = subscriptionViewDTO.getTotalPrice() /
                ((float) subscriptionViewDTO.getType().getDuration().toDays() / 30);
        DecimalFormat df = new DecimalFormat("#.##");

        return Float.parseFloat(df.format(pricePerMonth));
    }

    private void setCalculableFieldsViewDTO(SubscriptionViewDTO dtoView) {

        dtoView.setDaysLeft(daysUntilTheEnd(dtoView.getEnd()));
        dtoView.setPricePerMonth(calculatePricePerMonth(dtoView));
    }

}


