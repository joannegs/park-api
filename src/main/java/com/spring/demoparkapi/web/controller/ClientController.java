package com.spring.demoparkapi.web.controller;

import com.spring.demoparkapi.entity.Client;
import com.spring.demoparkapi.jwt.JwtUserDetails;
import com.spring.demoparkapi.service.ClientService;
import com.spring.demoparkapi.service.UserService;
import com.spring.demoparkapi.web.dto.ClientCreateDto;
import com.spring.demoparkapi.web.dto.ClientResponseDto;
import com.spring.demoparkapi.web.dto.UserResponseDto;
import com.spring.demoparkapi.web.dto.mapper.ClientMapper;
import com.spring.demoparkapi.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clients",
        description = "It contains all the operations related to the resources for clients registration, editing, and reading")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientService clientService;
    private final UserService userService;

    @Operation(summary = "Creates a new client",
            description = "Resource to create a new client",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resource created successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "CPF already signup",
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
                            description = "Not authorized",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto clientDto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = ClientMapper.toClient(clientDto);
        client.setUser(userService.getById(userDetails.getId()));
        clientService.save(client);

            return ResponseEntity.status(HttpStatus.CREATED).body(ClientMapper.toDto(client));
    }
}
