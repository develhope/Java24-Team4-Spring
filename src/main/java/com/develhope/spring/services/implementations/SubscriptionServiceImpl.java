package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SubscriptionRequestDTO;
import com.develhope.spring.dtos.responses.SubscriptionResponseDTO;
import com.develhope.spring.dtos.responses.SubscriptionWithoutListenerDTO;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.SubscriptionRepository;
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
    private final ListenerRepository listenerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionServiceImpl(
            SubscriptionRepository subscrRepository,
            ListenerRepository listenerRepository,
            ModelMapper modelMapper
    ) {
        this.subscrRepository = subscrRepository;
        this.listenerRepository = listenerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<SubscriptionResponseDTO> getSubscriptionById(Long id) {

        return subscrRepository.findById(id).map(subscription -> {
            SubscriptionResponseDTO response = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(response);

            return response;
        });
    }

    @Override
    public List<SubscriptionResponseDTO> getAllSubscriptions() {

        return subscrRepository.findAll().stream().map(subscription -> {
            SubscriptionResponseDTO found = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(found);

            return found;

        }).collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionResponseDTO> getAllByActive(Boolean active) {

        if (active) {
            return subscrRepository.findByActiveTrue().stream().map(subscription -> {
                SubscriptionResponseDTO found = modelMapper.map(subscription, SubscriptionResponseDTO.class);
                setCalculableFieldsViewDTO(found);

                return found;

            }).collect(Collectors.toList());
        } else {
            return subscrRepository.findByActiveFalse().stream().map(subscription -> {
                SubscriptionResponseDTO found = modelMapper.map(subscription, SubscriptionResponseDTO.class);
                setCalculableFieldsViewDTO(found);

                return found;

            }).collect(Collectors.toList());
        }
    }

    @Override
    public Optional<SubscriptionResponseDTO> createSubscription(SubscriptionRequestDTO request, Long userID) {
        return listenerRepository.findById(userID).map(listener -> {

            Subscription toSave = modelMapper.map(request, Subscription.class);

            toSave.setListener(listener);
            toSave.setTotalPrice(toSave.getType().getTotalPrice());
            setStartSetEnd(toSave);
            controlAndEnableSubscription(toSave);

            Subscription saved = subscrRepository.saveAndFlush(toSave);
            SubscriptionResponseDTO response = modelMapper.map(saved, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(response);
            return response;

        });

    }

    @Override
    public Optional<SubscriptionResponseDTO> updateSubscription(Long id, SubscriptionRequestDTO request) {

        return subscrRepository.findById(id).map(subscription -> {

            if (incomingDtoIsValid(request)) {
                modelMapper.map(request, subscription);
                setStartSetEnd(subscription);

                subscrRepository.saveAndFlush(subscription);
                SubscriptionResponseDTO response = modelMapper.map(subscription, SubscriptionResponseDTO.class);
                setCalculableFieldsViewDTO(response);

                return response;
            } else throw new IllegalArgumentException("Invalid subscription type. Allowed types are: ANNUAL, HALF_YEAR, MONTHLY.");

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
    public Optional<SubscriptionResponseDTO> enableSubscription(Long id) {

        return subscrRepository.findById(id).map(subscr -> {

            subscr.setActive(true);
            subscr = subscrRepository.saveAndFlush(subscr);

            SubscriptionResponseDTO response = modelMapper.map(subscr, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(response);

            return response;
        });
    }


    @Override
    public Optional<SubscriptionResponseDTO> disableSubscription(Long id) {
        return subscrRepository.findById(id).map(subscr -> {

            subscr.setActive(false);
            subscr = subscrRepository.saveAndFlush(subscr);

            SubscriptionResponseDTO response = modelMapper.map(subscr, SubscriptionResponseDTO.class);
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


    private float calculatePricePerMonth(SubscriptionResponseDTO subscriptionResponseDTO) {

        float pricePerMonth = subscriptionResponseDTO.getTotalPrice() /
                ((float) subscriptionResponseDTO.getType().getDuration().toDays() / 30);
        DecimalFormat df = new DecimalFormat("#.##");

        return Float.parseFloat(df.format(pricePerMonth));
    }

    //Overloaded method for SubscriptionWithoutListenerDTO
    private float calculatePricePerMonth(SubscriptionWithoutListenerDTO withoutListenerDTO) {

        float pricePerMonth = withoutListenerDTO.getTotalPrice() /
                ((float) withoutListenerDTO.getType().getDuration().toDays() / 30);
        DecimalFormat df = new DecimalFormat("#.##");

        return Float.parseFloat(df.format(pricePerMonth));
    }

    private void setCalculableFieldsViewDTO(SubscriptionResponseDTO dtoView) {

        dtoView.setDaysLeft(daysUntilTheEnd(dtoView.getEnd()));
        dtoView.setPricePerMonth(calculatePricePerMonth(dtoView));
    }

    //Overloaded method for SubscriptionWithoutListenerDTO
    private void setCalculableFieldsViewDTO(SubscriptionWithoutListenerDTO withoutListenerDTO) {

        withoutListenerDTO.setDaysLeft(daysUntilTheEnd(withoutListenerDTO.getEnd()));
        withoutListenerDTO.setPricePerMonth(calculatePricePerMonth(withoutListenerDTO));
    }

    private boolean incomingDtoIsValid(SubscriptionRequestDTO request) {

        return request.getType() == Subscription.SubscrType.ANNUAL ||
                request.getType() == Subscription.SubscrType.HALF_YEAR ||
                request.getType() == Subscription.SubscrType.MONTHLY;

    }

    public void invokeFromOutsideSetCalculableFieldsDTO(SubscriptionWithoutListenerDTO withoutListenerDTO) {
        setCalculableFieldsViewDTO(withoutListenerDTO);
    }


}


