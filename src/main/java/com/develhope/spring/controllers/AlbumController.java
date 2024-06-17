package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<Response> createAlbum(@RequestBody AlbumRequestDTO albumRequestDTO) {
        AlbumResponseDTO albumResponseDTO = albumService.createAlbum(albumRequestDTO);
        Response response = new Response(200, "Album created successfully.", albumResponseDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllAlbums() {
        List<AlbumResponseDTO> albumList = albumService.getAllAlbums();
        Response response = new Response(200, "Albums retrieved successfully.", albumList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> albumById(@PathVariable Long id) {
        Optional<AlbumResponseDTO> albumResponseDTO = albumService.albumById(id);
        if (albumResponseDTO.isPresent()) {
            Response response = new Response(200, "Album retrieved successfully.", albumResponseDTO.get());
            return ResponseEntity.ok(response);
        } else {
            Response response = new Response(404, "Album not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAlbum(@PathVariable Long id, @RequestBody AlbumRequestDTO albumRequestDTO) {
        try {
            AlbumResponseDTO updatedAlbum = albumService.updateAlbum(id, albumRequestDTO);
            Response response = new Response(200, "Album updated successfully.", updatedAlbum);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Response response = new Response(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> albumDelete(@PathVariable Long id) {
        albumService.albumDelete(id);
        Response response = new Response(200, "Album deleted successfully.", null);
        return ResponseEntity.ok(response);
    }

}
