package com.spring.demoparkapi.service;

import com.spring.demoparkapi.entity.ClientParkingSpot;
import com.spring.demoparkapi.exception.EntityNotFoundException;
import com.spring.demoparkapi.repository.ClientParkingSpotRepository;
import com.spring.demoparkapi.repository.projection.ParkingClientProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
public class ClientParkingSpotService {

    private final ClientParkingSpotRepository clientParkingRepository;

    @Transactional
    public ClientParkingSpot save(ClientParkingSpot clientParkingSpot) {
        return clientParkingRepository.save(clientParkingSpot);
    }

    @Transactional(readOnly = true)
    public ClientParkingSpot getByReceipt(String receipt) {
        return clientParkingRepository.findByReceiptAndCheckoutDateIsNull(receipt).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Receipt '%s' not found ou check-out is already done", receipt)
                )
        );
    }
    @Transactional(readOnly = true)
    public long getParkingTimes(String cpf) {
        return clientParkingRepository.countByClientCpfAndCheckoutDateIsNotNull(cpf);
    }

    @Transactional(readOnly = true)
    public Page<ParkingClientProjection> getAllByClientCpf(String cpf, Pageable pageable) {
        return clientParkingRepository.findAllByClientCpf(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ParkingClientProjection> getAllByClientId(Long id, Pageable pageable) {
        return clientParkingRepository.findAllByClientId(id, pageable);
    }
}
