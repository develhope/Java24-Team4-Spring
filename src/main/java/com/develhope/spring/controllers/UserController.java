package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.UserRequestDTO;
import com.develhope.spring.entities.User;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService; // Servizio responsabile delle operazioni relative agli utenti

    @Autowired
    private ModelMapper modelMapper; // Mapper per la conversione tra DTO ed entità

    // Endpoint per ottenere tutti gli utenti
    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint per ottenere un utente tramite ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findUser(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(new Response(200, "user found", user));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "user not found"));
        }
    }

    // Endpoint per creare un nuovo utente
    @PostMapping("/")
    public ResponseEntity<Response> createUser(@RequestBody UserRequestDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        User newUser = userService.createUser(user);

        if (newUser != null) {
            return ResponseEntity.ok().body(
                    new Response(
                            200, "User created successfully", newUser));
        } else {
            return ResponseEntity.status(400).body(
                    new Response(
                            400,
                            "Failed to create user"
                    )
            );
        }
    }

    // Endpoint per aggiornare un utente esistente
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok().body(new Response(200, "User updated successfully", updatedUser));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "User not found"));
        }
    }

    // Endpoint per eliminare un utente tramite ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.ok().body(new Response(200, "User deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new Response(404, "User not found"));
        }
    }
}
