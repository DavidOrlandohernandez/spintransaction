package com.spin.transaction.service;

import com.spin.transaction.numbregeneratorservice.TransactionStatus;
import com.spin.transaction.numbregeneratorservice.TransactionType;
import com.spin.transaction.dto.TransactionRequest;
import com.spin.transaction.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TransactionService implements  ITransactionServices{

    @Autowired
    BusinessRulesValidator businessRulesValidator;

    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(UUID.randomUUID());
        transactionResponse.setAccountId("acc-123456");
        transactionResponse.setType(TransactionType.CREDIT);
        transactionResponse.setAmount(new BigDecimal("1500.00"));
        transactionResponse.setCurrency("MXN");
        transactionResponse.setDescription("Transferencia recibida");
        transactionResponse.setStatus(TransactionStatus.EXECUTED);
        transactionResponse.setProviderTransactionId("txn-789");
        transactionResponse.setBalanceAfter(new BigDecimal("5500.00"));
        transactionResponse.setCreatedAt(OffsetDateTime.now());

        businessRulesValidator.validate(transactionRequest);


        return  transactionResponse;
    }
}
