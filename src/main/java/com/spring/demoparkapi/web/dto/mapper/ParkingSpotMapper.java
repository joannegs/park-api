package com.spring.demoparkapi.web.dto.mapper;


import com.spring.demoparkapi.entity.ParkingSpot;
import com.spring.demoparkapi.web.dto.ParkingSpotCreateDto;
import com.spring.demoparkapi.web.dto.ParkingSpotResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpotMapper {
    public static ParkingSpot toParkingSpot(ParkingSpotCreateDto parkingSpotDto) {
        return new ModelMapper().map(parkingSpotDto, ParkingSpot.class);
    }

    public static ParkingSpotResponseDto toDto(ParkingSpot parkingSpot) {
        return new ModelMapper().map(parkingSpot, ParkingSpotResponseDto.class);
    }
}
