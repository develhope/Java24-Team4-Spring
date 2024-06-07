package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.UserWithRoleDetailsResponseDTO;
import com.develhope.spring.models.ExHandler;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController extends ExHandler {

    @Autowired
    private UserService userService; // Servizio responsabile delle operazioni relative agli utenti

    @Autowired
    private ModelMapper modelMapper; // Mapper per la conversione tra DTO ed entità


    @GetMapping
    public ResponseEntity<Response> getAllUsers() {

        List<UserWithRoleDetailsResponseDTO> users = userService.getAllUsers();

        return !users.isEmpty() ? ResponseEntity.ok().body(new Response(200, "users found", users)) :
                ResponseEntity.status(404).body(new Response(404, "User list is empty"));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        Optional<UserWithRoleDetailsResponseDTO> user = userService.getUserById(id);

        return user.isPresent() ? ResponseEntity.ok().body(new Response(200, "user found", user))
                : ResponseEntity.status(404).body(new Response(404, "User not found"));

    }

    // Endpoint per creare un nuovo utente
    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody UserCreationDTO request)
            throws InvocationTargetException, IllegalAccessException {
        Optional<?> newUser = userService.createUser(request);

        return newUser.isPresent() ? ResponseEntity.ok().body(new Response(200, "User created successfully", newUser)) :
                ResponseEntity.status(400).body(new Response(400, "Failed to create user"));

    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@RequestBody UserCreationDTO user, @PathVariable Long id)
            throws InvocationTargetException, IllegalAccessException {
        Optional<?> updatedUser = userService.updateUser(user, id);

        return updatedUser.isPresent() ? ResponseEntity.ok().body(
                new Response(200, "User updated successfully", updatedUser)) :

                ResponseEntity.status(404).body(new Response(404, "User not found")
                );

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable long id) {
        boolean deleted = userService.deleteUserById(id);

        return deleted ? ResponseEntity.ok().body(new Response(200, "User deleted successfully")) :
                ResponseEntity.status(404).body(new Response(404, "User not found"));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Response> deleteAllUsers() {
        userService.deleteAllUsers();

        return ResponseEntity.ok().body(new Response(200, "All users deleted successfully"));
    }
}
