package com.spring.demoparkapi.repository;

import com.spring.demoparkapi.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
}
