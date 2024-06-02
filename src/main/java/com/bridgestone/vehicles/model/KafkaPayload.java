package com.bridgestone.vehicles.model;

import com.bridgestone.vehicles.enums.VehicleStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record KafkaPayload(
        @NotNull(message = "vehicleId cannot be null") Long vehicleId,
        @NotEmpty(message = "location cannot be empty or null") String location,
        @NotNull(message = "temperature cannot be null") Double temperature,
        @NotNull(message = "status cannot be null") VehicleStatus status,
        String description
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

