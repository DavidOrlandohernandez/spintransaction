package com.spin.transaction.dto.transaction;

import com.spin.transaction.enums.TransactionStatus;
import com.spin.transaction.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

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