package com.spring.demoparkapi.web.controller;

import com.spring.demoparkapi.entity.User;
import com.spring.demoparkapi.service.UserService;
import com.spring.demoparkapi.web.dto.UserCreateDto;
import com.spring.demoparkapi.web.dto.UserPasswordDto;
import com.spring.demoparkapi.web.dto.UserResponseDto;
import com.spring.demoparkapi.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto user) {
        User savedUser = userService.save(UserMapper.toUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(savedUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(UserMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(id, userPasswordDto.getCurrentPassword(), userPasswordDto.getUpdatedPassword(), userPasswordDto.getUpdatedPasswordConfirm());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(users));
    }
}
