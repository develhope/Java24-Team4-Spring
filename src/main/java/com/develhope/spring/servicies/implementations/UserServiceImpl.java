package com.develhope.spring.servicies.implementations;

import com.develhope.spring.entities.User;
import com.develhope.spring.repositories.UserRepository;
import com.develhope.spring.servicies.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.getById(id);
    }

    @Override
    public User updateUser(long id, User user) {
        user.setId(id);
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
