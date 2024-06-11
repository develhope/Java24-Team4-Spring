package com.develhope.spring.repositories;

import com.develhope.spring.entities.LikesSongs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesSongRepository extends JpaRepository<LikesSongs, Long> {

}

