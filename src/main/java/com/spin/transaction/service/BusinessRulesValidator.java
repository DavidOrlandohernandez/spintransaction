package com.spin.transaction.service;

import com.spin.transaction.exception.BusinessRuleException;
import com.spin.transaction.exception.DebitLimitExceededException;
import com.spin.transaction.exception.InvalidAmountException;
import com.spin.transaction.exception.UnsupportedCurrencyException;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import com.spin.transaction.dto.TransactionRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BusinessRulesValidator {
    public void validate(TransactionRequest request) {

        if (request.getAmount().compareTo(new BigDecimal("1.00")) <= 0) {
            throw new InvalidAmountException("Amount must be greater than $1.00");
        }

        if (request.getType() == TransactionType.DEBIT &&
                request.getAmount().compareTo(new BigDecimal("10000.00")) > 0) {
            throw new DebitLimitExceededException("DEBIT cannot exceed $10,000");
        }

        if (!"MXN".equalsIgnoreCase(request.getCurrency())) {
            throw new UnsupportedCurrencyException("Only MXN currency is supported");
        }
    }
}
