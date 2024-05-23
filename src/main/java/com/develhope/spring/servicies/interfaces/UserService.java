package com.develhope.spring.servicies.interfaces;

import com.develhope.spring.entities.User;
import java.util.List;
import java.util.Optional;

// Interfaccia per il servizio di gestione degli utenti
public interface UserService {

    // Metodo per creare un nuovo utente
    User createUser(User user);

    // Metodo per ottenere tutti gli utenti
    List<User> getAllUsers();

    // Metodo per trovare un utente tramite ID
    Optional<User> findUser(long id);

    // Metodo per aggiornare un utente esistente
    User updateUser(long id, User user);

    // Metodo per eliminare un utente
    boolean deleteUser(long id);

}
