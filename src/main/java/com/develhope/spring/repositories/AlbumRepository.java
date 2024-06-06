package com.develhope.spring.repositories;

import com.develhope.spring.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository <Album,Long> {
}