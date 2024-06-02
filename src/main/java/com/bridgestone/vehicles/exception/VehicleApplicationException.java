package com.bridgestone.vehicles.exception;

import com.bridgestone.vehicles.enums.CodeEnum;

public class VehicleApplicationException extends RuntimeException {

    private final CodeEnum code;

    public VehicleApplicationException(String message, CodeEnum code) {
        super(message);
        this.code = code;
    }

    public CodeEnum getCodeEnum() {
        return code;
    }
}


