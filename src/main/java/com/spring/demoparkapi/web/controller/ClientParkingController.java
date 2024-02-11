package com.spring.demoparkapi.web.controller;

import com.spring.demoparkapi.entity.ClientParkingSpot;
import com.spring.demoparkapi.service.ClientParkingService;
import com.spring.demoparkapi.service.ClientParkingSpotService;
import com.spring.demoparkapi.service.ParkingSpotService;
import com.spring.demoparkapi.web.dto.ClientParkingCreateDto;
import com.spring.demoparkapi.web.dto.ClientParkingResponseDto;
import com.spring.demoparkapi.web.dto.ParkingSpotResponseDto;
import com.spring.demoparkapi.web.dto.mapper.ClientParkingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/client-parking")
public class ClientParkingController {
    private final ClientParkingService clientParkingService;
    private final ClientParkingSpotService clientParkingSpotService;

    @Operation(summary = "Creates a parking spot check-in",
            description = "Vehicle entry feature for parking. Request takes requires a Bearer token. Only Admin is authorized",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resource created successful",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "The client's CPF is not signup or no parking spot available",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Resource not proceed because of input data",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Not Authorized",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
            }
    )
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientParkingResponseDto> checkIn(@RequestBody @Valid ClientParkingCreateDto parkingSpotCreateDto) {
        ClientParkingSpot clientParkingSpot = ClientParkingMapper.toClientParking(parkingSpotCreateDto);
        clientParkingService.ckeckIn(clientParkingSpot);
        ClientParkingResponseDto response = ClientParkingMapper.toDto(clientParkingSpot);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{receipt}")
                .buildAndExpand(clientParkingSpot.getReceipt())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/check-in/{receipt}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ClientParkingResponseDto> getByReceipt(@PathVariable String receipt) {
        ClientParkingSpot clientParkingSpot = clientParkingSpotService.getByReceipt(receipt);
        ClientParkingResponseDto dto = ClientParkingMapper.toDto(clientParkingSpot);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/check-out/{receipt}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientParkingResponseDto> checkOut(@PathVariable String receipt) {
        ClientParkingSpot clientParkingSpot = clientParkingService.checkOut(receipt);
        ClientParkingResponseDto dto = ClientParkingMapper.toDto(clientParkingSpot);
        return ResponseEntity.ok(dto);
    }
}
