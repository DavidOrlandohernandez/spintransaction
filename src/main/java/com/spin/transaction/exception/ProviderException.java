package com.spin.transaction.exception;

import lombok.Getter;

@Getter
public class ProviderException extends RuntimeException {

    private final String code;

    public ProviderException(String status, String code, String message) {
        super(message);
        this.code = code;
    }

}