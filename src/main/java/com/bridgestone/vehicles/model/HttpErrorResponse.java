package com.bridgestone.vehicles.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
public class HttpErrorResponse {

    @Schema(description = "Error Code", example = "BAD_REQUEST")
    @NotNull
    private String code;

    @Schema(description = "Reason of failure", example = "Entity not found")
    @NotNull
    private String reason;

    @Schema(description = "More detailed description about error and the way client can resolve it",
            example = "We have not found entity with id: 78621, validate id and try again later")
    private String description;

    @Schema(description = "Code of System, that returned error", example = "PBE.bridgestone-vehicles")
    @NotNull
    private String system;

    @Schema(description = "Timestamp at the moment of error", example = "2022-12-31T23:59:27.876+03:00")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime timestamp;

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
