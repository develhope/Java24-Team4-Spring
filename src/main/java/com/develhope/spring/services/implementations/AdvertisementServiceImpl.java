package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AdvertisementRequestDTO;
import com.develhope.spring.dtos.requests.AdvertisementUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementResponseDTO;
import com.develhope.spring.entities.Advertisement;
import com.develhope.spring.entities.Advertiser;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.exceptions.NegativeIdException;
import com.develhope.spring.repositories.AdvertisementRepository;
import com.develhope.spring.repositories.AdvertiserRepository;
import com.develhope.spring.services.UniversalFieldUpdater;
import com.develhope.spring.services.interfaces.AdvertisementService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final ModelMapper modelMapper;
    private final AdvertisementRepository advertisementRepository;
    private final AdvertiserRepository advertiserRepository;

    @Autowired
    public AdvertisementServiceImpl(ModelMapper modelMapper, AdvertisementRepository advertisementRepository, AdvertiserRepository advertiserRepository) {
        this.modelMapper = modelMapper;
        this.advertisementRepository = advertisementRepository;
        this.advertiserRepository = advertiserRepository;
    }

    @Override
    public AdvertisementResponseDTO createAdvertisement(AdvertisementRequestDTO request) {
        Optional<Advertiser> advertiser = advertiserRepository.findById(request.getAdvertiserUserId());

        if (request.getAdvertiserUserId() < 0) {
            throw new NegativeIdException("[Creation failed] Advertiser ID cannot be negative. Now: " + request.getAdvertiserUserId());
        }

        if (advertiser.isEmpty()) {
            throw new EntityNotFoundException("[Creation failed] Advertiser with ID " + request.getAdvertiserUserId() +
                    " not found in the database");
        }

        Advertisement newAdvertisement = modelMapper.map(request, Advertisement.class);

        newAdvertisement.setAdvertiser(advertiser.get());
        initializeAdvertisement(newAdvertisement);

        var advSaved = advertisementRepository.saveAndFlush(newAdvertisement);

        var responseDTO = modelMapper.map(newAdvertisement, AdvertisementResponseDTO.class);

        setCalculableFieldsAdvViewDTO(responseDTO);

        return responseDTO;
    }

    @Override
    public AdvertisementResponseDTO updateAdvertisement(AdvertisementUpdateDTO request, Long id) {
        if (id < 0) {
            throw new NegativeIdException("[Creation failed] Advertiser ID cannot be negative. Now: " + id);
        }

        Optional<Advertisement> advertisement = advertisementRepository.findById(id);

        if (advertisement.isEmpty()) {
            throw new EntityNotFoundException("[Update failed] Advertisement with ID " + id +
                    " not found in the database");
        }

        var toUpdate = advertisement.get();

        try {
            UniversalFieldUpdater.checkFieldsAndUpdate(request, toUpdate);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        initializeAdvertisement(toUpdate);

        var updated = advertisementRepository.saveAndFlush(toUpdate);
        var responseDto = modelMapper.map(updated, AdvertisementResponseDTO.class);

        setCalculableFieldsAdvViewDTO(responseDto);

        return responseDto;
    }

    @Override
    public AdvertisementResponseDTO displayAdvertisementToUser(Long id) {
        return advertisementRepository.findById(id).map(adv -> {

            incrementActualViews(adv);
            adv = advertisementRepository.saveAndFlush(adv);

            var responseDTO = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(responseDTO);

            return responseDTO;

        }).orElseThrow(() -> new EntityNotFoundException("Advertisement with ID " + id +
                " not found in the database"));
    }

    @Override
    public List<AdvertisementResponseDTO> getAllAdvertisements() {

        var advList = advertisementRepository.findAll()
                .stream()
                .map(adv -> {
                    var responseDTO = modelMapper.map(adv, AdvertisementResponseDTO.class);
                    setCalculableFieldsAdvViewDTO(responseDTO);

                    return responseDTO;

                }).toList();

        if (advList.isEmpty()) {
            throw new EmptyResultException("No advertisements found in the database.");
        }

        return advList;
    }

    @Override
    public AdvertisementResponseDTO getAdvertisementById(Long id) {

        return advertisementRepository.findById(id).map(adv -> {

            var responseDTO = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(responseDTO);

            return responseDTO;

        }).orElseThrow(() -> new EntityNotFoundException("Advertisement with ID " + id +
                " not found in the database"));
    }

    @Override
    public List<AdvertisementResponseDTO> getAllByActive(Boolean active) {
        List<Advertisement> ads = active ? advertisementRepository.findByActiveTrue() : advertisementRepository.findByActiveFalse();

        return ads.stream()
                .map(adv -> {
                    var responseDTO = modelMapper.map(adv, AdvertisementResponseDTO.class);
                    setCalculableFieldsAdvViewDTO(responseDTO);

                    return responseDTO;

                }).collect(Collectors.toList());
    }

    @Override
    public AdvertisementResponseDTO deleteAdvertisement(Long id) {

        return advertisementRepository.findById(id).map(advToDelete -> {

            advertisementRepository.deleteById(id);
            var responseDTO = modelMapper.map(advToDelete, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(responseDTO);

            return responseDTO;

        }).orElseThrow(() -> new EntityNotFoundException("Advertisement with ID " + id +
                " not found in the database"));
    }

    @Override
    public void deleteAllAdvertisements() {
        advertisementRepository.deleteAll();
    }

    @Override
    public AdvertisementResponseDTO enableAdvertisement(Long id) {

        return advertisementRepository.findById(id).map(adv -> {

            adv.setActive(true);
            adv = advertisementRepository.saveAndFlush(adv);
            AdvertisementResponseDTO response = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(response);

            return response;

        }).orElseThrow(() -> new EntityNotFoundException("Advertisement with ID " + id +
                " not found in the database"));
    }

    @Override
    public AdvertisementResponseDTO disableAdvertisement(Long id) {
        return advertisementRepository.findById(id).map(adv -> {

            adv.setActive(false);
            adv = advertisementRepository.saveAndFlush(adv);
            AdvertisementResponseDTO response = modelMapper.map(adv, AdvertisementResponseDTO.class);
            setCalculableFieldsAdvViewDTO(response);

            return response;

        }).orElseThrow(() -> new EntityNotFoundException("Advertisement with ID " + id +
                " not found in the database"));
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
