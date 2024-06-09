package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AdvertisementRequestDTO;
import com.develhope.spring.dtos.responses.AdvertisementResponseDTO;
import com.develhope.spring.entities.Advertisement;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    Optional <AdvertisementResponseDTO> createAdvertisement(AdvertisementRequestDTO creationDTO, Long userID);

    Optional<AdvertisementResponseDTO> updateAdvertisement(AdvertisementRequestDTO request, Long id);

    Optional<AdvertisementResponseDTO> displayAdvertisementToUser(Long id);

    List<AdvertisementResponseDTO> getAllAdvertisements();

    Optional<AdvertisementResponseDTO> getAdvertisementById(Long id);

    List<AdvertisementResponseDTO> getAllByActive(Boolean active);

    Optional<Advertisement> deleteAdvertisement(Long id);

    void deleteAllAdvertisements();

    Optional<AdvertisementResponseDTO> enableAdvertisement(Long id);

    Optional<AdvertisementResponseDTO> disableAdvertisement(Long id);
}
