package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.AdvertisementRequestDTO;
import com.develhope.spring.dtos.requests.AdvertisementUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementResponseDTO;
import com.develhope.spring.entities.Advertisement;

import java.util.List;
import java.util.Optional;

public interface AdvertisementService {

    AdvertisementResponseDTO createAdvertisement(AdvertisementRequestDTO creationDTO);

    AdvertisementResponseDTO updateAdvertisement(AdvertisementUpdateDTO request, Long id);

    AdvertisementResponseDTO displayAdvertisementToUser(Long id);

    List<AdvertisementResponseDTO> getAllAdvertisements();

    AdvertisementResponseDTO getAdvertisementById(Long id);

    List<AdvertisementResponseDTO> getAllByActive(Boolean active);

    AdvertisementResponseDTO deleteAdvertisement(Long id);

    void deleteAllAdvertisements();

    AdvertisementResponseDTO enableAdvertisement(Long id);

    AdvertisementResponseDTO disableAdvertisement(Long id);
}
