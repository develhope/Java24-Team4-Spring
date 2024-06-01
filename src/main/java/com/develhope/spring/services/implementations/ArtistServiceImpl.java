package com.develhope.spring.services.implementations;

import com.develhope.spring.entities.Artist;
import com.develhope.spring.repositories.ArtistRepository;
import com.develhope.spring.services.interfaces.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Implementazione del servizio per la gestione degli artisti
@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository; // Repository per l'accesso ai dati degli artisti

    // Metodo per creare un nuovo artista
    @Override
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist); // Salva l'artista nel database e lo restituisce
    }

    // Metodo per ottenere tutti gli artisti
    @Override
    public List<Artist> getAllArtists() {
        return artistRepository.findAll(); // Restituisce tutti gli utenti presenti nel database
    }

    // Metodo per trovare un artista tramite ID
    @Override
    public Optional<Artist> findArtist(long id) {
        return artistRepository.findById(id); // Restituisce l'artista con L'ID specificato
    }

    // Metodo per aggiornare un artista
    @Override
    public Artist updateArtist(long id, Artist artist) {
        artist.setId(id); // Imposta l'ID dell'artista da aggiornare
        return artistRepository.save(artist);
    }

    // Metodo per eliminare un artista
    @Override
    public boolean deleteArtist(long id) {
        if (artistRepository.existsById(id)) { // verifica se l'tente esiste
            artistRepository.deleteById(id); // Elimina l'utente con l'ID specificato
            return true; // L'artista è stato eliminato con successo
        } else {
            return false; // L'artista non è stato trovato
        }
    }
}
