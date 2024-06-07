package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.UserWithRoleDetailsResponseDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

// Interfaccia per il servizio di gestione degli utenti
public interface UserService {


    Optional<?> createUser(UserCreationDTO request) throws InvocationTargetException, IllegalAccessException;

    Optional<?> updateUser(UserCreationDTO request, Long ID) throws InvocationTargetException, IllegalAccessException;

    Optional<UserWithRoleDetailsResponseDTO> getUserById(Long id);

    List<UserWithRoleDetailsResponseDTO> getAllUsers();

    boolean deleteUserById(Long id);

    void deleteAllUsers();

}
