package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.AlbumRequestDTO;
import com.develhope.spring.dtos.responses.AlbumResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<Response> createAlbum(@RequestBody AlbumRequestDTO albumRequestDTO) {
        AlbumResponseDTO albumResponseDTO = albumService.createAlbum(albumRequestDTO);
        Response response = new Response(HttpStatus.OK.toString(), "Album created successfully.", albumResponseDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Response> getAllAlbums() {
        List<AlbumResponseDTO> albumList = albumService.getAllAlbums();
        Response response = new Response(HttpStatus.OK.toString(), "Albums retrieved successfully.", albumList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> albumById(@PathVariable Long id) {
        AlbumResponseDTO albumResponseDTO = albumService.getAlbumById(id);

        return ResponseEntity.ok(new Response(HttpStatus.OK.toString(), "Album found", albumResponseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAlbum(@PathVariable Long id, @RequestBody AlbumRequestDTO albumRequestDTO) {

        AlbumResponseDTO updatedAlbum = albumService.updateAlbum(id, albumRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.toString(), "Album updated successfully.", updatedAlbum));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> albumDelete(@PathVariable Long id) {
        AlbumResponseDTO deleted = albumService.deleteAlbumById(id);
        Response response = new Response(HttpStatus.OK.toString(), "Album deleted successfully.", deleted);

        return ResponseEntity.ok(response);
    }

}
