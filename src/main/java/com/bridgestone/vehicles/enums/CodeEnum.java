package com.bridgestone.vehicles.enums;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Generated
public enum CodeEnum {

    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("BAD_REQUEST", "Request is not valid", HttpStatus.BAD_REQUEST),
    HISTORY_NOT_FOUND("HISTORY_NOT_FOUND", "Failed to find History by vehicleId", HttpStatus.NOT_FOUND),
    MISSED_PARAMETER("MISSED_PARAMETER", "Required parameter absent in request", HttpStatus.BAD_REQUEST),
    AUTH_FAILED("AUTH_FAILED", "Failed to Authorise", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("ACCESS_DENIED", "You don't have an access to the resource", HttpStatus.FORBIDDEN);

    private final String value;
    private final String reason;
    private final HttpStatus status;
}
