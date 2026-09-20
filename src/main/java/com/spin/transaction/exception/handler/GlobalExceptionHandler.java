package com.spin.transaction.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.spin.transaction.exception.*;
import com.spin.transaction.exception.exceptionrules.DebitLimitExceededException;
import com.spin.transaction.exception.exceptionrules.InvalidAmountException;
import com.spin.transaction.exception.exceptionrules.UnsupportedCurrencyException;
import com.spin.transaction.exception.model.ErrorResponse;
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

    @ExceptionHandler(NotIdempotencyException.class)
    public ResponseEntity<ErrorResponse> handleCurrency(NotIdempotencyException ex) {

        ErrorResponse response = new ErrorResponse(
                "NOT_IDEMPOTENCY_CURRENT",
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                "NOT_FOUND",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                OffsetDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}