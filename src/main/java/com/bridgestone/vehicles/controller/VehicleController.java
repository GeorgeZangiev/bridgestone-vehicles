package com.bridgestone.vehicles.controller;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.exception.VehicleApplicationException;
import com.bridgestone.vehicles.model.KafkaPayload;
import com.bridgestone.vehicles.model.VehicleState;
import com.bridgestone.vehicles.repository.VehicleRepository;
import com.bridgestone.vehicles.service.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @ApiResponse(responseCode = "400", description = "Kafka payload have not been validated successfully"),
            @ApiResponse(responseCode = "401", description = "Not authorised (absent or invalid token)"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = KafkaPayload.class))),
            @ApiResponse(responseCode = "401", description = "Not authorised (absent or invalid token)"),
            @ApiResponse(responseCode = "404", description = "DB query returned an empty list"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
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
