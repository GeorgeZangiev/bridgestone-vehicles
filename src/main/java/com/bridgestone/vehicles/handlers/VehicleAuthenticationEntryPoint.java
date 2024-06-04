package com.bridgestone.vehicles.handlers;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.model.HttpErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class VehicleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String SYSTEM = "PBE:bridgestone-vehicles";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        HttpErrorResponse httpErrorResponse = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(authException.getMessage())
                .reason(CodeEnum.AUTH_FAILED.getReason())
                .code(CodeEnum.AUTH_FAILED.getValue())
                .timestamp(OffsetDateTime.now())
                .build();

        response.setContentType("application/json");
        response.setStatus(CodeEnum.AUTH_FAILED.getStatus().value());
        response.getWriter().write(httpErrorResponse.toJson());
    }
}

