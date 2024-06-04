package com.bridgestone.vehicles.handlers;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.model.HttpErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.OffsetDateTime;

@Component
public class VehicleAccessDeniedHandler implements AccessDeniedHandler {

    private static final String SYSTEM = "PBE:bridgestone-vehicles";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        HttpErrorResponse httpErrorResponse = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(accessDeniedException.getMessage())
                .reason(CodeEnum.ACCESS_DENIED.getReason())
                .code(CodeEnum.ACCESS_DENIED.getValue())
                .timestamp(OffsetDateTime.now())
                .build();

        response.setContentType("application/json");
        response.setStatus(CodeEnum.ACCESS_DENIED.getStatus().value());
        response.getWriter().write(httpErrorResponse.toJson());
    }
}

