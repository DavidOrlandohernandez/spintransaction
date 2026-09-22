package com.spin.transaction.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProviderErrorResponse {

    private String code;
    private String status;
    private String message;
    private String httpStatus;
}