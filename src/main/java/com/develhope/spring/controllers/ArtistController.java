package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.ArtistRequestDTO;
import com.develhope.spring.dtos.responses.ArtistResponseDTO;
import com.develhope.spring.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.develhope.spring.entities.Artist;
import com.develhope.spring.servicies.interfaces.ArtistService;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/api/v1/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService; // Servizio responsabile delle operazioni relative agli artisti

    @Autowired
    private ModelMapper modelMapper; // Mapper per la conversione tra DTO ed entità

    // Endpoint per ottenere tutti gli artisti
    @GetMapping("/")
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    // Endpoint per ottenere un artista tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getArtistById(@PathVariable Long id) {
        Optional<Artist> artist = artistService.findArtist(id);
        if (artist.isPresent()) {
            return ResponseEntity.ok().body(new Response(200, "Artist found", artist));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "Artist not found"));
        }
    }

    // Endpoint per creare un nuovo artista
    @PostMapping("/")
    public ResponseEntity<Response> createArtist(@RequestBody ArtistRequestDTO artistDTO) {
        Artist artist = modelMapper.map(artistDTO, Artist.class);
        Artist newArtist = artistService.createArtist(artist);

        if (newArtist != null) {
            return ResponseEntity.ok().body(new Response(200, "Artist created successfully", newArtist));
        } else {
            return ResponseEntity.status(400).body(
                    new Response(
                            400,
                            "Failed to create artist"
                    )
            );
        }
    }

    // Endpoint per aggiornare un artista esistente
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateArtist(@PathVariable long id, @RequestBody ArtistRequestDTO artistDTO) {
        Artist artistToUpdate = modelMapper.map(artistDTO, Artist.class);
        Artist updatedArtist = artistService.updateArtist(id, artistToUpdate);
        if (updatedArtist != null) {
            return ResponseEntity.ok().body(new Response(200, "Artist updated successfully", updatedArtist));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "Artist not found"));
        }
    }

    // Endpoint per eliminare un artista tramite ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteArtist(@PathVariable long id) {
        boolean deleted = artistService.deleteArtist(id);
        if (deleted) {
            return ResponseEntity.ok().body(new Response(200, "Artist deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "Artist not found"));
        }
    }
}

