package com.bridgestone.vehicles.controller;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.exception.VehicleApplicationException;
import com.bridgestone.vehicles.model.KafkaPayload;
import com.bridgestone.vehicles.model.VehicleState;
import com.bridgestone.vehicles.repository.VehicleRepository;
import com.bridgestone.vehicles.service.KafkaProducerService;
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

    @PostMapping("/status")
    public ResponseEntity<KafkaPayload> sendStatus(@Valid @RequestBody KafkaPayload payload) {
        kafkaProducerService.sendMessage(payload);
        return ResponseEntity.ok().body(payload);
    }

    @GetMapping("/history")
    public ResponseEntity<List<VehicleState>> getHistory(@RequestParam("id") Long vehicleId) {
        var entities = vehicleRepository.findAllByVehicleId(vehicleId);
        if (entities.isEmpty()) {
            throw new VehicleApplicationException("No entities were found for vehicleId: " + vehicleId, CodeEnum.NOT_FOUND);
        }
        return ResponseEntity.ok().body(entities);
    }
}
