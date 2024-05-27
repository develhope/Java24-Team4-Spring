package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.Advertisement;
import com.develhope.spring.entities.User;
import com.develhope.spring.repositories.AdvertismentRepository;
import com.develhope.spring.repositories.UserRepository;
import com.develhope.spring.services.interfaces.AdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
//TODO implementare il metodo di attivazione dell'annuncio non appena arriva il startDate ?????

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final ModelMapper mapper;
    private final AdvertismentRepository repository;
    private final UserRepository userRepository;


    @Autowired
    public AdvertisementServiceImpl(ModelMapper mapper, AdvertismentRepository repository, UserRepository userRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public AdvertisementViewDTO displayAdvertisementToUser(Long id) {

        if (! repository.existsById(id)) {
            return null;
        }
        Advertisement adv = repository.findById(id).orElse(null);

        if (adv == null) {
            return null;
        }

        //incremento actualViews e salvo il valore in DB ogni visualizzazione
        incrementActualViews(adv);
        repository.saveAndFlush(adv);

        AdvertisementViewDTO dtoView = mapper.map(adv, AdvertisementViewDTO.class);

        dtoView.setDaysPassed(calculateActualDuration(dtoView.getStartDate()));
        dtoView.setActualViews(adv.getActualViews());

        return dtoView;
    }

    @Override
    public List<AdvertisementViewDTO> getAllAdvertisements() {

        return repository.findAll().stream()
                .map
                        (ad -> {
                            AdvertisementViewDTO dtoView = mapper.map(ad, AdvertisementViewDTO.class);
                            dtoView.setDaysPassed(calculateActualDuration(dtoView.getStartDate()));
                            dtoView.setActualViews(ad.getActualViews());
                            return dtoView;
                        }).collect(Collectors.toList());
    }

    @Override
    public AdvertisementViewDTO getAdvertisementById(Long id) {

        if (! repository.existsById(id)) {
            return null;
        }

        Advertisement adv = repository.findById(id).orElse(null);

        if (adv == null) {
            return null;
        }

        AdvertisementViewDTO dtoView = mapper.map(adv, AdvertisementViewDTO.class);

        dtoView.setDaysPassed(calculateActualDuration(dtoView.getStartDate()));
        dtoView.setActualViews(adv.getActualViews());

        return dtoView;
    }


    @Override
    public Advertisement createAdvertisement(AdvertisementCreateUpdateDTO creationDTO, Long userID) {

        // faccio mapping
        Advertisement createdAdv = mapper.map(creationDTO, Advertisement.class);

        createdAdv.setUser(userRepository.findById(userID).orElse(null));

        // imposto i atributi necessari quelli che non c`erano in DTO
        createdAdv.setCostPerDay(calculateCostPerDay(createdAdv));
        createdAdv.setCostPerView(calculateCostPerView(createdAdv));
        createdAdv.setFinalCost(calculateFinalCost(createdAdv));
        createdAdv.setActualViews(0);
        createdAdv.setActive(
                ! LocalDateTime.now().isBefore(createdAdv.getStartDate())
        );

        repository.saveAndFlush(createdAdv);
        return createdAdv;
    }


    @Override
    public Advertisement updateAdvertisement(AdvertisementCreateUpdateDTO creationDTO, Long id) {

        Advertisement updatedAdv = mapper.map(creationDTO, Advertisement.class);
        //Set ID per essere sicuro
        updatedAdv.setId(id);

        //Qua +- la stessa cosa pero prendendo il valore di actualViews
        updatedAdv.setCostPerDay(calculateCostPerDay(updatedAdv));
        updatedAdv.setCostPerView(calculateCostPerView(updatedAdv));
        updatedAdv.setFinalCost(calculateFinalCost(updatedAdv));
        updatedAdv.setActualViews(getAdvertisementById(id).getActualViews());
        updatedAdv.setActive(
                ! LocalDateTime.now().isBefore(updatedAdv.getStartDate())
        );

        repository.saveAndFlush(updatedAdv);
        return updatedAdv;
    }

    @Override
    public void deleteAdvertisement(Long id) {

        if (! repository.existsById(id)) {
            return;
        }
        repository.deleteById(id);
    }

    @Override
    public void deleteAllAdvertisements() {

        repository.deleteAll();
    }


    @Override
    public void enableAdvertisement(Advertisement advertisementEntity) {

        advertisementEntity.setActive(true);
    }

    @Override
    public void disableAdvertisement(Advertisement advertisementEntity) {

        advertisementEntity.setActive(false);
    }

    @Override
    public void incrementActualViews(Advertisement advertisementEntity) {

        advertisementEntity.setActualViews(advertisementEntity.getActualViews() + 1);
    }

    @Override
    public Integer calculateActualDuration(LocalDateTime startDate) {

        return (int) (
                Duration.between(
                       startDate,
                        LocalDateTime.now()
                ).toDays()
        );
    }

    @Override
    public Integer calculateTotalDuration(Advertisement advertisementEntity) {

        return (int) (
                Duration.between(
                        advertisementEntity.getStartDate(),
                        advertisementEntity.getEndDate()
                ).toDays()
        );
    }

    @Override
    public Float calculateCostPerView(Advertisement advertisementEntity) {

        return advertisementEntity.getOrderedViews() >= 500 ? 0.35f : 0.50f;
    }

    @Override
    public Float calculateCostPerDay(Advertisement advertisementEntity) {

        return calculateTotalDuration(advertisementEntity) >= 50 ? 8f : 10f;
    }

    @Override
    public Float calculateFinalCost(Advertisement advertisement) {

        Float costPerDay = advertisement.getCostPerDay();
        Float costPerView = advertisement.getCostPerView();

        Float finalCost =
                costPerDay * calculateTotalDuration(advertisement) +
                costPerView * advertisement.getOrderedViews();

        return finalCost;
    }

}