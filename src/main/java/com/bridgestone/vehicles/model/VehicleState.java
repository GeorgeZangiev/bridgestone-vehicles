package com.bridgestone.vehicles.model;

import com.bridgestone.vehicles.enums.VehicleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "vehicle_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique numeric identifier in DB", example = "101")
    private Long id;
    @Schema(description = "Unique numeric identifier for vehicle", example = "52")
    private Long vehicleId;
    @Schema(description = "Location address or name",
            example = "C/ de Tarragona, 108, L'Eixample, 08015 Barcelona, Spain")
    private String location;
    @Schema(description = "Current temperature registered by vehicle", example = "18.31")
    private Double temperature;
    @Schema(description = "Current vehicle status", example = "AVAILABLE")
    private VehicleStatus status;
    @Schema(description = "Timestamp at the moment of registering the state",
            example = "2024-06-01T20:15:03.884419")
    private LocalDateTime dateTime;
    @Schema(description = "Description to the state, when necessary, for example status = BROKEN",
            example = "Engine has stopped working")
    private String description;
}