package com.spring.demoparkapi.web.dto.mapper;

import com.spring.demoparkapi.entity.ClientParkingSpot;
import com.spring.demoparkapi.web.dto.ClientParkingCreateDto;
import com.spring.demoparkapi.web.dto.ClientParkingResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientParkingMapper {
    public static ClientParkingSpot toClientParking(ClientParkingCreateDto clientParkingDto) {
        return new ModelMapper().map(clientParkingDto, ClientParkingSpot.class);
    }

    public static ClientParkingResponseDto toDto(ClientParkingSpot clientParking) {
        return new ModelMapper().map(clientParking, ClientParkingResponseDto.class);
    }
}
