package com.bridgestone.vehicles.controller;

import com.bridgestone.vehicles.config.SecurityConfig;
import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.enums.VehicleStatus;
import com.bridgestone.vehicles.exception.VehicleApplicationException;
import com.bridgestone.vehicles.model.KafkaPayload;
import com.bridgestone.vehicles.model.VehicleState;
import com.bridgestone.vehicles.repository.VehicleRepository;
import com.bridgestone.vehicles.service.KafkaProducerService;
import com.bridgestone.vehicles.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({VehicleController.class, AuthController.class})
@Import({SecurityConfig.class, TokenService.class})
class VehicleControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    VehicleController vehicleController;
    @MockBean
    KafkaProducerService kafkaProducerService;
    @MockBean
    VehicleRepository vehicleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void tokenWhenAnonymousThenStatusIsUnauthorized() throws Exception {
        this.mvc.perform(post("/token")).andExpect(status().isUnauthorized());
    }

    @Test
    void tokenWithBasicThenGetToken() throws Exception {
        MvcResult result = this.mvc.perform(post("/token").with(httpBasic("admin", "admin"))).andExpect(status().isOk()).andReturn();
        assertThat(result.getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().isUnauthorized());
    }

    @Test
    void rootWithBasicStatusIsUnauthorized() throws Exception {
        this.mvc.perform(get("/").with(httpBasic("admin", "admin"))).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void statusWithMockUserStatusBadRequest() throws Exception {
        // We don't pass any DTO, so we expect to get 400
        this.mvc.perform(post("/api/vehicles/status")).andExpect(status().is4xxClientError());
    }

    @Test
    void sendStatusSuccess() {
        var payload = new KafkaPayload(12L, "location1", 10.0, VehicleStatus.AVAILABLE, "description1");
        var expected = ResponseEntity.ok().body(payload);
        assertEquals(expected, vehicleController.sendStatus(payload));
    }

    @Test
    @WithMockUser
    void sendStatusException() throws Exception {
        // We expect validation to fail because vehicleId = null
        var payload = new KafkaPayload(null, "location1", 10.0, VehicleStatus.AVAILABLE, "description1");
        mvc.perform(post("/api/vehicles/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getHistorySuccess() {
        var vehicleStates = List.of(VehicleState.builder()
                        .id(1L)
                        .vehicleId(100L)
                        .temperature(10.01)
                        .location("location1")
                        .build(),
                VehicleState.builder()
                        .id(2L)
                        .vehicleId(100L)
                        .temperature(10.02)
                        .location("location2")
                        .build());
        when(vehicleRepository.findAllByVehicleId(any()))
                .thenReturn(vehicleStates);
        var expected = ResponseEntity.ok().body(vehicleStates);
        assertEquals(expected, vehicleController.getHistory(100L));
    }

    @Test
    void getHistoryException() {
        when(vehicleRepository.findAllByVehicleId(any()))
                .thenReturn(Collections.emptyList());
        var exception = assertThrows(VehicleApplicationException.class, () -> vehicleController.getHistory(100L));
        assertEquals("No entities were found for vehicleId: 100", exception.getMessage());
        assertEquals(CodeEnum.NOT_FOUND, exception.getCodeEnum());
    }
}