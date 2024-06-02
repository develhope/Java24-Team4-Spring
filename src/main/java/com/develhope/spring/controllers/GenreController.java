package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.GenreRequestDTO;
import com.develhope.spring.entities.Genre;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.GenreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Endpoint per ottenere tutti i genri musicali.
    @GetMapping("/")
    public List<Genre> getAllGenres(){
        return genreService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getGenreById(@PathVariable long id){
        Optional<Genre> genre = genreService.findGenreById(id);
        if(genre.isPresent()){
            return ResponseEntity.ok().body(new Response(200, "genre found", genre));
        } else {
            return ResponseEntity.ok().body(new Response(404, "genre not found", genre));
        }
    }

    // Metodo per creare un nuovo Genere
    @PostMapping("/")
    public ResponseEntity<Response> createGenre(@RequestBody GenreRequestDTO genreDTO){
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        Genre newGenre = genreService.createGenre(genre);

        if (newGenre != null){
            return ResponseEntity.ok().body(new Response(201,"genre created successfully", newGenre));
        }else {
            return ResponseEntity.ok().body(new Response(400, "Genre creation failed!"));
        }
    }

    //Endpoint per aggiornare un genere musicale esistente
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateGenre(@PathVariable long id, Genre genre){
        Genre updateGenre = genreService.updateGenre(id,genre);
        if(updateGenre != null){
            return ResponseEntity.ok().body(new Response(200, "Genre updated successfully!", updateGenre));
        }else{
            return ResponseEntity.ok().body(new Response(404, "Genre not found"));
        }
    }

    //Endpoint per eliminare un Genere musicale

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteGenre(@PathVariable long id){
        boolean deletedGenre = genreService.deleteGenre(id);
        if(deletedGenre){
            return ResponseEntity.ok().body(new Response(204,"Genre deleted successfully"));
        }else{
            return ResponseEntity.ok().body(new Response(404, "Genre not found"));
        }
    }
}
