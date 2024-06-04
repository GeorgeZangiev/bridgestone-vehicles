package com.bridgestone.vehicles.handlers;

import com.bridgestone.vehicles.enums.CodeEnum;
import com.bridgestone.vehicles.exception.VehicleApplicationException;
import com.bridgestone.vehicles.model.HttpErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.BindException;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
@Generated
public class RestResponseEntityExceptionHandler {

    private static final String SYSTEM = "PBE:bridgestone-vehicles";

    @ExceptionHandler(VehicleApplicationException.class)
    public ResponseEntity<Object> emptyException(VehicleApplicationException e) {
        HttpErrorResponse response = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(e.getMessage())
                .reason(e.getCodeEnum().getReason())
                .code(e.getCodeEnum().getValue())
                .timestamp(OffsetDateTime.now())
                .build();
        return new ResponseEntity<>(response, e.getCodeEnum().getStatus());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        HttpErrorResponse response = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(CodeEnum.MISSED_PARAMETER.getReason())
                .reason("Missing parameter " + ex.getParameterName())
                .code(CodeEnum.MISSED_PARAMETER.getValue())
                .timestamp(OffsetDateTime.now())
                .build();
        return new ResponseEntity<>(response, CodeEnum.MISSED_PARAMETER.getStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleServletRequestConstraintViolation(ConstraintViolationException ex) {
        String msg = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));
        HttpErrorResponse response = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(CodeEnum.BAD_REQUEST.getReason())
                .reason(msg)
                .code(CodeEnum.BAD_REQUEST.getValue())
                .timestamp(OffsetDateTime.now())
                .build();
        return new ResponseEntity<>(response, CodeEnum.BAD_REQUEST.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errCode = notNullExists(ex) ? CodeEnum.MISSED_PARAMETER : CodeEnum.BAD_REQUEST;
        String msg = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        HttpErrorResponse response = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(errCode.getReason())
                .reason(msg)
                .code(errCode.getValue())
                .timestamp(OffsetDateTime.now())
                .build();
        return new ResponseEntity<>(response, errCode.getStatus());
    }

    private boolean notNullExists(MethodArgumentNotValidException ex) {
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if ("NotNull".equals(error.getCode())) {
                return true;
            }
        }
        return false;
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            TypeMismatchException.class,
            BindException.class
    })
    public ResponseEntity<Object> badRequest(Exception e) {

        HttpErrorResponse response = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(e.getMessage())
                .reason(CodeEnum.BAD_REQUEST.getReason())
                .code(CodeEnum.BAD_REQUEST.getValue())
                .timestamp(OffsetDateTime.now())
                .build();
        log.warn("Internal Error: ", e);
        return new ResponseEntity<>(response, CodeEnum.BAD_REQUEST.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> serviceProcessInternalException(Exception e) {
        HttpErrorResponse response = HttpErrorResponse.builder()
                .system(SYSTEM)
                .description(e.toString())
                .reason(CodeEnum.INTERNAL_SERVER_ERROR.getReason())
                .code(CodeEnum.INTERNAL_SERVER_ERROR.getValue())
                .timestamp(OffsetDateTime.now())
                .build();
        log.error("Internal Error: ", e);
        return new ResponseEntity<>(response, CodeEnum.INTERNAL_SERVER_ERROR.getStatus());
    }
}