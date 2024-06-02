package com.bridgestone.vehicles.service;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.enums.VehicleStatus;
import com.bridgestone.vehicles.exception.VehicleApplicationException;
import com.bridgestone.vehicles.model.KafkaPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    private static final String TOPIC = "vehicles_history";

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService kafkaProducerService;

    @Test
    void testSendMessage() throws JsonProcessingException {
        // Given
        KafkaPayload payload = new KafkaPayload(1L, "location1", 20.0, VehicleStatus.AVAILABLE, "description1");

        // When
        kafkaProducerService.sendMessage(payload);

        // Then
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate, times(1)).send(eq(TOPIC), messageCaptor.capture());
        String capturedMessage = messageCaptor.getValue();

        ObjectMapper objectMapper = new ObjectMapper();
        KafkaPayload capturedPayload = objectMapper.readValue(capturedMessage, KafkaPayload.class);

        assertEquals(payload, capturedPayload);
    }
}
