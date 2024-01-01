package com.spring.demoparkapi.service;

import com.spring.demoparkapi.entity.ParkingSpot;
import com.spring.demoparkapi.exception.EntityNotFoundException;
import com.spring.demoparkapi.exception.UniqueCodeViolationException;
import com.spring.demoparkapi.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpot save(ParkingSpot parkingSpot) {
        try {
            return parkingSpotRepository.save(parkingSpot);
        } catch(DataIntegrityViolationException exception) {
            throw new UniqueCodeViolationException(String.format("Parking spot with code %s is already signup", parkingSpot.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public ParkingSpot getByCode(String code) {
        return parkingSpotRepository.findByCode(code).orElseThrow(() -> new EntityNotFoundException(String.format("Parking spot with code %s was not found", code)));
    }
}
