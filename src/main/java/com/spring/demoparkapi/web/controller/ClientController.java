package com.spring.demoparkapi.web.controller;

import com.spring.demoparkapi.entity.Client;
import com.spring.demoparkapi.entity.User;
import com.spring.demoparkapi.jwt.JwtUserDetails;
import com.spring.demoparkapi.repository.projection.ClientProjection;
import com.spring.demoparkapi.service.ClientService;
import com.spring.demoparkapi.service.UserService;
import com.spring.demoparkapi.web.dto.ClientCreateDto;
import com.spring.demoparkapi.web.dto.ClientResponseDto;
import com.spring.demoparkapi.web.dto.PageableDto;
import com.spring.demoparkapi.web.dto.UserResponseDto;
import com.spring.demoparkapi.web.dto.mapper.ClientMapper;
import com.spring.demoparkapi.web.dto.mapper.PageableMapper;
import com.spring.demoparkapi.web.dto.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.List;

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

    @Operation(summary = "Gets a client by id",
            security = @SecurityRequirement(name = "security"),
            description = "Request takes requires a Bearer token. Only Admin is authorized",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resource got successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ClientResponseDto.class))
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
    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDto> getById(@PathVariable Long id) {
        Client client = clientService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ClientMapper.toDto(client));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(Pageable pageable) {
        Page<ClientProjection> clients = clientService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(PageableMapper.toDto(clients));
    }

    @GetMapping("/details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Client client = clientService.getUserId(userDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ClientMapper.toDto(client));
    }
}
