package com.spring.demoparkapi.web.exception;


import com.spring.demoparkapi.exception.PasswordInvalidException;
import com.spring.demoparkapi.exception.EntityNotFoundException;
import com.spring.demoparkapi.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

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

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErrorMessage>
    usernameUniqueViolationException(RuntimeException exception, HttpServletRequest request) {
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
