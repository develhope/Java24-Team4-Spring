package com.develhope.spring.services.implementations;

import com.develhope.spring.entities.User;
import com.develhope.spring.repositories.UserRepository;
import com.develhope.spring.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Implementazione del servizio per la gestione degli utenti
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository; // Repository per l'accesso ai dati degli utenti

    // Metodo per creare un nuovo utente
    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user); // Salva l'utente nel database e lo restituisce
    }

    // Metodo per ottenere tutti gli utenti
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Restituisce tutti gli utenti presenti nel database
    }

    // Metodo per trovare un utente tramite ID
    @Override
    public Optional<User> findUser(long id) {
        return userRepository.findById(id); // Restituisce l'utente con l'ID specificato, se presente
    }

    // Metodo per aggiornare un utente esistente
    @Override
    public User updateUser(long id, User user) {
        user.setId(id); // Imposta l'ID dell'utente da aggiornare
        return userRepository.saveAndFlush(user); // Salva le modifiche e restituisce l'utente aggiornato
    }

    // Metodo per eliminare un utente
    @Override
    public boolean deleteUser(long id) {
        if (userRepository.existsById(id)) { // Verifica se l'utente esiste
            userRepository.deleteById(id); // Elimina l'utente con l'ID specificato
            return true; // L'utente è stato eliminato con successo
        } else {
            return false; // L'utente non è stato trovato
        }
    }

}
