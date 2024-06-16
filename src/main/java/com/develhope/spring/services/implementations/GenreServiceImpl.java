package com.develhope.spring.services.implementations;

import com.develhope.spring.entities.Genre;
import com.develhope.spring.repositories.GenreRepository;
import com.develhope.spring.services.interfaces.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    // Metodo per creare un genere musicale
    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    //  Metodo che restituisce tutti i generi musicali presenti.
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    // Metodo per cercare un genere musicale tramite ID
    @Override
    public Optional<Genre> findGenreById(long id) {
        return genreRepository.findById(id);
    }

    // Metodo per aggiornare un genere musicale
    @Override
    public Genre updateGenre(long id, Genre genre) {
        genre.setId(id);
        return genreRepository.save(genre);
    }

    // Metodo per eliminare un genere musicale
    @Override
    public boolean deleteGenre(long id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
