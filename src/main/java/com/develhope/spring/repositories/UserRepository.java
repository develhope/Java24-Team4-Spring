package com.develhope.spring.repositories;

import com.develhope.spring.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaccia di repository per l'entità User
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
