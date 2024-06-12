package com.develhope.spring.repositories;

import com.develhope.spring.dtos.requests.LikesSongRequestDTO;
import com.develhope.spring.dtos.responses.LikesSongResponseDTO;
import com.develhope.spring.entities.LikesSongs;
import com.develhope.spring.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesSongRepository extends JpaRepository<LikesSongs, Long> {
    List<LikesSongs> findByListenerId(Long listenerId);

    List<LikesSongs> findBySongId(Long songId);
}
