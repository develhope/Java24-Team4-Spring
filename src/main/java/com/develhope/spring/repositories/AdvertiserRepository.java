package com.develhope.spring.repositories;

import com.develhope.spring.entities.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser, Long> {

}
