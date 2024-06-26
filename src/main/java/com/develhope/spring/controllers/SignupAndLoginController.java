package com.develhope.spring.controllers;

import com.develhope.spring.authentication.JWTService;
import com.develhope.spring.authentication.UzerDetailsService;
import com.develhope.spring.dtos.requests.LoginDTO;
import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.models.Response;
import com.develhope.spring.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
public class SignupAndLoginController {

    private final AuthenticationManager authenticationManager;
    private final UzerDetailsService uzerDetailsService;
    private final JWTService jwtService;
    private final UserService userService;

    @Autowired
    public SignupAndLoginController(AuthenticationManager authenticationManager, UzerDetailsService uzerDetailsService, JWTService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.uzerDetailsService = uzerDetailsService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String loginAndGetToken(@RequestBody LoginDTO loginForm){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password())
        );
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(uzerDetailsService.loadUserByUsername(loginForm.username()));

        } else throw new UsernameNotFoundException("Invalid credentials");
    }

    @PostMapping("/signup")
    public ResponseEntity<Response> signup(@RequestBody UserCreationDTO request)
            throws InvocationTargetException, IllegalAccessException {
        Optional<?> user = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new Response(HttpStatus.OK.value(), "User created successfully.", user)
        );
    }


}
