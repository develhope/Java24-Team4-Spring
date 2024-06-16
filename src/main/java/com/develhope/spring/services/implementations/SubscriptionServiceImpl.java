package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.SubscriptionRequestDTO;
import com.develhope.spring.dtos.requests.SubscriptionUpdateDTO;
import com.develhope.spring.dtos.responses.SubscriptionResponseDTO;
import com.develhope.spring.dtos.responses.SubscriptionWithoutListenerDTO;
import com.develhope.spring.entities.Listener;
import com.develhope.spring.entities.Subscription;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.exceptions.NegativeIdException;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.repositories.SubscriptionRepository;
import com.develhope.spring.services.UniversalFieldUpdater;
import com.develhope.spring.services.interfaces.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

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
    public SubscriptionResponseDTO getSubscriptionById(Long id) {

        if (id < 0){
            throw new NegativeIdException(
                    "[Search failed] Subscription ID cannot be negative. Now: " + id);
        }

        return subscrRepository.findById(id).map(subscription -> {
            SubscriptionResponseDTO response = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(response);

            return response;
        }).orElseThrow(() -> new EntityNotFoundException("[Search failed] Subscription with ID " + id +
                " not found in the database"));
    }

    @Override
    public List<SubscriptionResponseDTO> getAllSubscriptions() {

        var subscriptions = subscrRepository.findAll().stream().map(subscription -> {
            SubscriptionResponseDTO found = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(found);

            return found;

        }).toList();

        if (subscriptions.isEmpty()) {
            throw new EmptyResultException("[Search failed] No subscriptions found in the database.");
        } else {
            return subscriptions;
        }
    }

    @Override
    public List<SubscriptionResponseDTO> getAllByActive(Boolean active) {

        List<Subscription> subscriptions = active ? subscrRepository.findByActiveTrue() :
                subscrRepository.findByActiveFalse();

        var foundByActive = subscriptions.stream().map(subscription -> {

            var responseDTO = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(responseDTO);

            return responseDTO;

        }).toList();

        if (foundByActive.isEmpty()) {
            throw new EmptyResultException("[Search failed] No subscriptions(active = " + active +
                    ") found in the database.");
        } else {
            return foundByActive;
        }
    }

    @Override
    public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO request) {

        if (request.getListenerId() < 0){
            throw new NegativeIdException(
                    "[Creation failed] Listener ID cannot be negative. Now: " + request.getListenerId());
        }

        Optional<Listener> listener = listenerRepository.findById(request.getListenerId());

        if (listener.isEmpty()){
            throw new EntityNotFoundException("[Creation failed] Listener with ID " + request.getListenerId() + " not found in the database");
        }

        Subscription toSave = modelMapper.map(request, Subscription.class);

        toSave.setListener(listener.get());
        toSave.setTotalPrice(toSave.getType().getTotalPrice());
        setStartSetEnd(toSave);
        controlAndEnableSubscription(toSave);

        var subscrSaved = subscrRepository.saveAndFlush(toSave);
        var responseDTO = modelMapper.map(subscrSaved, SubscriptionResponseDTO.class);

        setCalculableFieldsViewDTO(responseDTO);

        return responseDTO;
    }

    @Override
    public SubscriptionResponseDTO updateSubscription(Long id, SubscriptionUpdateDTO request) {

        if (id < 0){
            throw new NegativeIdException(
                    "[Update failed] Subscription ID cannot be negative. Now: " + id);
        }

        return subscrRepository.findById(id).map(subscription -> {

            if (incomingUpdateDtoIsValid(request)){
                try {
                    UniversalFieldUpdater.checkFieldsAndUpdate(request, subscription);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                setStartSetEnd(subscription);

                subscription = subscrRepository.saveAndFlush(subscription);
                SubscriptionResponseDTO response = modelMapper.map(subscription, SubscriptionResponseDTO.class);
                setCalculableFieldsViewDTO(response);

                return response;
            } else {
                throw new IllegalArgumentException(
                        "[Update failed] Invalid subscription type. Allowed types are: ANNUAL, HALF_YEAR, MONTHLY.");
            }

        }).orElseThrow(() -> new EntityNotFoundException("[Update failed] Subscription with ID " + id +
                " not found in the database"));
    }

    @Override
    public SubscriptionResponseDTO deleteSubscriptionById(Long id) {

        if (id < 0) {
            throw new NegativeIdException("[Delete failed] Subscription ID cannot be negative. Now: " + id);
        }

        return subscrRepository.findById(id).map(subscription -> {
            subscrRepository.deleteById(id);
            var responseDTO = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(responseDTO);

            return responseDTO;

        }).orElseThrow(() -> new EntityNotFoundException("[Delete failed] Subscription with ID " + id +
                " not found in the database"));

    }

    @Override
    public void deleteAllSubscriptions() {
        subscrRepository.deleteAll();
    }

    @Override
    public SubscriptionResponseDTO enableSubscription(Long id) {

        if (id < 0){
            throw new NegativeIdException(
                    "[Activation failed] Subscription ID cannot be negative. Now: " + id);
        }

        return subscrRepository.findById(id).map(subscription -> {

            subscription.setActive(true);
            subscription = subscrRepository.saveAndFlush(subscription);

            var responseDTO = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(responseDTO);

            return responseDTO;

        }).orElseThrow(() -> new EntityNotFoundException("[Activation failed] Subscription with ID " + id +
                " not found in the database"));
    }


    @Override
    public SubscriptionResponseDTO disableSubscription(Long id) {

        if (id < 0){
            throw new NegativeIdException(
                    "[Deactivation failed] Subscription ID cannot be negative. Now: " + id);
        }

        return subscrRepository.findById(id).map(subscription -> {

            subscription.setActive(false);
            subscription = subscrRepository.saveAndFlush(subscription);

            var responseDTO = modelMapper.map(subscription, SubscriptionResponseDTO.class);
            setCalculableFieldsViewDTO(responseDTO);

            return responseDTO;

        }).orElseThrow(() -> new EntityNotFoundException("[Deactivation failed] Subscription with ID " + id +
                " not found in the database"));
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

    private boolean incomingUpdateDtoIsValid(SubscriptionUpdateDTO request) {

        return request.getType() == Subscription.SubscrType.ANNUAL ||
                request.getType() == Subscription.SubscrType.HALF_YEAR ||
                request.getType() == Subscription.SubscrType.MONTHLY;

    }

    public void invokeFromOutsideSetCalculableFieldsDTO(SubscriptionWithoutListenerDTO withoutListenerDTO) {
        setCalculableFieldsViewDTO(withoutListenerDTO);
    }

}


