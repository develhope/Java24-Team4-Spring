package com.develhope.spring.controllers;

import com.develhope.spring.authentication.JWTService;
import com.develhope.spring.authentication.UzerDetailsService;
import com.develhope.spring.dtos.requests.LoginDTO;
import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.UserWithRoleDetailsResponseDTO;
import com.develhope.spring.entities.UserEntity;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final UzerDetailsService uzerDetailsService;
    private final JWTService jwtService;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, AuthenticationManager authenticationManager, UzerDetailsService uzerDetailsService, JWTService jwtService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.uzerDetailsService = uzerDetailsService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<Response> getAllUsers() {

        List<UserWithRoleDetailsResponseDTO> users = userService.getAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Users found: " + users.size() + ".", users)
        );
    }

    @GetMapping("/byRole")
    public ResponseEntity<Response> getAllByRole(@RequestParam UserEntity.Role role) {
        List<UserWithRoleDetailsResponseDTO> users = userService.getAllByRole(role);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Users with role " +
                        role + " found: " + users.size() + ".", users)
        );
    }

    @GetMapping("/myProfile")
    public ResponseEntity<Response> getCurrentUser(){
        UserWithRoleDetailsResponseDTO user = userService.getCurrentUser();

        return ResponseEntity.status(HttpStatus.OK).body(new Response(HttpStatus.OK.value(), "Current user found.", user));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {
        UserWithRoleDetailsResponseDTO user = userService.getUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "User found.", user)
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


    @PostMapping("/fileService/{id}")
    public ResponseEntity<Response> uploadProfileImage(@RequestParam MultipartFile imageFile, @PathVariable Long id) {
        String uploadedFileUrl = userService.uploadUserProfileImage(imageFile,id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Profile image uploaded successfully", uploadedFileUrl)
        );
    }

    @DeleteMapping("/fileService/{id}")
    public ResponseEntity<Response> deleteUserProfileImage(@PathVariable Long id) {
        userService.deleteUserProfileImg(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "Profile image deleted successfully")
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
