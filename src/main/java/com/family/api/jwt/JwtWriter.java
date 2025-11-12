package com.family.api.jwt;

import com.family.api.dto.ApiResponse;
import com.family.api.type.JWTEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtWriter {
    private final ObjectMapper objectMapper;

    public void write(JWTEnum jwtEnum, HttpServletResponse response, int errorNum) throws IOException{
        response.setStatus(errorNum);

        ApiResponse apiResponse = ApiResponse.builder()
                .msg(jwtEnum.getKey())
                .data(jwtEnum.getValue())
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
