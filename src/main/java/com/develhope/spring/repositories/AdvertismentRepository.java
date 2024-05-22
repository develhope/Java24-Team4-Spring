package com.develhope.spring.repositories;

import com.develhope.spring.entities.AdvertisementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertismentRepository extends JpaRepository<AdvertisementEntity, Long> {
}
