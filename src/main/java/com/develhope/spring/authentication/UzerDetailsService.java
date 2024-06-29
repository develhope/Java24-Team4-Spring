package com.develhope.spring.authentication;

import com.develhope.spring.entities.UserEntity;
import com.develhope.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UzerDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UzerDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            return User.builder()
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .roles(userEntity.getRole().toString())
                    .build();
        } else {
            throw new UsernameNotFoundException("User with email " +
                    email + " not found");
        }
    }
}
