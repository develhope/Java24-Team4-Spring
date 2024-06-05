package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.SongRequestDTO;
import com.develhope.spring.entities.Song;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.SongService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vi/song")
public class SongController {
    @Autowired
    private SongService songService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public List<Song> getAllSongs(){
        return songService.getAllSong();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getSongById(@PathVariable int id) {
        Optional<Song> song = songService.findSong(id);
        if (song.isPresent()) {
            return ResponseEntity.ok().body(new Response(200, "song found", song));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "song not found"));
        }
    }
    @PostMapping("/")
    public ResponseEntity<Response> createSong(@RequestBody SongRequestDTO songDTO) {
       Song song = modelMapper.map(songDTO,Song.class);
        Song newSong = songService.createSong(song);

        if (newSong != null) {
            return ResponseEntity.ok().body(
                    new Response(
                            200, "song created successfully", newSong));
        } else {
            return ResponseEntity.status(400).body(
                    new Response(
                            400,
                            "Failed to create song"
                    )
            );
        }
    }
}
