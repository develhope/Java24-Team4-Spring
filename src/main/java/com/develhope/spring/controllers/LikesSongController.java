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
    public ResponseEntity<LikesSongResponseDTO> getLikeById(@PathVariable Long id) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.getLikeById(id);
        return responseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<List<LikesSongResponseDTO>> getLikesBySongId(@PathVariable Long songId) {
        List<LikesSongResponseDTO> responseDTOs = likesSongService.getLikesBySongId(songId);
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/listener/{listenerId}")
    public ResponseEntity<List<LikesSongResponseDTO>> getLikesByListenerId(@PathVariable Long listenerId) {
        List<LikesSongResponseDTO> responseDTOs = likesSongService.getLikesByListenerId(listenerId);
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping
    public ResponseEntity<Response> createLike(@RequestBody LikesSongRequestDTO requestDTO) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.createLike(requestDTO);
        return ResponseEntity.ok(new Response(200,"Like were added.",responseDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LikesSongResponseDTO> updateLike(@PathVariable Long id, @RequestBody LikesSongRequestDTO requestDTO) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.updateLike(id, requestDTO);
        return responseDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}