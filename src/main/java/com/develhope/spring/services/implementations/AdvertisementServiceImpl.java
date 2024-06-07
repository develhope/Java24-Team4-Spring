package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AdvertisementRequestDTO;
import com.develhope.spring.dtos.responses.AdvertisementResponseDTO;
import com.develhope.spring.entities.Advertisement;
import com.develhope.spring.repositories.AdvertisementRepository;
import com.develhope.spring.repositories.AdvertiserRepository;
import com.develhope.spring.services.interfaces.AdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final ModelMapper modelMapper;
    private final AdvertisementRepository advRepository;
    private final AdvertiserRepository advertiserRepository;

    @Autowired
    public AdvertisementServiceImpl(ModelMapper modelMapper, AdvertisementRepository advRepository, AdvertiserRepository advertiserRepository) {
        this.modelMapper = modelMapper;
        this.advRepository = advRepository;
        this.advertiserRepository = advertiserRepository;
    }

    @Override
    public Optional<AdvertisementResponseDTO> createAdvertisement(AdvertisementRequestDTO request, Long advertiserID) {
        return advertiserRepository.findById(advertiserID).map(advertiser -> {
            Advertisement newAdv = modelMapper.map(request, Advertisement.class);
            newAdv.setAdvertiser(advertiser);
            initializeAdvertisement(newAdv);
            advRepository.saveAndFlush(newAdv);

            AdvertisementResponseDTO response = modelMapper.map(newAdv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(response);
            return response;
        });
    }

    @Override
    public Optional<AdvertisementResponseDTO> updateAdvertisement(AdvertisementRequestDTO request, Long id) {
        return advRepository.findById(id).map(adv -> {
            modelMapper.map(request, adv);
            controlAndEnableAdvertisement(adv);
            advRepository.saveAndFlush(adv);

            AdvertisementResponseDTO response = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(response);

            return response;
        });
    }

    @Override
    public Optional<AdvertisementResponseDTO> displayAdvertisementToUser(Long id) {
        return advRepository.findById(id).map(adv -> {
            incrementActualViews(adv);
            advRepository.saveAndFlush(adv);
            AdvertisementResponseDTO found = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(found);

            return found;
        });
    }

    @Override
    public List<AdvertisementResponseDTO> getAllAdvertisements() {
        return advRepository.findAll().stream()
                .map(adv -> {
                    AdvertisementResponseDTO found = modelMapper.map(adv, AdvertisementResponseDTO.class);
                    setCalculableFieldsAdvViewDTO(found);
                    return found;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<AdvertisementResponseDTO> getAdvertisementById(Long id) {
        return advRepository.findById(id).map(adv -> {
            AdvertisementResponseDTO found = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(found);
            return found;
        });
    }

    @Override
    public List<AdvertisementResponseDTO> getAllByActive(Boolean active) {
        List<Advertisement> ads = active ? advRepository.findByActiveTrue() : advRepository.findByActiveFalse();
        return ads.stream()
                .map(adv -> {
                    AdvertisementResponseDTO found = modelMapper.map(adv, AdvertisementResponseDTO.class);
                    setCalculableFieldsAdvViewDTO(found);
                    return found;
                }).collect(Collectors.toList());
    }

    @Override
    public Optional<Advertisement> deleteAdvertisement(Long id) {
        return advRepository.findById(id).map(advToDelete -> {
            advRepository.deleteById(id);
            return advToDelete;
        });
    }

    @Override
    public void deleteAllAdvertisements() {
        advRepository.deleteAll();
    }

    @Override
    public Optional<AdvertisementResponseDTO> enableAdvertisement(Long id) {
        return advRepository.findById(id).map(adv -> {
            adv.setActive(true);
            advRepository.saveAndFlush(adv);
            AdvertisementResponseDTO response = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(response);
            return response;
        });
    }

    @Override
    public Optional<AdvertisementResponseDTO> disableAdvertisement(Long id) {
        return advRepository.findById(id).map(adv -> {
            adv.setActive(false);
            advRepository.saveAndFlush(adv);
            AdvertisementResponseDTO response = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(response);
            return response;
        });
    }

    private void controlAndEnableAdvertisement(Advertisement advertisement) {
        advertisement.setActive(!LocalDateTime.now().isBefore(advertisement.getStartDate()));
    }

    private void initializeAdvertisement(Advertisement newAdv) {
        newAdv.setCostPerDay(calculateCostPerDay(newAdv));
        newAdv.setCostPerView(calculateCostPerView(newAdv));
        newAdv.setFinalCost(calculateFinalCost(newAdv));
        controlAndEnableAdvertisement(newAdv);
    }

    private void incrementActualViews(Advertisement advertisement) {
        advertisement.setActualViews(advertisement.getActualViews() + 1);
    }

    private Integer calculateActualDuration(LocalDateTime start) {
        LocalDateTime current = LocalDateTime.now();
        if (current.isBefore(start)) {
            return 0;
        }
        return (int) (Duration.between(start.toLocalDate().atStartOfDay(), current.toLocalDate()).plusDays(1).toDays());
    }

    private Integer calculateTotalDuration(LocalDateTime start, LocalDateTime end) {
        return (int) (Duration.between(start, end).plusDays(2).toDays());
    }

    private Float calculateCostPerView(Advertisement advertisement) {
        return advertisement.getOrderedViews() >= 500 ? 0.35f : 0.50f;
    }

    private Float calculateCostPerDay(Advertisement advertisement) {
        return calculateTotalDuration(advertisement.getStartDate(), advertisement.getEndDate()) >= 50 ? 8f : 10f;
    }

    private Float calculateFinalCost(Advertisement advertisement) {
        Float costPerDay = advertisement.getCostPerDay();
        Float costPerView = advertisement.getCostPerView();
        return costPerDay * calculateTotalDuration(advertisement.getStartDate(), advertisement.getEndDate()) + costPerView * advertisement.getOrderedViews();
    }

    private void setCalculableFieldsAdvViewDTO(AdvertisementResponseDTO advertisementResponseDTO) {
        advertisementResponseDTO.setActualDuration(calculateActualDuration(advertisementResponseDTO.getStartDate()));
        advertisementResponseDTO.setTotalDuration(calculateTotalDuration(advertisementResponseDTO.getStartDate(), advertisementResponseDTO.getEndDate()));
    }
}
