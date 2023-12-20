package com.spring.demoparkapi.web.controller;

import com.spring.demoparkapi.entity.User;
import com.spring.demoparkapi.service.UserService;
import com.spring.demoparkapi.web.dto.UserCreateDto;
import com.spring.demoparkapi.web.dto.UserPasswordDto;
import com.spring.demoparkapi.web.dto.UserResponseDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users",
description = "It contains all the operations related to the resources for user registration, editing, and reading")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Creates a new user",
            description = "Resource to create a new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Resource created successful",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User e-mail already signup",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Resource not proceed because of input data",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto user) {
        User savedUser = userService.save(UserMapper.toUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(savedUser));
    }

    @Operation(summary = "Gets a user by id",
            security = @SecurityRequirement(name = "security"),
            description = "Request takes requires a Bearer token. Restricted to Admin or Client",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resource got successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))
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
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(user));
    }

    @Operation(summary = "Updates user password",
            security = @SecurityRequirement(name = "security"),
            description = "Request takes requires a Bearer token. Restricted to Admin or Client",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resource patched successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Resource was not found",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Resource was not patched due to incorrect password",
                            content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Resource was not patched due to invalid data",
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

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND #id == authentication.principal.id")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(id, userPasswordDto.getCurrentPassword(), userPasswordDto.getUpdatedPassword(), userPasswordDto.getUpdatedPasswordConfirm());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Gets all signup users",
            security = @SecurityRequirement(name = "security"),
            description = "Request takes requires a Bearer token. Restricted to Admin",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Resource got successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "501",
                            description = "Resource was not retrieved due to an error",
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
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(users));
    }
}
