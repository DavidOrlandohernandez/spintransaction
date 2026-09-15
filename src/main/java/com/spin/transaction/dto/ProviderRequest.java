package com.spin.transaction.dto;

import com.spin.transaction.numbregeneratorservice.TransactionType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProviderRequest {
    private String accountId;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
}
