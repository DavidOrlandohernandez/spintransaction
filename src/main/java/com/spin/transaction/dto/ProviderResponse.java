package com.spin.transaction.dto;

import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderResponse {
    private String transactionId;
    private TransactionStatus status;
    private BigDecimal balance;
    private Instant executedAt;
}
