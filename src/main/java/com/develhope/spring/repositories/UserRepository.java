package com.develhope.spring.repositories;

import com.develhope.spring.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByRole(UserEntity.Role role);

    Optional<UserEntity> findByEmail(String username);
}
