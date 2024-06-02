package com.bridgestone.vehicles.service;

import com.bridgestone.vehicles.enums.VehicleStatus;
import com.bridgestone.vehicles.model.VehicleState;
import com.bridgestone.vehicles.repository.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class KafkaConsumerService {

    private final VehicleRepository vehicleRepository;

    @KafkaListener(topics = "vehicles_history", groupId = "bridgestone_vehicles")
    public void consume(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(message);

            VehicleState vehicleState = VehicleState.builder()
                    .vehicleId(jsonNode.get("vehicleId").asLong())
                    .location(jsonNode.get("location").asText())
                    .temperature(jsonNode.get("temperature").asDouble())
                    .status(VehicleStatus.valueOf(jsonNode.get("status").asText()))
                    .dateTime(LocalDateTime.now())
                    .description(jsonNode.get("description").asText())
                    .build();

            vehicleRepository.save(vehicleState);
        } catch (JsonProcessingException e) {
            log.error("Failed to process message: {}", message);
        }
    }
}
