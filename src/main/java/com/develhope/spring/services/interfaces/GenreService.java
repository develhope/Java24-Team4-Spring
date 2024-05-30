package com.develhope.spring.services.interfaces;

import com.develhope.spring.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    // -Interfaccia per il servizio di gestione dei generi musicali-

    // Metodo per creare un nuovo genere musicale
    Genre createGenre(Genre genre );

    // Metodo per ottenere tutti i generi
    List<Genre> getAllGenres();

    // Metodo per trovare un genere tramite ID
    Optional<Genre> findGenreById(long id);

    // Metodo pe aggiornare un genere esistente
    Genre updateGenre(long id, Genre genre);

    // Metodo per eliminare un genere
    boolean deleteGenre(long id);
}

