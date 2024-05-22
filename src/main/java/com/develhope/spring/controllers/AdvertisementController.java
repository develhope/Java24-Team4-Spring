package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.AdvertisementCreateUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementViewDTO;
import com.develhope.spring.entities.Advertisement;
import com.develhope.spring.services.interfaces.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<?> getAllAdvertisements() {

        List<AdvertisementViewDTO> advList = advService.getAllAdvertisements();
        return advList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(advList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdvertisementById(@PathVariable Long id) {

        AdvertisementViewDTO adv = advService.getAdvertisementById(id);
        return adv == null ? ResponseEntity.notFound().build(): ResponseEntity.ok().body(adv);
    }

    @PostMapping
    public ResponseEntity<?> createAdvertisement(@RequestBody AdvertisementCreateUpdateDTO creationDTO) {

        Advertisement adv = advService.createAdvertisement(creationDTO);
        return adv == null ?ResponseEntity.badRequest().body("Error creating advertisement") : ResponseEntity.created(URI.create("/api/v1/advertisements/" + adv.getId())).body(adv);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> enableAdvertisement(@PathVariable Long id, @RequestBody AdvertisementCreateUpdateDTO updateDTO) {

        Advertisement adv = advService.updateAdvertisement(updateDTO, id);
        return adv == null ? ResponseEntity.notFound().build() :ResponseEntity.ok(adv);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertisement(@PathVariable Long id) {
        advService.deleteAdvertisement(id);
        return ResponseEntity.ok().body("Advertisement deleted");
    }
}
