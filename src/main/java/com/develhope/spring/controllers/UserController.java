package com.develhope.spring.controllers;

import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.UserWithRoleDetailsResponseDTO;
import com.develhope.spring.entities.User;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.UserService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<Response> getAllUsers() {

        List<UserWithRoleDetailsResponseDTO> users = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Users found: " + users.size() + ".", users)
        );
    }

    @GetMapping("/byRole")
    public ResponseEntity<Response> getAllByRole(@RequestParam User.Role role) {
        List<UserWithRoleDetailsResponseDTO> users = userService.getAllByRole(role);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Users with role " +
                        role + " found: " + users.size() + ".", users)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        UserWithRoleDetailsResponseDTO user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "User found.", user)
        );
    }

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody UserCreationDTO request)
            throws InvocationTargetException, IllegalAccessException {
        Optional<?> user = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "User created successfully.", user)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@RequestBody UserCreationDTO creationDTO, @PathVariable Long id)
            throws InvocationTargetException, IllegalAccessException {
        Optional<?> user = userService.updateUser(creationDTO, id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "User updated successfully.", user)
        );
    }

    //todo: aggiungere exceptions in GlobalExHandler
    //todo: aggiungere removeProfileImage method
    @PatchMapping("/profilePhoto/{id}")
    public ResponseEntity<Response> uploadProfileImage(@RequestParam MultipartFile imageFile, @PathVariable Long id) throws FileSizeLimitExceededException {
        String uploadedFileUrl = userService.uploadUserProfileImage(imageFile,id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Profile image uploaded successfully", uploadedFileUrl)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUserByID(@PathVariable long id) {

        UserWithRoleDetailsResponseDTO user = userService.deleteUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "User deleted successfully.", user)
        );
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Response> deleteAllUsers() {
        userService.deleteAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "All users deleted")
        );
    }
}
