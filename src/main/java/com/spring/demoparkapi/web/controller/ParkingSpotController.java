package com.spring.demoparkapi.web.controller;

import com.spring.demoparkapi.entity.ParkingSpot;
import com.spring.demoparkapi.service.ParkingSpotService;
import com.spring.demoparkapi.web.dto.ParkingSpotCreateDto;
import com.spring.demoparkapi.web.dto.ParkingSpotResponseDto;
import com.spring.demoparkapi.web.dto.mapper.ParkingSpotMapper;
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
@RequestMapping("/api/v1/parking-spot")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @Operation(summary = "Creates a new parking spot",
            description = "Resource to create a new parking sport. Request takes requires a Bearer token. Only Admin is authorized",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resource created successful",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL of the created resource")
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Parking spot is already signup",
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
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ParkingSpotCreateDto parkingSpotDto) {
        ParkingSpot parkingSpot = ParkingSpotMapper.toParkingSpot(parkingSpotDto);
        parkingSpotService.save(parkingSpot);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{code}")
                .buildAndExpand(parkingSpot.getCode())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Find a parking spot",
            description = "Resource to return a parking spot by its code. Request takes requires a Bearer token. Only Admin is authorized",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resource got successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ParkingSpotResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resource not found",
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
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingSpotResponseDto> getByCode(@PathVariable String code) {
        ParkingSpot parkingSpot = parkingSpotService.getByCode(code);
        return ResponseEntity.ok(ParkingSpotMapper.toDto(parkingSpot));
    }
}
