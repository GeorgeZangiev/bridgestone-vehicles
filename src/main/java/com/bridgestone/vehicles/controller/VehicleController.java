package com.bridgestone.vehicles.controller;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.exception.VehicleApplicationException;
import com.bridgestone.vehicles.model.HttpErrorResponse;
import com.bridgestone.vehicles.model.KafkaPayload;
import com.bridgestone.vehicles.model.VehicleState;
import com.bridgestone.vehicles.repository.VehicleRepository;
import com.bridgestone.vehicles.service.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "vehicle-controller",
        description = "API which allows to register current vehicle state and get history of states for particular vehicle")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final KafkaProducerService kafkaProducerService;
    private final VehicleRepository vehicleRepository;

    @Operation(summary = "Registers vehicle state",
            description = "receives KafkaPayload, validates it and sends to the topic",
            security = @SecurityRequirement(name = "bearerToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = KafkaPayload.class))),
            @ApiResponse(responseCode = "400", description = "Kafka payload has not been validated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"BAD_REQUEST\",\"reason\":\"Invalid payload data\",\"description\":\"Request is not valid\",\"system\":\"PBE.bridgestone-vehicles\",\"timestamp\":\"2024-06-01T20:15:03.884419+03:00\"}"))),
            @ApiResponse(responseCode = "401", description = "Not authorised (absent or invalid token)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"UNAUTHORIZED\",\"reason\":\"Invalid or missing token\",\"description\":\"Full authentication is required to access this resource\",\"system\":\"PBE.bridgestone-vehicles\",\"timestamp\":\"2024-06-01T20:15:03.884419+03:00\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"INTERNAL_SERVER_ERROR\",\"reason\":\"An unexpected error occurred\",\"description\":\"Something went wrong\",\"system\":\"PBE.bridgestone-vehicles\",\"timestamp\":\"2024-06-01T20:15:03.884419+03:00\"}")))
    })
    @PostMapping("/status")
    public ResponseEntity<KafkaPayload> sendStatus(@Valid @RequestBody KafkaPayload payload) {
        kafkaProducerService.sendMessage(payload);
        return ResponseEntity.ok().body(payload);
    }


    @Operation(summary = "Gets states history for particular vehicle",
            description = "receives vehicleId and returns list of VehicleState",
            security = @SecurityRequirement(name = "bearerToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleState.class),
                    examples = @ExampleObject(value = "[{\"id\":\"101\",\"vehicleId\":\"52\",\"location\":\"C/ de Tarragona, 108, L'Eixample, 08015 Barcelona, Spain\",\"temperature\":\"23.12\",\"status\":\"BROKEN\",\"dateTime\":\"2024-06-01T20:15:03.884419\",\"description\":\"some description\"}]"))),
            @ApiResponse(responseCode = "401", description = "Not authorised (absent or invalid token)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"UNAUTHORIZED\",\"reason\":\"Invalid or missing token\",\"description\":\"Full authentication is required to access this resource\",\"system\":\"PBE.bridgestone-vehicles\",\"timestamp\":\"2024-06-01T20:15:03.884419+03:00\"}"))),
            @ApiResponse(responseCode = "404", description = "DB query returned an empty list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"HISTORY_NOT_FOUND\",\"reason\":\"Failed to find History by vehicleId\",\"description\":\"Failed to find History by vehicleId\",\"system\":\"PBE.bridgestone-vehicles\",\"timestamp\":\"2024-06-01T20:15:03.884419+03:00\"}"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HttpErrorResponse.class),
                            examples = @ExampleObject(value = "{\"code\":\"INTERNAL_SERVER_ERROR\",\"reason\":\"An unexpected error occurred\",\"description\":\"Something went wrong\",\"system\":\"PBE.bridgestone-vehicles\",\"timestamp\":\"2024-06-01T20:15:03.884419+03:00\"}")))
    })
    @GetMapping("/history")
    public ResponseEntity<List<VehicleState>> getHistory(@RequestParam("id") Long vehicleId) {
        var entities = vehicleRepository.findAllByVehicleId(vehicleId);
        if (entities.isEmpty()) {
            throw new VehicleApplicationException("No entities were found for vehicleId: " + vehicleId, CodeEnum.HISTORY_NOT_FOUND);
        }
        return ResponseEntity.ok().body(entities);
    }
}
