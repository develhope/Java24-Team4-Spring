package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.entities.Album;
import com.develhope.spring.services.interfaces.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable("id") Long id) {
        Optional<AlbumResponseDTO> album = albumService.getAlbumById(id);
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlbumResponseDTO>> getAllAlbums() {
        List<AlbumResponseDTO> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @PostMapping("/create")
    public ResponseEntity<AlbumResponseDTO> createAlbum(@RequestBody AlbumRequestDTO request) {
        Optional<AlbumResponseDTO> album = albumService.createAlbum(request);
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AlbumResponseDTO> updateAlbum(@PathVariable("id") Long id, @RequestBody AlbumRequestDTO request) {
        Optional<AlbumResponseDTO> album = albumService.updateAlbum(id, request);
        return album.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlbumById(@PathVariable("id") Long id) {
        Optional<Album> album = albumService.deleteAlbumById(id);
        if (album.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAllAlbums() {
        albumService.deleteAllAlbums();
        return ResponseEntity.noContent().build();
    }
}
