package com.develhope.spring.servicies.implementations;

import com.develhope.spring.entities.User;
import com.develhope.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(long id) {
        return userRepository.getById(id);
    }

    public User updateUser(long id, User user) {
        user.setId(id);
        return userRepository.saveAndFlush(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}