package com.family.api.jwt;

import com.family.api.dto.ApiResponse;
import com.family.api.type.JWTEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtWriter jwtWriter;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        setResponse(response, "Don't have token information");
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        JWTEnum jwtEnum = JWTEnum.NO_TOKEN;
        log.error("JWT error message = {}", message);
        ObjectMapper objectMapper = new ObjectMapper();

        jwtWriter.write(jwtEnum,response, HttpServletResponse.SC_UNAUTHORIZED);
    }
}
