package com.develhope.spring.services.implementations;

import com.develhope.spring.dtos.requests.GenreRequestDTO;
import com.develhope.spring.dtos.responses.GenreResponseDTO;
import com.develhope.spring.entities.Genre;
import com.develhope.spring.exceptions.EmptyResultException;
import com.develhope.spring.repositories.GenreRepository;
import com.develhope.spring.services.interfaces.GenreService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {


    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, ModelMapper modelMapper) {
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public GenreResponseDTO createGenre(GenreRequestDTO genre) {

        Genre toSave = modelMapper.map(genre, Genre.class);
        var genSaved = genreRepository.saveAndFlush(toSave);

        return modelMapper.map(genSaved, GenreResponseDTO.class);
    }

    @Override
    public List<GenreResponseDTO> getAllGenres() {

        var genreDTOList = genreRepository.findAll()
                .stream()
                .map(genre -> modelMapper.map(genre, GenreResponseDTO.class))
                .toList();

        if (genreDTOList.isEmpty()) throw new EmptyResultException("[Search error] No genres found in the database");

        return genreDTOList;
    }


    @Override
    public GenreResponseDTO findGenreById(long id) {

        return genreRepository.findById(id)
                .map(genre -> modelMapper.map(genre, GenreResponseDTO.class))
                .orElseThrow(() -> new EntityNotFoundException
                        ("[Search error] Genre with id " + id + " not found in the database.")
                );
    }

    @Override
    @Transactional
    public GenreResponseDTO updateGenre(Long id, GenreRequestDTO genre) {

        Genre toUpdate = genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException
                ("[Search error] Genre with id " + id + " not found in the database.")
        );
        if (genre.getTitle() != null && !genre.getTitle().isBlank()) {
            toUpdate.setId(toUpdate.getId());
            toUpdate.setTitle(genre.getTitle());
            //todo controllare se c`e in ex. handler
        } else throw new IllegalArgumentException("[Update error] request body contains empty/null fields");

        var genUpdated = genreRepository.saveAndFlush(toUpdate);

        return modelMapper.map(genUpdated, GenreResponseDTO.class);
    }


    @Override
    @Transactional
    public GenreResponseDTO deleteGenre(Long id) {
        Genre toDelete = genreRepository.findById(id).orElseThrow(() -> new EntityNotFoundException
                ("[Delete error] Genre with id " + id + " not found in the database")
        );
        genreRepository.deleteById(id);

        return modelMapper.map(toDelete, GenreResponseDTO.class);
    }
}
