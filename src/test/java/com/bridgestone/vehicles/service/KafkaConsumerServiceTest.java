package com.bridgestone.vehicles.service;

import com.bridgestone.vehicles.enums.VehicleStatus;
import com.bridgestone.vehicles.model.VehicleState;
import com.bridgestone.vehicles.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private Logger log;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Test
    void testConsumeValidMessage() {
        // Given
        String message = "{\"vehicleId\":1,\"location\":\"location1\",\"temperature\":20.0,\"status\":\"AVAILABLE\",\"description\":\"description1\"}";

        // When
        kafkaConsumerService.consume(message);

        // Then
        ArgumentCaptor<VehicleState> vehicleStateCaptor = ArgumentCaptor.forClass(VehicleState.class);
        verify(vehicleRepository, times(1)).save(vehicleStateCaptor.capture());
        VehicleState capturedVehicleState = vehicleStateCaptor.getValue();

        assertNotNull(capturedVehicleState);
        assertEquals(1L, capturedVehicleState.getVehicleId());
        assertEquals("location1", capturedVehicleState.getLocation());
        assertEquals(20.0, capturedVehicleState.getTemperature());
        assertEquals(VehicleStatus.AVAILABLE, capturedVehicleState.getStatus());
        assertEquals("description1", capturedVehicleState.getDescription());
        assertNotNull(capturedVehicleState.getDateTime());
    }

    @Test
    void testConsumeInvalidMessage() {
        // Given
        String invalidMessage = "{\"vehicleId\":1,\"location\":\"location1\"";  // Invalid JSON

        // When
        kafkaConsumerService.consume(invalidMessage);

        // Then
        verify(vehicleRepository, never()).save(any());
    }
}
