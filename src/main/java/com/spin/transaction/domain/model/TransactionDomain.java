package com.spin.transaction.domain.model;

import com.spin.transaction.enums.TransactionStatus;
import com.spin.transaction.enums.TransactionType;
import com.spin.transaction.exception.exceptionrules.DebitLimitExceededException;
import com.spin.transaction.exception.exceptionrules.InvalidAmountException;
import com.spin.transaction.exception.exceptionrules.UnsupportedCurrencyException;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionDomain {

    private final String accountId;
    private final TransactionType type;
    private final BigDecimal amount;
    private final String currency;
    private final String description;

    private TransactionStatus status;
    private String providerTransactionId;
    private BigDecimal balanceAfter;

    public TransactionDomain(String accountId, TransactionType type,
                             BigDecimal amount, String currency, String description) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    public void applyBusinessRules() {
        if (amount.compareTo(new BigDecimal("1.00")) <= 0) {
            throw new InvalidAmountException("Amount must be greater than $1.00");
        }

        if (type == TransactionType.DEBIT &&
                amount.compareTo(new BigDecimal("10000.00")) > 0) {
            throw new DebitLimitExceededException("DEBIT cannot exceed $10,000");
        }

        if (!"MXN".equalsIgnoreCase(currency)) {
            throw new UnsupportedCurrencyException("Only MXN currency is supported");
        }
    }

    public void markAsExecuted(String providerId, BigDecimal balance) {
        this.status = TransactionStatus.APPROVED;
        this.providerTransactionId = providerId;
        this.balanceAfter = balance;
    }

    public void markAsRejected() {
        this.status = TransactionStatus.REJECTED;
    }

}