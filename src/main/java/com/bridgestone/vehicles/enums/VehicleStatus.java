package com.bridgestone.vehicles.enums;

import lombok.Getter;

@Getter
public enum VehicleStatus {

    AVAILABLE("AVAILABLE"),
    CURRENTLY_OCCUPIED("CURRENTLY_OCCUPIED"),
    BROKEN("BROKEN"),
    UNAVAILABLE("UNAVAILABLE");

    public final String value;


    VehicleStatus(String value) {
        this.value = value;
    }
}
