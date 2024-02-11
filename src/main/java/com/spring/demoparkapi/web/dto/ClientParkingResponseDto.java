package com.spring.demoparkapi.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientParkingResponseDto {
    private String vehicleRegistration;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleColor;
    private String clientCpf;
    private String receipt;

    @JsonFormat(pattern = "yyy-MM-dd hh:mm:ss")
    private LocalDate checkinDate;

    @JsonFormat(pattern = "yyy-MM-dd hh:mm:ss")
    private LocalDate checkoutDate;
    private String parkingSpotCode;
    private BigDecimal value;
    private BigDecimal discount;

}
