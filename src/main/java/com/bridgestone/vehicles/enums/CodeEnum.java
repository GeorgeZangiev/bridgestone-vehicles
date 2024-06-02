package com.bridgestone.vehicles.enums;

import lombok.Getter;

@Getter
public enum CodeEnum {

    INTERNAL_SERVER_ERROR(500),
    NOT_FOUND(404),
    BAD_REQUEST(400);

    public final int statusCode;


    CodeEnum(int statusCode) {
        this.statusCode = statusCode;
    }
}
