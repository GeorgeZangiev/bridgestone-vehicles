package com.bridgestone.vehicles.model;

import com.bridgestone.vehicles.enums.VehicleStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KafkaPayloadTest {

    @Test
    void testEqualsAndHashCode() {
        var payloadOne = new KafkaPayload(1L, "location2", 10.03, VehicleStatus.BROKEN, "description2");
        var payloadTwo = new KafkaPayload(1L, "location2", 10.03, VehicleStatus.BROKEN, "description2");
        assertEquals(payloadOne, payloadTwo);
        assertEquals(payloadOne.hashCode(), payloadTwo.hashCode());
    }
}