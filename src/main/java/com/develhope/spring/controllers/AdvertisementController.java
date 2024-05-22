package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.AdvertisementEntity;
import com.develhope.spring.services.interfaces.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/advertisements")
public class AdvertisementController {

    private final AdvertisementService advService;

    @Autowired
    public AdvertisementController(AdvertisementService advService) {
        this.advService = advService;
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementViewDTO>> getAllAdvertisements() {

        List<AdvertisementViewDTO> advList = advService.getAllAdvertisements();
        return advList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(advList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementViewDTO> getAdvertisementById(@PathVariable Long id) {

        AdvertisementViewDTO adv = advService.getAdvertisementById(id);
        return adv == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(adv, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdvertisementEntity> createAdvertisement(@RequestBody AdvertisementCreateUpdateDTO creationDTO) {

        AdvertisementEntity adv = advService.createAdvertisement(creationDTO);
        return adv == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(adv, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementEntity> enableAdvertisement(@PathVariable Long id, @RequestBody AdvertisementCreateUpdateDTO updateDTO) {

        AdvertisementEntity adv = advService.updateAdvertisement(updateDTO, id);
        return adv == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(adv, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AdvertisementEntity> deleteAdvertisement(@PathVariable Long id) {
        advService.deleteAdvertisement(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
