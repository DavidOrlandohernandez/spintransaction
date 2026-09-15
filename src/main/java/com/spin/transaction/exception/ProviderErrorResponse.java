package com.spin.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProviderErrorResponse {
    private String status;
    private String code;
    private String message;
}