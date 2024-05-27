package com.develhope.spring.services.interfaces;

import com.develhope.spring.entities.Listener;
import java.util.List;
import java.util.Optional;

// Interfaccia per il servizio di gestione degli utenti ascoltatori
public interface ListenerService {

    // Metodo per creare un nuovo ascoltatore
    Listener createListener(Listener listener);

    // Metodo per ottenere tutti gli ascoltatori
    List<Listener> getAllListeners();

    // Metodo per trovare un ascoltatore tramite ID
    Optional<Listener> findListener(long id);

    // Metodo pe aggiornare un ascoltatore esistente
    Listener updateListener(long id, Listener listener);

    // Metodo per eliminare un ascoltatore
    boolean deleteListener(long id);

}
