package com.spring.demoparkapi.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof InsufficientAuthenticationException) {
            log.info("Http Status 403 - Forbidden: {}", authException.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            log.info("Http Status 401 - Unauthorized: {}", authException.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("www-authenticate", "Bearer realm='api/v1/auth'");
        }
    }
}

