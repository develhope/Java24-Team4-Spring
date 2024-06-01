package com.develhope.spring.repositories;

import com.develhope.spring.entities.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByActiveTrue();

    List<Advertisement> findByActiveFalse();
}
