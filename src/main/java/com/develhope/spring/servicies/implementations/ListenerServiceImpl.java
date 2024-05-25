package com.develhope.spring.servicies.implementations;

import com.develhope.spring.entities.Listener;
import com.develhope.spring.repositories.ListenerRepository;
import com.develhope.spring.servicies.interfaces.ListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Implementazione del servizio per la gestione degli ascolatori
@Service
public class ListenerServiceImpl implements ListenerService {

    @Autowired
    private ListenerRepository listenerRepository; // Repository per l'accesso ai dati degli ascolatori

    @Override
    public Listener createListener(Listener listener) {
        return listenerRepository.saveAndFlush(listener); // Salva l'ascolatore nel database e lo restituisce
    }

    @Override
    public List<Listener> getAllListeners() {
        return listenerRepository.findAll(); // Restituisce tutti gli ascolatori presenti nel database
    }

    @Override
    public Optional<Listener> findListener(long id) {
        return listenerRepository.findById(id); // Restituisce l'ascolatore con L'ID specificato
    }

    @Override
    public Listener updateListener(long id, Listener listener) {
        listener.setId(id); // Imposta l'ID dell'ascoltatore da aggiornare
        return listenerRepository.saveAndFlush(listener);
    }

    @Override
    public boolean deleteListener(long id) {
        if (listenerRepository.existsById(id)) { // verifica se l'ascoltatore esiste
            listenerRepository.deleteById(id); // Elimina l'ascoltatore con l'ID specificato
            return true; // L'ascoltatore è stato eliminato con successo
        } else {
            return false; // L'ascoltatore non è stato trovato
        }
    }
}