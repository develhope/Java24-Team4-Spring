package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.GenreRequestDTO;
import com.develhope.spring.dtos.responses.GenreResponseDTO;
import com.develhope.spring.entities.Genre;

import java.util.List;

public interface GenreService {

    GenreResponseDTO createGenre(GenreRequestDTO genre);
    List<GenreResponseDTO> getAllGenres();
    GenreResponseDTO findGenreById(long id);
    GenreResponseDTO updateGenre(Long id, GenreRequestDTO genre);
    GenreResponseDTO deleteGenre(Long id);
}
