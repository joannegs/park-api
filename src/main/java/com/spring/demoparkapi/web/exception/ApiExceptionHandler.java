package com.spring.demoparkapi.web.exception;


import com.spring.demoparkapi.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage>
    accessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        log.error("API Error - " + exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, exception.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMessage>
    forbiddenException(AccessDeniedException exception, HttpServletRequest request) {
        log.error("API Error - " + exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, "Not authorized"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage>
    argumentNotValidException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request,
            BindingResult result) {
                log.error("API Error - " + exception);
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Invalid input", result));
    }

    @ExceptionHandler({UsernameUniqueViolationException.class, CpfUniqueViolationException.class, UniqueCodeViolationException.class})
    public ResponseEntity<ErrorMessage>
    uniqueViolationException(RuntimeException exception, HttpServletRequest request) {
        log.error("API Error - " + exception);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage>
    entityNotFoundException(RuntimeException exception, HttpServletRequest request) {
        log.error("API Error - " + exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage>
    passwordInvalidException(RuntimeException exception, HttpServletRequest request) {
        log.error("API Error - " + exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                        .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }


}
