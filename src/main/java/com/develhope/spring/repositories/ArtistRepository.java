package com.develhope.spring.repositories;

import com.develhope.spring.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Interfaccia di repository per l'entità Artist
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
