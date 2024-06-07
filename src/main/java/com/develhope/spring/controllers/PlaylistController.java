package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.PlaylistRequestDTO;
import com.develhope.spring.dtos.responses.PlaylistResponseDTO;
import com.develhope.spring.services.implementations.PlaylistServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    private final PlaylistServiceImpl playlistService;

    @Autowired
    public PlaylistController(PlaylistServiceImpl playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylistById(@PathVariable Long id) {
        Optional<PlaylistResponseDTO> playlist = playlistService.getPlaylistById(id);

        return playlist.isPresent() ? ResponseEntity.ok(playlist) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found!");
    }

    @GetMapping
    public ResponseEntity<?> getAllPlaylists() {
        List<PlaylistResponseDTO> playlists = playlistService.getAllPlaylists();

        return !playlists.isEmpty() ? ResponseEntity.ok(playlists) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).body("Playlists list is empty!");
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistRequestDTO request) {
        Optional<PlaylistResponseDTO> playlist = playlistService.createPlaylist(request);

        return playlist.isPresent() ? ResponseEntity.ok(playlist) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistRequestDTO request) {
        Optional<PlaylistResponseDTO> playlist = playlistService.updatePlaylist(id, request);

        return playlist.isPresent() ? ResponseEntity.ok(playlist) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        Optional<PlaylistResponseDTO> playlist = playlistService.deletePlaylistById(id);

        return playlist.isPresent() ? ResponseEntity.noContent().build() :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found!");
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllPlaylists() {
        playlistService.deleteAllPlaylists();

        return ResponseEntity.noContent().build();
    }
}
