package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.Advertisement;
import com.develhope.spring.repositories.AdvertisementRepository;
import com.develhope.spring.repositories.UserRepository;
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
    private final UserRepository userRepository;


    @Autowired
    public AdvertisementServiceImpl(
            ModelMapper modelMapper,
            AdvertisementRepository advRepository,
            UserRepository userRepository
    ) {
        this.modelMapper = modelMapper;
        this.advRepository = advRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AdvertisementViewDTO> createAdvertisement(AdvertisementCreateUpdateDTO request, Long userID) {


        return userRepository.findById(userID).map(user -> {
            Advertisement newAdv = modelMapper.map(request, Advertisement.class);

            newAdv.setUser(user);
            initializeAdvertisement(newAdv);
            advRepository.saveAndFlush(newAdv);

            AdvertisementViewDTO response = modelMapper.map(newAdv, AdvertisementViewDTO.class);
            setCalculableFieldsAdvViewDTO(response);
            return response;
        });
    }

    @Override
    public Optional<AdvertisementViewDTO> updateAdvertisement(AdvertisementCreateUpdateDTO request, Long id) {

        return advRepository.findById(id).map(adv -> {
            modelMapper.map(request, adv);
            controlAndEnableAdvertisement(adv);
            advRepository.saveAndFlush(adv);

            AdvertisementViewDTO response = modelMapper.map(adv, AdvertisementViewDTO.class);
            setCalculableFieldsAdvViewDTO(response);

            return response;
        });
    }

    @Override
    public Optional<AdvertisementViewDTO> displayAdvertisementToUser(Long id) {

        return advRepository.findById(id).map(adv -> {

            incrementActualViews(adv);
            advRepository.saveAndFlush(adv);
            AdvertisementViewDTO found = modelMapper.map(adv, AdvertisementViewDTO.class);
            setCalculableFieldsAdvViewDTO(found);

            return found;
        });
    }

    @Override
    public List<AdvertisementViewDTO> getAllAdvertisements() {

        return advRepository.findAll().stream()
                .map
                        (adv -> {
                            AdvertisementViewDTO found = modelMapper.map(adv, AdvertisementViewDTO.class);
                            setCalculableFieldsAdvViewDTO(found);
                            return found;
                        }).collect(Collectors.toList());
    }

    @Override
    public Optional<AdvertisementViewDTO> getAdvertisementById(Long id) {

        return advRepository.findById(id).map(adv -> {
            AdvertisementViewDTO found = modelMapper.map(adv, AdvertisementViewDTO.class);
            setCalculableFieldsAdvViewDTO(found);
            return found;
        });
    }

    @Override
    public List<AdvertisementViewDTO> getAllByActive(Boolean active) {

        if (active) {
            return advRepository.findByActiveTrue().stream()
                    .map
                            (adv -> {
                                AdvertisementViewDTO found = modelMapper.map(adv, AdvertisementViewDTO.class);
                                setCalculableFieldsAdvViewDTO(found);

                                return found;
                            }).collect(Collectors.toList());
        } else {
            return advRepository.findByActiveFalse().stream()
                    .map
                            (adv -> {
                                AdvertisementViewDTO found = modelMapper.map(adv, AdvertisementViewDTO.class);
                                setCalculableFieldsAdvViewDTO(found);

                                return found;
                            }).collect(Collectors.toList());
        }
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
    public Optional<AdvertisementViewDTO> enableAdvertisement(Long id) {

        return advRepository.findById(id).map(adv -> {
            adv.setActive(true);
            advRepository.saveAndFlush(adv);
            AdvertisementViewDTO response = modelMapper.map(adv, AdvertisementViewDTO.class);
            setCalculableFieldsAdvViewDTO(response);

            return response;
        });
    }

    @Override
    public Optional<AdvertisementViewDTO> disableAdvertisement(Long id) {

        return advRepository.findById(id).map(adv -> {
            adv.setActive(false);
            advRepository.saveAndFlush(adv);
            AdvertisementViewDTO response = modelMapper.map(adv, AdvertisementViewDTO.class);
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
        return (int) (
                Duration.between(
                        start.toLocalDate().atStartOfDay(),
                        current.toLocalDate()
                ).plusDays(1).toDays()
        );
    }

    private Integer calculateTotalDuration(LocalDateTime start, LocalDateTime end) {

        return (int) (
                Duration.between(
                        start,
                        end
                ).plusDays(2).toDays()
        );
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

        return costPerDay * calculateTotalDuration(advertisement.getStartDate(), advertisement.getEndDate()) +
                costPerView * advertisement.getOrderedViews();


    }

    private void setCalculableFieldsAdvViewDTO(AdvertisementViewDTO advertisementViewDTO) {

        advertisementViewDTO.setActualDuration(calculateActualDuration(advertisementViewDTO.getStartDate()));

        advertisementViewDTO.setTotalDuration(calculateTotalDuration(
                advertisementViewDTO.getStartDate(), advertisementViewDTO.getEndDate()));
    }
}
