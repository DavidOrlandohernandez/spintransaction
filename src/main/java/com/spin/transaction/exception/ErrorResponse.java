package com.spin.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String error;
    private String message;
    private int status;
    private OffsetDateTime timestamp;
}