package com.develhope.spring.utils;

import com.develhope.spring.entities.UserEntity;
import com.develhope.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Security {

    private static UserRepository userRepository;

    @Autowired
    public Security(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("[Error] Current user not found"));
    }
}
