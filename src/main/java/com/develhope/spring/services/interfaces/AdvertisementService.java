package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.AdvertisementEntity;

import java.time.LocalDateTime;
import java.util.List;


public interface AdvertisementService {

    public List<AdvertisementViewDTO> getAllAdvertisements();


    public AdvertisementViewDTO getAdvertisementById(Long id);

    public AdvertisementEntity createAdvertisement(AdvertisementCreateUpdateDTO creationDTO);

    public AdvertisementEntity updateAdvertisement(AdvertisementCreateUpdateDTO creationDTO, Long id);

    public void deleteAdvertisement(Long id);

    public void deleteAllAdvertisements();

    public void enableAdvertisement(AdvertisementEntity advertisementEntity);

    void disableAdvertisement(AdvertisementEntity advertisementEntity);

    //Il metodo che aumenta i views dopo che l'annuncio e` stato visualizzato
    public void incrementActualViews(AdvertisementEntity advertisementEntity);

    public Integer calculateActualDuration(LocalDateTime startDate);

    public Integer calculateTotalDuration(AdvertisementEntity advertisementEntity);

    public Float calculateCostPerView(AdvertisementEntity advertisementEntity);

    public Float calculateCostPerDay(AdvertisementEntity advertisementEntity);

    public Float calculateFinalCost(AdvertisementEntity advertisementEntity);

}

