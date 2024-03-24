package com.spring.demoparkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ClientParkingCreateDto {
    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "The vehicle registration must follow the pattern XXX-000")
    private String vehicleRegistration;

    @NotBlank
    private String vehicleBrand;

    @NotBlank
    private String vehicleModel;

    @NotBlank
    private String vehicleColor;

    @NotBlank
    @Size(min = 11, max = 11)
    @CPF
    private String clientCpf;
}
