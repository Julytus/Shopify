package com.julytus.DropShop.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.julytus.DropShop.dto.response.ErrorResponse;
import com.julytus.DropShop.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.Instant;

@Slf4j(topic = "AUTHENTICATION-ENTRY-POINT")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {
        log.error("Authentication failed");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(errorCode.getCode())
                .error(errorCode.getMessage())
                .path(request.getRequestURI())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        response.flushBuffer();
    }
}
