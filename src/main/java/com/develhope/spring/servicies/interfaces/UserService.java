package com.develhope.spring.servicies.interfaces;

import com.develhope.spring.entities.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    User getUser(long id);
    User updateUser(long id, User user);
    void deleteUser(long id);
}

