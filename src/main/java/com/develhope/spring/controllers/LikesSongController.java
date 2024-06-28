package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.LikesSongRequestDTO;
import com.develhope.spring.dtos.responses.LikesSongResponseDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.LikesSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/likes")
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
            Response response = new Response(HttpStatus.OK.toString(), "Like retrieved successfully.", responseDTO.get());
            return ResponseEntity.ok(response);
        } else {
            Response response = new Response(HttpStatus.NOT_FOUND.toString(), "Like not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @GetMapping("/song/{songId}")
    public ResponseEntity<Response> getLikesBySongId(@PathVariable Long songId) {
        List<LikesSongResponseDTO> responseDTOs = likesSongService.getLikesBySongId(songId);
        Response response = new Response(HttpStatus.OK.toString(), "Likes retrieved successfully.", responseDTOs);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listener/{listenerId}")
    public ResponseEntity<Response> getLikesByListenerId(@PathVariable Long listenerId) {
        List<LikesSongResponseDTO> responseDTOs = likesSongService.getLikesByListenerId(listenerId);
        Response response = new Response(HttpStatus.OK.toString(), "Likes retrieved successfully.", responseDTOs);
        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Response> createLike(@RequestBody LikesSongRequestDTO requestDTO) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.createLike(requestDTO);
        Response response = new Response(HttpStatus.OK.toString(), "Like added successfully.", responseDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateLike(@PathVariable Long id, @RequestBody LikesSongRequestDTO requestDTO) {
        Optional<LikesSongResponseDTO> responseDTO = likesSongService.updateLike(id, requestDTO);

        if (responseDTO.isPresent()) {
            Response response = new Response(HttpStatus.OK.toString(), "Like updated successfully.", responseDTO.get());

            return ResponseEntity.ok(response);

        } else {
            Response response = new Response(HttpStatus.NOT_FOUND.toString(), "Like not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteLikeById(@PathVariable Long id){
        likesSongService.deleteLikeById(id);

        return ResponseEntity.ok(new Response(HttpStatus.OK.toString(), "Like deleted successfully"));
    }


    @DeleteMapping
    public ResponseEntity<Response> deleteAllLikes(@PathVariable Long id){
        likesSongService.deleteAllLikes();

        return ResponseEntity.ok(new Response(HttpStatus.OK.toString(), "All likes deleted successfully"));
    }

}


