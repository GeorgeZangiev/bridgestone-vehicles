package com.bridgestone.vehicles.model;

import com.bridgestone.vehicles.enums.VehicleStatus;
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
    private Long id;
    private Long vehicleId;
    private String location;
    private Double temperature;
    private VehicleStatus status;
    private LocalDateTime dateTime;
    private String description;
}