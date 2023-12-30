package com.spring.demoparkapi.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ParkingSpotResponseDto {
    private String id;
    private String code;
    private String status;
}
