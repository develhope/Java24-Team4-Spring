package com.develhope.spring.controllers;

import com.develhope.spring.authentication.JWTService;
import com.develhope.spring.authentication.UzerDetailsService;
import com.develhope.spring.dtos.requests.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UzerDetailsService uzerDetailsService;
    private final JWTService jwtService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UzerDetailsService uzerDetailsService, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.uzerDetailsService = uzerDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public String loginAndGetToken(@RequestBody LoginDTO loginForm){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.username(), loginForm.password())
        );
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(uzerDetailsService.loadUserByUsername(loginForm.username()));

        } else throw new UsernameNotFoundException("Invalid credentials");
    }


}
