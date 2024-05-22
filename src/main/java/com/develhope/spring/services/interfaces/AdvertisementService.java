package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.Advertisement;

import java.time.LocalDateTime;
import java.util.List;


public interface AdvertisementService {

    public List<AdvertisementViewDTO> getAllAdvertisements();


    public AdvertisementViewDTO getAdvertisementById(Long id);

    public Advertisement createAdvertisement(AdvertisementCreateUpdateDTO creationDTO);

    public Advertisement updateAdvertisement(AdvertisementCreateUpdateDTO creationDTO, Long id);

    public void deleteAdvertisement(Long id);

    public void deleteAllAdvertisements();

    public void enableAdvertisement(Advertisement advertisementEntity);

    void disableAdvertisement(Advertisement advertisementEntity);

    //Il metodo che aumenta i views dopo che l'annuncio e` stato visualizzato
    public void incrementActualViews(Advertisement advertisementEntity);

    public Integer calculateActualDuration(LocalDateTime startDate);

    public Integer calculateTotalDuration(Advertisement advertisementEntity);

    public Float calculateCostPerView(Advertisement advertisementEntity);

    public Float calculateCostPerDay(Advertisement advertisementEntity);

    public Float calculateFinalCost(Advertisement advertisementEntity);

}

