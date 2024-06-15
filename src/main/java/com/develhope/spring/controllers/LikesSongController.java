package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.LikesSongRequestDTO;
import com.develhope.spring.dtos.responses.LikesSongResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.LikesSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
public class LikesSongController {

    private final LikesSongService likesSongService;

    @Autowired
    public LikesSongController(LikesSongService likesSongsService) {
        this.likesSongService = likesSongsService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getLikeById(@PathVariable Long id) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.getLikeById(id);
        if (responseDTO.isPresent()) {
            Response response = new Response(200, "Like retrieved successfully.", responseDTO.get());
            return ResponseEntity.ok(response);
        } else {
            Response response = new Response(404, "Like not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<Response> getLikesBySongId(@PathVariable Long songId) {
        List<LikesSongResponseDTO> responseDTOs = likesSongService.getLikesBySongId(songId);
        Response response = new Response(200, "Likes retrieved successfully.", responseDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listener/{listenerId}")
    public ResponseEntity<Response> getLikesByListenerId(@PathVariable Long listenerId) {
        List<LikesSongResponseDTO> responseDTOs = likesSongService.getLikesByListenerId(listenerId);
        Response response = new Response(200, "Likes retrieved successfully.", responseDTOs);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response> createLike(@RequestBody LikesSongRequestDTO requestDTO) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.createLike(requestDTO);
        Response response = new Response(200, "Like added successfully.", responseDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateLike(@PathVariable Long id, @RequestBody LikesSongRequestDTO requestDTO) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.updateLike(id, requestDTO);
        if (responseDTO.isPresent()) {
            Response response = new Response(200, "Like updated successfully.", responseDTO.get());
            return ResponseEntity.ok(response);
        } else {
            Response response = new Response(404, "Like not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }
}

