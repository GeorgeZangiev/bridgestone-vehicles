package com.bridgestone.vehicles.service;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.exception.VehicleApplicationException;
import com.bridgestone.vehicles.model.KafkaPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final String TOPIC = "vehicles_history";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(KafkaPayload payload) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(payload);
            kafkaTemplate.send(TOPIC, message);
        } catch (JsonProcessingException e) {
            throw new VehicleApplicationException("Failed to send message to topic: " + TOPIC + ". Error: " + e.getMessage(), CodeEnum.INTERNAL_SERVER_ERROR);
        }
    }
}

