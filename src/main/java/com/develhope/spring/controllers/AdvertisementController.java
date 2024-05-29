package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.Advertisement;
import com.develhope.spring.services.interfaces.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/advertisements")
public class AdvertisementController {

    private final AdvertisementService advService;

    @Autowired
    public AdvertisementController(AdvertisementService advService) {
        this.advService = advService;
    }

    @GetMapping("/display_adv/{id}")
    public ResponseEntity<?> displayAdvertisementToUser(@PathVariable Long id) {
        Optional<AdvertisementViewDTO> adv = advService.displayAdvertisementToUser(id);

        return adv.isPresent() ? ResponseEntity.ok().body(adv) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Advertisement not found!");
    }

    @GetMapping
    public ResponseEntity<?> getAllAdvertisements() {
        List<AdvertisementViewDTO> advList = advService.getAllAdvertisements();

        return !advList.isEmpty() ? ResponseEntity.ok().body(advList) : ResponseEntity.status(HttpStatus.NO_CONTENT).body("Advertisements list is empty");
    }

    @GetMapping
    public ResponseEntity<?> getAllByActive(@RequestParam Boolean active) {
        List<AdvertisementViewDTO> advList = active ? advService.getAllActiveTrue() : advService.getAllActiveFalse();

        return ! advList.isEmpty() ? ResponseEntity.ok(advList) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Advertisements list" + "(Active = " + active +
                        ") is empty");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long id) {
        Optional<AdvertisementViewDTO> adv = advService.getAdvertisementById(id);

        return adv.isPresent() ? ResponseEntity.ok().body(adv) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Advertisement not found!");
    }

    @PostMapping
    public ResponseEntity<?> createAdvertisement(@RequestBody AdvertisementCreateUpdateDTO creationDTO, @RequestParam Long userID) {
        AdvertisementViewDTO response = advService.createAdvertisement(creationDTO, userID);

        return response == null ? ResponseEntity.badRequest().body("Error creating advertisement") : ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdvertisement(@PathVariable Long id, @RequestBody AdvertisementCreateUpdateDTO updateDTO) {
        Optional<AdvertisementViewDTO> adv = advService.updateAdvertisement(updateDTO, id);

        return adv.isPresent() ? ResponseEntity.ok(adv) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Advertisement not found!");

    }

    @PatchMapping("/enable_adv/{id}")
    public ResponseEntity<?> enableAdvertisement(@PathVariable Long id) {
        Optional<AdvertisementViewDTO> adv = advService.enableAdvertisement(id);

        return adv.isPresent() ? ResponseEntity.ok().body(adv) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Advertisement not found!");
    }

    @PatchMapping("/disable_adv/{id}")
    public ResponseEntity<?> disableAdvertisement(@PathVariable Long id) {
        Optional<AdvertisementViewDTO> adv = advService.disableAdvertisement(id);

        return adv.isPresent() ? ResponseEntity.ok().body(adv) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Advertisement not found!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertisement(@PathVariable Long id) {
        Optional<Advertisement> adv = advService.deleteAdvertisement(id);

        return adv.isPresent() ? ResponseEntity.ok().body(adv) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Advertisement not found!");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllAdvertisements() {
        advService.deleteAllAdvertisements();

        return ResponseEntity.ok().body("All advertisements deleted!");
    }
}
