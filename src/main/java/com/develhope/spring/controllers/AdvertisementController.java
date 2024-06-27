package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.AdvertisementRequestDTO;
import com.develhope.spring.dtos.requests.AdvertisementUpdateDTO;
import com.develhope.spring.dtos.responses.AdvertisementResponseDTO;
import com.develhope.spring.models.Response;
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

    @GetMapping("/displayAdv/{id}")
    public ResponseEntity<Response> displayAdvertisementToUser(@PathVariable Long id) {
        AdvertisementResponseDTO adv = advService.displayAdvertisementToUser(id);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Advertisement shown successfully", adv)
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllAdvertisements() {
        List<AdvertisementResponseDTO> advList = advService.getAllAdvertisements();

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(),
                        "Advertisements found: " + advList.size(), advList)
        );
    }

    @GetMapping("/findActive")
    public ResponseEntity<Response> getAllByActive(@RequestParam Boolean active) {
        List<AdvertisementResponseDTO> advList = advService.getAllByActive(active);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(),
                        "Advertisements(active = " + active +
                                ") found: " + advList.size() + ".", advList)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getAdvertisementById(@PathVariable Long id) {
        AdvertisementResponseDTO adv = advService.getAdvertisementById(id);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(),
                        "Advertisement found successfully.", adv)
        );
    }

    @PostMapping
    public ResponseEntity<Response> createAdvertisement(@RequestBody AdvertisementRequestDTO requestDTO
    ) {
        AdvertisementResponseDTO adv = advService.createAdvertisement(requestDTO);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(),
                        "The ad was created and linked to a advertiser with ID " +
                                requestDTO.getAdvertiserUserId()+ ".", adv)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAdvertisement(@PathVariable Long id,
                                                        @RequestBody AdvertisementUpdateDTO updateDTO
    ) {
        AdvertisementResponseDTO adv = advService.updateAdvertisement(updateDTO, id);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Advertisement updated successfully.", adv)
        );
    }

    @PatchMapping("/enableAdv/{id}")
    public ResponseEntity<Response> enableAdvertisement(@PathVariable Long id) {
        AdvertisementResponseDTO adv = advService.enableAdvertisement(id);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Advertisement with ID " + id +
                        " enabled successfully.", adv)
        );
    }

    @PatchMapping("/disableAdv/{id}")
    public ResponseEntity<Response> disableAdvertisement(@PathVariable Long id) {
        AdvertisementResponseDTO adv = advService.disableAdvertisement(id);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Advertisement with ID " + id +
                        " disabled successfully.", adv)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteAdvertisement(@PathVariable Long id) {
        AdvertisementResponseDTO adv = advService.deleteAdvertisement(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new Response(HttpStatus.NO_CONTENT.value(), "Advertisement deleted successfully.", adv)
        );
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteAllAdvertisements() {
        advService.deleteAllAdvertisements();

        return ResponseEntity.ok().body(
                new Response(HttpStatus.NO_CONTENT.value(), "All advertisements deleted.")
        );
    }
}
