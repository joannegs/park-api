package com.spring.demoparkapi.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ParkingClientProjection {
    String getVehicleRegistration();
    String getVehicleBrand();
    String getVehicleModel();
    String getVehicleColor();
    String getClientCpf();
    String getReceipt();

    @JsonFormat(pattern = "yyy-MM-dd hh:mm:ss")
    LocalDate getCheckinDate();

    @JsonFormat(pattern = "yyy-MM-dd hh:mm:ss")
    LocalDate getCheckoutDate();

    String getParkingSpotCode();
    BigDecimal getValue();
    BigDecimal getDiscount();
}
