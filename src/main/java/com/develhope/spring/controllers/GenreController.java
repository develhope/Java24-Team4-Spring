package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.GenreRequestDTO;
import com.develhope.spring.dtos.responses.GenreResponseDTO;
import com.develhope.spring.entities.Genre;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.GenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Response> getAllGenres() {
        List<GenreResponseDTO> genresList = genreService.getAllGenres();

        return ResponseEntity.ok(new Response(HttpStatus.OK.toString(), "Genres found: " + genresList.size(), genresList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getGenreById(@PathVariable long id) {
        GenreResponseDTO genre = genreService.findGenreById(id);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.toString(), "genre found", genre));

    }

    @PostMapping
    public ResponseEntity<Response> createGenre(@RequestBody GenreRequestDTO genreDTO) {

        GenreResponseDTO newGenre = genreService.createGenre(genreDTO);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.toString(), "genre created successfully", newGenre));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> updateGenre(@PathVariable long id, @RequestBody GenreRequestDTO genre) {
        GenreResponseDTO updateGenre = genreService.updateGenre(id, genre);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.toString(), "Genre updated successfully!", updateGenre));

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteGenre(@PathVariable long id) {
        GenreResponseDTO deletedGenre = genreService.deleteGenre(id);

        return ResponseEntity.ok().body(new Response(HttpStatus.OK.toString(), "Genre deleted successfully", deletedGenre));
    }
}
