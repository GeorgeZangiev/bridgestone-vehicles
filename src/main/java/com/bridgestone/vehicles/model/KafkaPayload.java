package com.bridgestone.vehicles.model;

import com.bridgestone.vehicles.enums.VehicleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record KafkaPayload(
        @NotNull(message = "vehicleId cannot be null")
        @Schema(description = "Unique numeric identifier for vehicle", example = "52") Long vehicleId,
        @NotEmpty(message = "location cannot be empty or null")
        @Schema(description = "Location address or name",
                example = "C/ de Tarragona, 108, L'Eixample, 08015 Barcelona, Spain") String location,
        @NotNull(message = "temperature cannot be null")
        @Schema(description = "Current temperature registered by vehicle", example = "18.31") Double temperature,
        @NotNull(message = "status cannot be null")
        @Schema(description = "Current vehicle status", example = "AVAILABLE") VehicleStatus status,
        @Schema(description = "Description to the state, when necessary, for example status = BROKEN",
                example = "Engine has stopped working") String description
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KafkaPayload that = (KafkaPayload) o;
        return Objects.equals(vehicleId, that.vehicleId) && Objects.equals(location, that.location) && Objects.equals(temperature, that.temperature) && Objects.equals(description, that.description) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, location, temperature, status, description);
    }
}

