package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.SongRequestDTO;
import com.develhope.spring.dtos.responses.SongResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.implementations.SongServiceImpl;
import com.develhope.spring.services.interfaces.SongService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/song")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Response> getAllSongs() {
        List<SongResponseDTO> songs = songService.getAllSong();

        return ResponseEntity.ok().body(new Response(200,
                songs.size() + " song(s) found in the database.", songs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getSongById(@PathVariable Long id) {
        SongResponseDTO song = songService.findSongById(id);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Song found.", song));
    }

    @PostMapping
    public ResponseEntity<Response> createSong(@RequestBody SongRequestDTO request) {
        SongResponseDTO song = songService.createSong(request);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Song created successfully.", song));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateSong(@PathVariable Long id, @RequestBody SongRequestDTO request) {
        SongResponseDTO song = songService.updateSong(id, request);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Song updated successfully.", song));
    }

    @PostMapping("/fileService/{id}")
    public ResponseEntity<Response> uploadProfileImage(@RequestParam MultipartFile musicFile, @PathVariable Long id) {
        String uploadedFileUrl = songService.uploadSong(musicFile, id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Song uploaded uploaded successfully", uploadedFileUrl)
        );
    }

    @DeleteMapping("/fileService/{id}")
    public ResponseEntity<Response> deleteUserProfileImage(@PathVariable Long id) {
        songService.deleteSongFromMinioStorage(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Song deleted successfully")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteSongById(@PathVariable Long id) {
        SongResponseDTO songDeleted = songService.deleteSongById(id);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "Song deleted successfully.", songDeleted));
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteAllSongs() {
        songService.deleteAllSongs();

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(), "All songs have been removed."));
    }
}
