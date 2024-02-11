package com.spring.demoparkapi.repository;

import com.spring.demoparkapi.entity.ClientParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientParkingSpotRepository extends JpaRepository<ClientParkingSpot, Long> {
    Optional<ClientParkingSpot> findByReceiptAndCheckoutDateIsNull(String receipt);

    long countByClientCpfAndCheckoutDateIsNotNull(String cpf);
}
