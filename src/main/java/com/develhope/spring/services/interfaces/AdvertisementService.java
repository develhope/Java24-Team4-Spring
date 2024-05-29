package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.Advertisement;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    AdvertisementViewDTO createAdvertisement(AdvertisementCreateUpdateDTO creationDTO, Long userID);

    Optional<AdvertisementViewDTO> updateAdvertisement(AdvertisementCreateUpdateDTO request, Long id);

    Optional<AdvertisementViewDTO> displayAdvertisementToUser(Long id);

    List<AdvertisementViewDTO> getAllAdvertisements();

    Optional<AdvertisementViewDTO> getAdvertisementById(Long id);

    List<AdvertisementViewDTO> getAllActiveTrue();

    List<AdvertisementViewDTO> getAllActiveFalse();

    Optional<Advertisement> deleteAdvertisement(Long id);

    void deleteAllAdvertisements();

    Optional<AdvertisementViewDTO> enableAdvertisement(Long id);

    Optional<AdvertisementViewDTO> disableAdvertisement(Long id);
}
