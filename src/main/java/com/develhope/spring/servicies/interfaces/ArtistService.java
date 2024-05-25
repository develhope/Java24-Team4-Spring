package com.develhope.spring.servicies.interfaces;

import com.develhope.spring.entities.Artist;

import java.util.List;
import java.util.Optional;

// Interfaccia per il servizio di gestione degli utenti
public interface ArtistService {

    // Metodo per creare un nuovo artista
    Artist createArtist(Artist aritst);

    // Metodo per ottenere tutti gli artisti
    List<Artist> getAllArtists();

    // Metodo per trovare un artista tramite ID
    Optional<Artist> findArtist(long id);

    // Metodo pe aggiornare un artista esistente
    Artist updateArtist(long id, Artist artist);

    // Metodo per eliminare un artista
    boolean deleteArtist(long id);
}
