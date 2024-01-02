package com.spring.demoparkapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientParkingResponseDto {
    private String vehicleRegistration;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleColor;
    private String clientCpf;
    private String receipt;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private String parkingSpotCode;
    private BigDecimal value;
    private BigDecimal discount;

}
