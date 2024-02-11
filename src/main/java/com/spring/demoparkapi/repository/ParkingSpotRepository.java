package com.spring.demoparkapi.repository;

import com.spring.demoparkapi.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    Optional<ParkingSpot> findByCode(String code);
    Optional<ParkingSpot> findFirstByStatus(ParkingSpot.StateParkingSpot stateParkingSpot);
}
