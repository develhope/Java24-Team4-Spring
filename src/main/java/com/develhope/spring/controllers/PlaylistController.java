package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.PlaylistRequestDTO;
import com.develhope.spring.dtos.requests.PlaylistUpdateDTO;
import com.develhope.spring.dtos.responses.PlaylistResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.implementations.PlaylistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    private final PlaylistServiceImpl playlistService;

    @Autowired
    public PlaylistController(PlaylistServiceImpl playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getPlaylistById(@PathVariable Long id) {
        PlaylistResponseDTO playlist = playlistService.getPlaylistById(id);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Playlist found.", playlist)
        );
    }

    @GetMapping
    public ResponseEntity<Response> getAllPlaylists() {
        List<PlaylistResponseDTO> playlists = playlistService.getAllPlaylists();

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Playlists found.", playlists)
        );
    }

    @PostMapping
    public ResponseEntity<Response> createPlaylist(@RequestBody PlaylistRequestDTO request) {
        PlaylistResponseDTO playlist = playlistService.createPlaylist(request);

        return ResponseEntity.ok().body(
                new Response(HttpStatus.OK.value(), "Playlist created successfully.", playlist)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistUpdateDTO request) {
        PlaylistResponseDTO playlist = playlistService.updatePlaylist(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Playlist update successfully.", playlist)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        PlaylistResponseDTO playlist = playlistService.deletePlaylistById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new Response(HttpStatus.NO_CONTENT.value(), "Playlist deleted successfully.", playlist)
        );
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteAllPlaylists() {
        playlistService.deleteAllPlaylists();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new Response(HttpStatus.NO_CONTENT.value(), "All playlists deleted.")
        );
    }
}
