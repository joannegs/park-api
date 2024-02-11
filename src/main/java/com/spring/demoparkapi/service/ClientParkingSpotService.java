package com.spring.demoparkapi.service;

import com.spring.demoparkapi.entity.ClientParkingSpot;
import com.spring.demoparkapi.exception.EntityNotFoundException;
import com.spring.demoparkapi.repository.ClientParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
