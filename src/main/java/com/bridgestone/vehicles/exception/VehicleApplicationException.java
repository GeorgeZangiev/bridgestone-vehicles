package com.bridgestone.vehicles.exception;

import com.bridgestone.vehicles.enums.CodeEnum;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Generated
public class VehicleApplicationException extends RuntimeException {

    private final CodeEnum codeEnum;

    public VehicleApplicationException(String message, CodeEnum codeEnum) {
        super(message);
        this.codeEnum = codeEnum;
    }

    public VehicleApplicationException(String message, CodeEnum codeEnum, Throwable cause) {
        super(message, cause);
        this.codeEnum = codeEnum;
    }
}


