package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.AdvertisementEntity;
import com.develhope.spring.repositories.AdvertismentRepository;
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

    @Autowired
    public AdvertisementServiceImpl(ModelMapper mapper, AdvertismentRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<AdvertisementViewDTO> getAllAdvertisements() {

        return repository.findAll().stream()
                .map
                        (ad -> {
                            AdvertisementViewDTO dtoView = mapper.map(ad, AdvertisementViewDTO.class);
                            dtoView.setDaysPassed(calculateActualDuration(dtoView.getStartDate()));
                            //TODO implementare il metodo che calcola valore di actualViews ora `e sempre null !!!!!!
                            return dtoView;
                        }).collect(Collectors.toList());
    }

    @Override
    public AdvertisementViewDTO getAdvertisementById(Long id) {

        if (! repository.existsById(id)) {
            return null;
        }

        AdvertisementEntity adv = repository.findById(id).orElse(null);

        if (adv == null) {
            return null;
        }
        AdvertisementViewDTO dtoView = mapper.map(adv, AdvertisementViewDTO.class);

        dtoView.setDaysPassed(calculateActualDuration(dtoView.getStartDate()));
        dtoView.setActualViews(adv.getActualViews());
        //TODO implementare il metodo che calcola valore di actualViews ora `e sempre null !!!!!!
        return dtoView;
    }


    @Override
    public AdvertisementEntity createAdvertisement(AdvertisementCreateUpdateDTO creationDTO) {

        // faccio mapping
        AdvertisementEntity createdAdv = mapper.map(creationDTO, AdvertisementEntity.class);

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
    public AdvertisementEntity updateAdvertisement(AdvertisementCreateUpdateDTO creationDTO, Long id) {

        AdvertisementEntity updatedAdv = mapper.map(creationDTO, AdvertisementEntity.class);
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
    public void enableAdvertisement(AdvertisementEntity advertisementEntity) {

        advertisementEntity.setActive(true);
    }

    @Override
    public void disableAdvertisement(AdvertisementEntity advertisementEntity) {

        advertisementEntity.setActive(false);
    }

    @Override
    public void incrementActualViews(AdvertisementEntity advertisementEntity) {

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
    public Integer calculateTotalDuration(AdvertisementEntity advertisementEntity) {

        return (int) (
                Duration.between(
                        advertisementEntity.getStartDate(),
                        advertisementEntity.getEndDate()
                ).toDays()
        );
    }

    @Override
    public Float calculateCostPerView(AdvertisementEntity advertisementEntity) {

        return advertisementEntity.getOrderedViews() >= 500 ? 0.35f : 0.50f;
    }

    @Override
    public Float calculateCostPerDay(AdvertisementEntity advertisementEntity) {

        return calculateTotalDuration(advertisementEntity) >= 50 ? 8f : 10f;
    }

    @Override
    public Float calculateFinalCost(AdvertisementEntity advertisementEntity) {

        Float costPerDay = advertisementEntity.getCostPerDay();
        Float costPerView = advertisementEntity.getCostPerView();

        Float finalCost =
                costPerDay * calculateTotalDuration(advertisementEntity) +
                costPerView * advertisementEntity.getOrderedViews();

        return finalCost;
    }

}
