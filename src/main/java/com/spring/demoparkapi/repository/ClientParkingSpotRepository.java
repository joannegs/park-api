package com.spring.demoparkapi.repository;

import com.spring.demoparkapi.entity.ClientParkingSpot;
import com.spring.demoparkapi.repository.projection.ParkingClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientParkingSpotRepository extends JpaRepository<ClientParkingSpot, Long> {
    Optional<ClientParkingSpot> findByReceiptAndCheckoutDateIsNull(String receipt);

    long countByClientCpfAndCheckoutDateIsNotNull(String cpf);

    Page<ParkingClientProjection> findAllByClientCpf(String cpf, Pageable pageable);

    Page<ParkingClientProjection> findAllByClientId(Long id, Pageable pageable);
}
