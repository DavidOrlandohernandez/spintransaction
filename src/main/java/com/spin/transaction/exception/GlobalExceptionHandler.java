package com.spin.transaction.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidAmount(InvalidAmountException ex) {

        ErrorResponse response = new ErrorResponse(
                "INVALID_AMOUNT",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DebitLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleDebitLimit(DebitLimitExceededException ex) {

        ErrorResponse response = new ErrorResponse(
                "DEBIT_LIMIT_EXCEEDED",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UnsupportedCurrencyException.class)
    public ResponseEntity<ErrorResponse> handleCurrency(UnsupportedCurrencyException ex) {

        ErrorResponse response = new ErrorResponse(
                "UNSUPPORTED_CURRENCY",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // fallback general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse response = new ErrorResponse(
                "INTERNAL_ERROR",
                "Unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                OffsetDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {

        String message = "Invalid request format";

        if (ex.getCause() instanceof InvalidFormatException formatException) {

            if (formatException.getTargetType() != null &&
                    formatException.getTargetType().isEnum()) {

                message = "Invalid value for field 'type'. Allowed values: CREDIT, DEBIT";
            }
        }

        ErrorResponse response = new ErrorResponse(
                "INVALID_REQUEST",
                message,
                400,
                OffsetDateTime.now()
        );

        return ResponseEntity.badRequest().body(response);
    }
}