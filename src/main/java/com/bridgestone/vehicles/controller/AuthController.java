package com.bridgestone.vehicles.controller;

import com.bridgestone.vehicles.model.HttpErrorResponse;
import com.bridgestone.vehicles.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "auth-controller", description = "The Authorisation API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @Operation(summary = "Generates JWT token",
            description = "receives login + password and returns JWT token as String",
            security = @SecurityRequirement(name = "basicAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = String.class),
                    examples = @ExampleObject(value = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiYWRtaW4iLCJleHAiOjE3MTc1ODUxNTAsImlhdCI6MTcxNzU4MTU1MCwic2NvcGUiOiJyZWFkIn0.DZHvITaj9jdUP8UdyG1zDlOTZ1T6mlrGau0T8rFicfyaoa4FW6MouraL0lPmt-tIzXf6otTX4F3DyitmB730wCIsH71fkhwGlXEqBWSeiyd_QfelTkc9_ZXm16HbZxVH65N5_VX_zynqvskELUMZC8XUX30xsEfTK_MvLvb12dtJlGAZMUUszt9cyMu3ieSvfi625dqjf2Mq0N8GlLIqalKxvO8Q5gM2PQFFzvJ0KP1hoze7859nPgx8Bw8X6K7KrUGQVjw4IrLAdQtwxk5zFDawi3ynVxdIboVMxOvsVSOGhuzYwr_yRUgGTw-f-u-JxKdFHgL4VhgnIJTZxxTlEA"))),
            @ApiResponse(responseCode = "401", description = "Not authorised (invalid login or password)",
                    content = @Content(schema = @Schema(implementation = HttpErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"UNAUTHORIZED\",\"reason\":\"Invalid login or password\",\"description\":\"Full authentication is required to access this resource\",\"system\":\"PBE.bridgestone-vehicles\",\"timestamp\":\"2024-06-01T20:15:03.884419+03:00\"}")))
    })
    @PostMapping("/token")
    public String token(Authentication authentication) {
        log.debug("Token requested for user: '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication);
        log.debug("Token granted: {}", token);
        return token;
    }
}
