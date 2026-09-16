package com.spin.transaction.dto;

import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private UUID id;
    private String accountId;
    private TransactionType type;
    private BigDecimal amount;
    private String currency;
    private String description;
    private TransactionStatus status;
    private String providerTransactionId;
    private BigDecimal balanceAfter;
    private OffsetDateTime createdAt;

}