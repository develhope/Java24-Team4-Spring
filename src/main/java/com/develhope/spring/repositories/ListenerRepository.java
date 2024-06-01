package com.develhope.spring.repositories;

import com.develhope.spring.entities.Listener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenerRepository extends JpaRepository<Listener, Long> {
}
