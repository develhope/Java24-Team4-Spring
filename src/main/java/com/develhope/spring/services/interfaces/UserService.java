package com.develhope.spring.services.interfaces;

import com.develhope.spring.dtos.requests.UserCreationDTO;
import com.develhope.spring.dtos.responses.UserWithRoleDetailsResponseDTO;
import com.develhope.spring.entities.User;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

// Interfaccia per il servizio di gestione degli utenti
public interface UserService {


    Optional<?> createUser(UserCreationDTO request) throws InvocationTargetException, IllegalAccessException;

    Optional<?> updateUser(UserCreationDTO request, Long ID) throws InvocationTargetException, IllegalAccessException;

    UserWithRoleDetailsResponseDTO getUserById(Long id);

    List<UserWithRoleDetailsResponseDTO> getAllUsers();

    List<UserWithRoleDetailsResponseDTO> getAllByRole(User.Role role);

    UserWithRoleDetailsResponseDTO deleteUserById(Long id);

    void deleteAllUsers();

    String uploadUserProfileImage(MultipartFile file, Long userID) throws FileSizeLimitExceededException;

}
