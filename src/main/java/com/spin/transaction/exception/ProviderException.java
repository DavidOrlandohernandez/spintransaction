package com.spin.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ProviderException extends RuntimeException {

    private final String code;
    private final String status;
    private final String message;
    private final String httpStatus;

}